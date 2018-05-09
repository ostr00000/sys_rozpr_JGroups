import java.util.HashMap;
import java.util.Map;

public class StateMap {
    private Map<String, String> map;

    StateMap(){
        this.map = new HashMap<>();
    }

    synchronized public Map<String, String> getMap(){
        return this.map;
    }

    synchronized public void setMap(Map<String, String> map){
        this.map = map;
    }

    synchronized public boolean containsKey(String key) {
        return this.map.containsKey(key);
    }

    synchronized public String get(String key) {
        return this.map.get(key);
    }

    synchronized public String put(String key, String value) {
        return this.map.put(key, value);
    }

    synchronized public String remove(String key) {
        return this.map.remove(key);
    }

    @Override
    synchronized public String toString() {
        return map.toString();
    }
}
