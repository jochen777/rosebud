package de.rosebud.core;

import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ContentBuilder {
	public static String getContent(Map<String, Object> globals, Fragment fragment) {
		fragment.collectData();
		// TODO: moeglichkeit bilden, dass kinderknoten daten dem
		// uebergeordneten Inhalt bereitstellen
		// Vielleicht zwei-pass anbieten?
		StringBuilder start = new StringBuilder(TemplateRenderer.parseTemplate(
				fragment.getStartTemplate(), fragment.getData(), globals, fragment));
		for (Fragment child : fragment.getChilds()) {
			start.append(ContentBuilder.getContent(globals,child));
		}
		// End - Template
		Resource endTemplateResource = new ClassPathResource("/templates/"
				+ fragment.getStartTemplate() + "_end" + ".html");
		if (endTemplateResource.exists()) {
			start.append(TemplateRenderer.parseTemplate(fragment.getStartTemplate() + "_end",
					fragment.getData(), globals, fragment));
		}
		return start.toString();
	}

}
