package de.rosebud.core;

import javax.servlet.http.HttpServletRequest;

// Sort of "global" class for a page, that gives access to the web environemtn
public class Environment {

    HttpServletRequest req;

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest _req) {
        req = _req;
    }

}
