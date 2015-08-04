package de.rosebud.core;

import java.util.Map;

public interface TemplateRenderer {
	public String parseTemplate(String filecontent, Map<String, Object> data,
			 Fragment source);
}