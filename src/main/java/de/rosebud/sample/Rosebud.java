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
		Fragment root = loader.load("sample");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cont", "Global content");
		RosebudHelper.getFragmentWithName(root, "central").setData(model);
		return ContentBuilder.createPage(model, root, req);
	}


}
