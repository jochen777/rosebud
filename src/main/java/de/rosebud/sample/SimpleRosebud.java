package de.rosebud.sample;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.rosebud.core.ContentBuilder;

@Controller
public class SimpleRosebud {

        @Autowired
        ContentBuilder simpleContentBuilder;

        @RequestMapping("/simple")
        public @ResponseBody String simple(HttpServletRequest req) {
            return simpleContentBuilder.run("/pagetypes/bootstrap/sample", req);
        }


        @RequestMapping("/quick")
        public @ResponseBody String quick(HttpServletRequest req) {
            return "hello world";
        }

}
