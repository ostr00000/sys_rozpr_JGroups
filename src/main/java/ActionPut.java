public class ActionPut extends Action {
    private String value;

    ActionPut(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute(StateMap stateMap) {
        stateMap.put(key, value);
    }
}
