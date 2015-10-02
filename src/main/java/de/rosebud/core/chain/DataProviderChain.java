package de.rosebud.core.chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class DataProviderChain {
    private List<DataProvider> dataProviderChain = new ArrayList<>();
    Map<String, Object> data = new HashMap<>();

    public DataProviderChain addDataProvider(DataProvider dp) {
        dataProviderChain.add(dp);
        return this;
    }
    
    public DataProviderChain addDataProvideChain(DataProviderChain chainToAdd) {
        dataProviderChain.addAll(chainToAdd.dataProviderChain);
        return this;
    }
    
    public Map<String, Object> enrichData(HttpServletRequest req) {
        Map<String, Object> global = new HashMap<>();
        for (DataProvider dataProvider : dataProviderChain) {
            global.putAll(dataProvider.enrichData(req, global));
        }
        return global;
    }
    
}
