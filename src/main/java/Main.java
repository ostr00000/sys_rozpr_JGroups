import java.util.Scanner;

public class Main {
    private Scanner scanner;
    private DistributedMap distributedMap;

    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");
        Main main = new Main();
        main.mainLoop();
    }

    private Main() throws Exception {
        this.scanner = new Scanner(System.in);
        this.distributedMap = new DistributedMap("default");
    }

    private void mainLoop() {
        while (true) {
            System.out.println("Actions: 'c'-contains, 'g'-get, 'r'-remove, 'p'-put, 's'-show map");

            String action = scanner.next();
            System.out.println("Next action: " + action);
            switch (action) {
                case "c":
                    contains();
                    break;
                case "g":
                    get();
                    break;
                case "r":
                    remove();
                    break;
                case "p":
                    put();
                    break;
                case "s":
                    showMap();
                    break;
                case "e":
                    return;
                default:
                    System.out.println("Unknown action: " + action);
            }
        }
    }

    private void contains() {
        String key = read();
        boolean contains = this.distributedMap.containsKey(key);
        String output = "Map " + (contains ? "contains " : "doesn't contain ") + key;
        System.out.println(output);
    }

    private String read() {
        return this.scanner.next();
    }

    private void get() {
        String key = read();
        String value = this.distributedMap.get(key);
        System.out.println("Map[" + key + "]=" + value);
    }

    private void remove() {
        String key = read();
        String value = this.distributedMap.remove(key);
        System.out.println("Removed key: " + key + " with value: " + value);
    }

    private void put() {
        String key = read();
        String value = read();
        String old_val = this.distributedMap.put(key, value);
        System.out.println("Map[" + key + "]=" + value + " old value was: " + old_val);
    }

    private void showMap() {
        System.out.println(this.distributedMap.toString());
    }
}
