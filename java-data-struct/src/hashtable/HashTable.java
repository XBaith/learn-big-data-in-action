package hashtable;

import java.util.TreeMap;

public class HashTable<K, V> {
    private final int[] capacity
            = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
            49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
            12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};
    private final static int upperTol = 10;
    private final static int lowerTol = 2;
    private static int initIndex = 0;
    private TreeMap<K, V>[] hashTable;
    private int M;
    private int size;

    public HashTable() {
        this.M = capacity[initIndex];
        hashTable = new TreeMap[M];
        for(int i = 0 ; i < M; i++){
            hashTable[i] = new TreeMap<>();
        }
        size = 0;
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize(){
        return size;
    }

    public V get(K key){
        return hashTable[hash(key)].get(key);
    }

    public void add(K key, V value){
        TreeMap map = hashTable[hash(key)];
        if(map.containsKey(key)){
            map.put(key, value);
        }else{
            map.put(key, value);
            size++;
            if(size >= upperTol * M){
                resize(capacity[initIndex++]);
            }
        }
    }

    private void resize(int newM) {
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for(int i = 0 ; i < newM; i++){
            newHashTable[i] = new TreeMap<>();
        }
        int oldM = M;
        this.M = newM;
        for(int i = 0 ; i < oldM; i++){
            TreeMap<K, V> map = hashTable[i];
            for(K key : map.keySet()) {
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }
        this.hashTable = newHashTable;
    }

    public V remove(K key){
        TreeMap<K, V> map = hashTable[hash(key)];
        if(!map.containsKey(key)){
            throw new IllegalArgumentException(key + " is not exist");
        }
        V result = map.remove(key);
        size--;
        if(size < lowerTol * M && M >= capacity[0]){
            resize(capacity[initIndex--]);
        }
        return result;
    }

    public void set(K key, V value){
        TreeMap map = hashTable[hash(key)];
        if(!map.containsKey(key)){
            throw new IllegalArgumentException(key + " is not exist");
        }
        map.put(key, value);
    }

    public boolean contains(K key){
        return hashTable[hash(key)].containsKey(key);
    }
}
