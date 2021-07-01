package de.rosebud.sample;

import javax.servlet.http.HttpServletRequest;

import de.rosebud.core.TemplateBroker;

public class MobileAwareTemplateBroker extends TemplateBroker {

    boolean mobile = false;

    public void init(HttpServletRequest req) {
        // RFE: Use real useragent-sniffing
        if (req.getHeader("User-Agent").contains("phone")) {
            mobile = true;
        }
    }

    // return an alternative template, if wanted...
    public String getTemplate(String templateName) {
        // RFE: Could test, if templates exists. If not, choose normal template...
        if (mobile) {
            return templateName + "_mobile";
        }
        return templateName;
    }


}
