public class ActionRemove extends Action {
    ActionRemove(String key) {
        this.key = key;
    }

    @Override
    public void execute(StateMap stateMap) {
        stateMap.remove(key);
    }
}
