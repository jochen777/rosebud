package de.rosebud.core;

import javax.servlet.http.HttpServletRequest;

import de.rosebud.Data;

// dynaimc part of the page rendering process
public class PageContext {
    Environment env;
    String pageConfigName;

    Fragment pageTree;

    Data globalData;

    public PageContext setReq(HttpServletRequest req) {
        if (env == null) {
            env = new Environment();
        }
        env.setReq(req);
        return this;
    }

    public PageContext setPageConfigName(String pageConfigName) {
        this.pageConfigName = pageConfigName;
        return this;
    }

    public PageContext setGlobalData(Data data) {
        globalData = data;
        return this;
    }

    // create callbacks for pageTree
}
