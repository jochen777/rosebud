package de.rosebud.sample;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.rosebud.Data;
import de.rosebud.core.ContentBuilder;
import de.rosebud.core.PageContext;

@Controller
public class SimpleRosebud {

    @Autowired
    ContentBuilder simpleContentBuilder;

    // simplest variant: render a json pagetype without dynamic data
    @RequestMapping("/simple")
    public @ResponseBody String simple(HttpServletRequest req) {
        return simpleContentBuilder.run("/pagetypes/bootstrap/sample", req);
    }

    // typical mvc example: template, wrapper, model: go!
    @RequestMapping("/blueprint")
    public @ResponseBody String blueprint(HttpServletRequest req) {
        return simpleContentBuilder.runWithHole("/pagetypes/bootstrap/blueprint",
                provideData(req), provideGlobalData(),
                "/de/rosebud/sample/frags/article/article", req);
    }

    private Data provideData(HttpServletRequest req) {
        Data data = new Data();
        data.addData("headline", "Sample Headline Blueprint");
        data.addData("text", "Lorem Ipsum Blueprint");
        data.addData("link", "My Blueprint Link");
        return data;
    }

    
    private Data provideGlobalData() {
        Data data = new Data();
        data.addData("title", "This is the Start Page Blueprint");
        return data;
    }

    // advanced mvc example: access the fragment tree and speak with them
    @RequestMapping("/blueprint2")
    public @ResponseBody String blueprint2(HttpServletRequest req) {
        PageContext pageContext = new PageContext().setPageConfigName(
                "/pagetypes/bootstrap/sample").setReq(req);
        return simpleContentBuilder.run("/pagetypes/bootstrap/sample", req);
    }

    public static final String tt = "hello world";

    @RequestMapping("/quick")
    public @ResponseBody String quick(HttpServletRequest req) {
        return tt;
    }

}
