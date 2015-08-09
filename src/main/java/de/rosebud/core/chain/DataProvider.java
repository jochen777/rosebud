package de.rosebud.core.chain;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class DataProvider {
    Map<String, Object> globalData = new HashMap<>();
    public Map<String, Object> getGlobalData() {
        return globalData;
    }
    public abstract Map<String, Object> enrichData(HttpServletRequest req);
}
