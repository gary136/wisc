public class Pair<KeyType, ValueType> {
    private final KeyType key;
    private final ValueType value;

    public Pair(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    public KeyType key()   { return key; }
    public ValueType value() { return value; }
}