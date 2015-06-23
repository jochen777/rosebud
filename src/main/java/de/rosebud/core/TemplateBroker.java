package de.rosebud.core;

import javax.servlet.http.HttpServletRequest;

// decides, which templates should be selected for rendering
public class TemplateBroker {

	public void init(HttpServletRequest req) {
		// do something useful here
	}
	
	// return an alternative template, if wanted...
	public String getTemplate(String templateName) {
		return templateName;
	}
	
}
