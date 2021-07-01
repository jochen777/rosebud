package de.rosebud.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SpringContentBuilderFactory {

    @Autowired
    Loader loader;

    public Loader getLoader() {
        return loader;
    }

    @Autowired
    freemarker.template.Configuration freemarkerConfiguration;

    public ContentBuilder getFreemarkerBuilder(
            HttpServletRequest req) {
        ContentBuilder cb = new ContentBuilder();
        cb.loader = loader;
        cb.configuration = new Configuration();
        cb.env = new Environment();
        cb.env.setReq(req);
        cb.templateBroker = new TemplateBroker();

        TemplateRendererFreemarker tr = new TemplateRendererFreemarker();
        tr.setConfiguration(freemarkerConfiguration);
        cb.setTemplateRenderer(tr);

        return cb;
    }

}
