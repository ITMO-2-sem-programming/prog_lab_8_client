package ru.itmo.util;

import java.io.Serializable;
import java.util.HashMap;


/**
 * The {@code HashMapStrictKey} class represents
 * a stripped down functionality implementation
 * of the {@link HashMap} class.
 *
 * The main difference is method {@link #get(K key)} accepts
 * argument - key only of earlier defined type {@code K}.
 * In other cases {@code HashMapStrictKey} behaves the same way
 * as {@link HashMap}.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author Maxim Monakhov
 * @version 0.1
 *
 * @see HashMap
 */
public class HashMapStrictKey <K, V>
    implements Serializable {


    private HashMap<K, V> innerMap;


    /**
     * Constructs an empty {@code HasMapStrictKey}.
     */
    public HashMapStrictKey() {
        innerMap = new HashMap<>();
    }




    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     *
     * @see HashMap#size()
     */
    public int size() {
        return innerMap.size();
    }


    /**
     * Returns {@code true} if this map contains no key-value mappings.
     *
     * @return {@code true} if this map contains no key-value mappings
     *
     * @see HashMap#isEmpty()
     */
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }


    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @see HashMap#get(Object key)
     */
    public V get(K key) {
        return innerMap.get(key);
    }


    /**
     * Returns {@code true} if this map contains a mapping for the
     * specified key.
     *
     * @param   key   The key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified
     * key.
     *
     * @see HashMap#containsKey(Object key)
     */
    public boolean containsKey(K key) {
        return innerMap.containsKey(key);
    }


    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with {@code key}.)
     *
     * @see HashMap#put(K key, V value)
     */
    public V put(K key, V value) {
        return innerMap.put(key, value);
    }
}

