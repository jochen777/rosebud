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

import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;
import de.rosebud.core.Loader;
import de.rosebud.core.RosebudHelper;

@Controller
public class Rosebud {

	@Autowired
	Loader loader;

	@RequestMapping("/rosebud")
	public @ResponseBody String test(HttpServletRequest req) {
		return ContentBuilder.run("/pagetypes/bootstrap/sample", loader, req);
	}

	@RequestMapping("/sport")
	public @ResponseBody String sport(HttpServletRequest req) {
		Fragment root = loader.load("/pagetypes/bootstrap/sample");
		RosebudHelper.getFragmentWithName(root, "keyvisual").addSingleData(
				"headline", "Die Sportrubrik");
		return ContentBuilder.createPage(null, root, req);
	}

	@RequestMapping("/art")
	public @ResponseBody String art(HttpServletRequest req) {
		Fragment root = loader.load("/pagetypes/bootstrap/sample");
		RosebudHelper.getFragmentWithName(root, "keyvisual").addSingleData(
				"headline", "Die Kunstrubrik");
		return ContentBuilder.createPage(null, root, req);
	}
	
	@RequestMapping("/test")
	public @ResponseBody String testJS() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			engine.eval("print('Hello World!');");
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
	}


}
