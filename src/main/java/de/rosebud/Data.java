package de.rosebud;

import java.util.HashMap;
import java.util.Map;

public class Data {
    Map<String, Object> model = null;

    public void addData(String key, Object value) {
        if (model == null) {
            model = new HashMap<String, Object>();
        }
        model.put(key, value);
    }

    public Object getData(String key) {
        return model.get(key);
    }

    public Map<String, Object> getMap() {
        return model;
    }
}
