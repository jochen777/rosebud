package de.rosebud.sample;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.rosebud.Data;
import de.rosebud.core.Configuration;
import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;
import de.rosebud.core.Loader;
import de.rosebud.core.RosebudHelper;

@Controller
public class Rosebud {

    @Autowired
    Loader loader;

    @RequestMapping("/rosebud")
    public @ResponseBody
    String test(HttpServletRequest req) {
        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(req);
        return cb.run("/pagetypes/bootstrap/sample", provideGlobalData());
    }

    @RequestMapping("/sport")
    public @ResponseBody
    String sport(HttpServletRequest req) {
        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(req);
        cb.getConfiguration().setDebugLevel(Configuration.DebugLevel.DEBUG);
        Fragment root = loader.load("/pagetypes/bootstrap/sample");
        RosebudHelper.getFragmentWithName(root, "keyvisual").addSingleData(
                "headline", "Die Sportrubrik");
        return cb.createPage(provideGlobalData(), root);
    }

    @RequestMapping("/art")
    public @ResponseBody
    String art(HttpServletRequest req) {
        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(req);
        cb.getConfiguration().setDebugLevel(Configuration.DebugLevel.DEBUG);
        Fragment root = loader.load("/pagetypes/bootstrap/sample");
        RosebudHelper.getFragmentWithName(root, "keyvisual").addSingleData(
                "headline", "Die Kunstrubrik");
        return cb.createPage(null, root);
    }

    @RequestMapping("/test")
    public @ResponseBody
    String testJS(HttpServletRequest req) {
        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(req);
        Fragment root = loader.load("/pagetypes/test/sample_js");
        return cb.createPage(null, root);
    }

    private Data provideGlobalData() {
        Data data = new Data();
        data.addData("title", "This is the Start Page Blueprint");
        return data;
    }


}
