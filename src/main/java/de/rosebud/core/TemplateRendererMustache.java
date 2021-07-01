package de.rosebud.core;

import java.util.Map;

import com.samskivert.mustache.Mustache;

public class TemplateRendererMustache implements TemplateRenderer {

    Mustache.Compiler compiler;

    public void setCompiler(Mustache.Compiler compiler) {
        this.compiler = compiler;
    }


    public String parseTemplate(String filecontent, Map<String, Object> data,
                                Fragment source) {
        try {
            return compiler.compile(filecontent).execute(data);
        } catch (Exception e) {
            return ErrorHandler.getTemplateRenderErrorMessage(e, source);
        }
    }

}
