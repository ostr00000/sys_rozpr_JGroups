import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;

import java.net.InetAddress;


public class DistributedMap implements SimpleStringMap {
    private final StateMap state = new StateMap();
    private final JChannel channel;

    DistributedMap(String cluster_name) throws Exception {
        this.channel = new JChannel(false);
        ProtocolStack protocolStack = new ProtocolStack()
                .addProtocol(new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.0.0.52")))
                .addProtocol(new PING())
                .addProtocol(new MERGE3())
                .addProtocol(new FD_SOCK())
                .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK2())
                .addProtocol(new UNICAST3())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new STATE_TRANSFER())
                .addProtocol(new SEQUENCER());
        this.channel.setProtocolStack(protocolStack);
        protocolStack.init();

        this.channel.setReceiver(new ReceiverDistributedMap(this.state, this.channel));
        this.channel.connect(cluster_name);
        this.channel.getState(null, 10000);
    }

    @Override
    public boolean containsKey(String key) {
        return this.state.containsKey(key);
    }

    @Override
    public String get(String key) {
        return this.state.get(key);
    }

    @Override
    public String put(String key, String value) {
        String ret = this.state.put(key, value);
        Action action = new ActionPut(key, value);
        try {
            this.channel.send(new Message(null, action));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public String remove(String key) {
        String ret = this.state.remove(key);
        Action action = new ActionRemove(key);
        try {
            this.channel.send(new Message(null, action));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public String toString() {
        return this.state.toString();
    }
}
