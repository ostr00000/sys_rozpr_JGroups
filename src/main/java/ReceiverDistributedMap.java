import org.jgroups.*;
import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class ReceiverDistributedMap extends ReceiverAdapter {
    private final StateMap stateMap;
    private final JChannel channel;

    ReceiverDistributedMap(StateMap state, JChannel channel) {
        this.stateMap = state;
        this.channel = channel;
    }

    @Override
    public void receive(Message message) {
        Action action = (Action) message.getObject();
        action.execute(this.stateMap);
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized (this.stateMap) {
            Util.objectToStream(this.stateMap.getMap(), new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        synchronized (this.stateMap) {
            //noinspection unchecked - only allowed objects are Map<String, String>
            this.stateMap.setMap((Map<String, String>) Util.objectFromStream(new DataInputStream(input)));
        }
    }

    @Override
    public void viewAccepted(View view) {
        if (view instanceof MergeView) {
            MergeView mergeView = (MergeView) view;
            System.out.println(view);
            System.out.println(hasWrongGroup(mergeView));
            if (hasWrongGroup(mergeView)) {
                new Thread(() -> {
                    try {
                        this.channel.getState(mergeView.getSubgroups().get(0).getCoord(), 10000);
                    } catch (Exception e) {
                        System.out.println("WARNING: find another group, but cannot get their state");
                    }
                }).start();
            }
        }
    }

    private boolean hasWrongGroup(MergeView mergeView) {
        View firstGroup = mergeView.getSubgroups().get(0);
        Address myAddress = this.channel.getAddress();
        return !firstGroup.getMembers().contains(myAddress);
    }
}
