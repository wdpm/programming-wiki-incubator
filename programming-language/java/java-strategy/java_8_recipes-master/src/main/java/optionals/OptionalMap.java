package optionals;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionalMap<K,V> {
    private Map<K,V> map = new HashMap<>();

    public Map<K,V> getMap() {
        return map;
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public Optional<V> get(K key) {
        return Optional.ofNullable(map.get(key));
    }
}
