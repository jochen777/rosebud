package de.rosebud.sample;

import java.util.HashMap;
import java.util.Map;

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
		return ContentBuilder.run("bootstrap/sample", loader, req);
	}

	@RequestMapping("/sport")
	public @ResponseBody String sport(HttpServletRequest req) {
		Fragment root = loader.load("bootstrap/sample");
		RosebudHelper.getFragmentWithName(root, "keyvisual").addSingleData(
				"headline", "Die Sportrubrik");
		return ContentBuilder.createPage(null, root, req);
	}

	@RequestMapping("/art")
	public @ResponseBody String art(HttpServletRequest req) {
		Fragment root = loader.load("bootstrap/sample");
		RosebudHelper.getFragmentWithName(root, "keyvisual").addSingleData(
				"headline", "Die Kunstrubrik");
		return ContentBuilder.createPage(null, root, req);
	}


}
