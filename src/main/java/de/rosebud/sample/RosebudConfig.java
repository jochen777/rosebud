package de.rosebud.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Environment;
import de.rosebud.core.Loader;
import de.rosebud.core.TemplateBroker;
import de.rosebud.core.TemplateRendererMustache;

// setup some configuraitons that can be used in the project
@Configuration
public class RosebudConfig {

    @Autowired
    Loader loader;

    @Bean
    public ContentBuilder simpleContentBuilder() {
        ContentBuilder cb = new ContentBuilder();
        cb.setLoader(loader);
        cb.setConfiguration(new de.rosebud.core.Configuration());
        cb.setEnv(new Environment());
        cb.setTemplateBroker(new TemplateBroker());
        TemplateRendererMustache mustacheTemplateRenderer = new TemplateRendererMustache();
        mustacheTemplateRenderer.setCompiler(ContentBuilder.prepareTemplateRenderer());
        cb.setTemplateRenderer(mustacheTemplateRenderer);
        return cb;
    }


}
