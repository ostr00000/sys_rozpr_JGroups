import java.io.Serializable;

public abstract class Action implements Serializable {
    String key;

    public abstract void execute(StateMap stateMap);
}
