package de.rosebud.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

// RFE: Perhaps rename this to "rosebud" :)
public class ContentBuilder {
	
	public static String createPage(Map<String, Object> globals, Fragment root) {
		ContentBuilder.contentLoad(globals, root);
		return ContentBuilder.getContent(root);
	}
	
	
	// Generates the HTML out of the prepared tree
	private static String getContent(Fragment fragment) {
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
	private static void contentLoad(Map<String, Object> globals, Fragment fragment) {
		// RFE: Do this async?
		fragment.collectData(globals);
		for (Fragment child : fragment.getChilds()) {
			ContentBuilder.contentLoad(globals, child);
		}
	}

}
