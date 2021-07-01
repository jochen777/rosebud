package de.rosebud.core;

import java.io.StringReader;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateRendererFreemarker implements TemplateRenderer {

    Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String parseTemplate(String filecontent, Map<String, Object> data,
                                Fragment source) {
        try {

            Template t = new Template("templateName", new StringReader(filecontent),
                    configuration);

            return FreeMarkerTemplateUtils.processTemplateIntoString(t, data);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
