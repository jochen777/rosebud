package de.rosebud.sample;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.rosebud.Data;
import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;
import de.rosebud.core.RosebudHelper;

@Controller
public class SimpleRosebud {

    @Autowired
    ContentBuilder simpleContentBuilder;

    // simplest variant: render a json pagetype without dynamic data
    @RequestMapping("/simple")
    public @ResponseBody
    String simple(HttpServletRequest req) {
        return simpleContentBuilder.run("/pagetypes/bootstrap/sample", req);
    }

    // typical mvc example: template, wrapper, model: go!
    @RequestMapping("/blueprint")
    public @ResponseBody
    String blueprint(HttpServletRequest req) {
        return simpleContentBuilder.runWithHole("/pagetypes/bootstrap/blueprint",
                provideData(req), provideGlobalData(),
                "/de/rosebud/sample/frags/article/article", req);
    }

    // advanced mvc example: modify several holes
    @RequestMapping("/blueprint2")
    public @ResponseBody
    String blueprint2(HttpServletRequest req) {
        Fragment root = simpleContentBuilder.load("/pagetypes/bootstrap/blueprint");

        /*
         *  if you want, you can load another pagetree here and append it.
         *  This might be useful to integrate different teaser-columns
         */
        RosebudHelper.getFragmentWithName(root, "content_container").
                addChild(Fragment.build().setStartTemplate("/de/rosebud/sample/frags/article/article").
                        setData(provideData(req)));
        return simpleContentBuilder.createPage(provideGlobalData(), root, req);
    }

    // simplest variant: render a json pagetype without dynamic data
    @RequestMapping("/wrap")
    public @ResponseBody
    String wrap(HttpServletRequest req) {
        return simpleContentBuilder.run("/pagetypes/test/wrapper/sample_test", req);
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

}
