package de.rosebud.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CacheProvider {

    static CacheProvider instance;

    // RFE: consider google cache, thread-safe!
    static Map<String, String> cache = new ConcurrentHashMap<String, String>();

    public String get(String key) {
        return cache.get(key);
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public static CacheProvider getInstance() {
        if (instance == null) {
            instance = new CacheProvider();
        }
        return instance;
    }

}
