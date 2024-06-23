package uy.edu.um.tad.hash;

import lombok.Setter;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;
import uy.edu.um.tad.hash.MyHash;
import uy.edu.um.tad.linkedlist.MyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementacion de MyHash Cerrado, que se autodimensiona
 * si el factor de carga supera 0.75
 */
public class MyHashImpl<Key, Value> implements MyHash<Key, Value> {

	private HashMap<Key, Value> map = null;
	
	/**
	 * Crea un hash cerrado con la cantidad de buckets de 16
	 */
	public MyHashImpl() {
		map = new HashMap<Key, Value>();
	}

	/**
	 * Crea un hash cerrado con la cantidad de buckets inicial de "initialCapacity"
	 */
	public MyHashImpl(int initialCapacity) {
		map = new HashMap<Key, Value>(initialCapacity);
	}


	@Override
	public MyList<Key> keys() {
		MyList<Key> toReturn = new MyLinkedListImpl<>();

		map.keySet().stream().forEach(s -> toReturn.add(s));

		return toReturn;
	}

	@Override
	public MyList<Value> values() {
		MyList<Value> toReturn = new MyLinkedListImpl<>();

		map.values().stream().forEach(s -> toReturn.add(s));

		return toReturn;
	}

    @Override
    public int size() {
        return map.size();
    }

    @Override
	public void put(Key key, Value value) {
		map.put(key, value);
	}

	@Override
	public Value get(Key key) {
		return map.get(key);
	}

    @Override
    public boolean contains(Key key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(Key key) {
        map.remove(key);
    }

	public MyList<Value> valuesAsList() {
		MyList<Value> toReturn = new MyLinkedListImpl<>();
		map.values().stream().forEach(s -> toReturn.add(s));
		return toReturn;
	}

	public Iterable<Value> valuesAsIterable() {
		return new ArrayList<>(map.values());
	}

	public Value getOrDefault(Key key, Value defaultValue) {
		Value value = this.get(key);
		return (value != null) ? value : defaultValue;
	}

	public Set<Key> keySet() {
		return map.keySet();
	}
	public Set<Map.Entry<Key, Value>> entrySet() {
		return map.entrySet();
	}

	public static class Entry<K, V> {
		private K key;
		@Setter
        private V value;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

    }
}

