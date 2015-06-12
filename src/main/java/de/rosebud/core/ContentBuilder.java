package de.rosebud.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ContentBuilder {
	// Generates the HTML out of the prepared tree
	public static String getContent(Fragment fragment) {
		// TODO: moeglichkeit bilden, dass kinderknoten daten dem
		// uebergeordneten Inhalt bereitstellen
		// Vielleicht zwei-pass anbieten?		
		
		StringBuilder start = new StringBuilder(TemplateRenderer.parseTemplate(
				fragment.getStartTemplate(), fragment.getData(), fragment));
		for (Fragment child : fragment.getChilds()) {
			start.append(ContentBuilder.getContent(child));
		}
		// End - Template
		Resource endTemplateResource = new ClassPathResource("/templates/"
				+ fragment.getStartTemplate() + "_end" + ".html");
		if (endTemplateResource.exists()) {
			start.append(TemplateRenderer.parseTemplate(fragment.getStartTemplate() + "_end",
					fragment.getData(), fragment));
		}
		return start.toString();
	}
	
	// Prepares the tree
	public static void contentLoad(Map<String, Object> globals, Fragment fragment) {
		// RFE: Do this async?
		fragment.collectData(globals);
		for (Fragment child : fragment.getChilds()) {
			ContentBuilder.contentLoad(globals, child);
		}
	}

}
