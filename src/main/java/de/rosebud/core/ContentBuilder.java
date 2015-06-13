package de.rosebud.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.eventbus.EventBus;
import com.samskivert.mustache.Mustache;

// RFE: Perhaps rename this to "rosebud" :)
public class ContentBuilder {
	
	public static String createPage(Map<String, Object> globals, Fragment root) {

		Mustache.Compiler compiler = prepareTemplateRenderer();

		
		
		
		EventBus eventBus = new EventBus("rosebud-page"+root.getName());
		ContentBuilder.registerListeners(root, eventBus);
		ContentBuilder.contentLoad(globals, root, eventBus);
		return ContentBuilder.getContent(root, compiler);
	}


	private static Mustache.Compiler prepareTemplateRenderer() {
		Resource resourceDir = new ClassPathResource("/templates/");
		File templateDir;
		try {
			templateDir = resourceDir.getFile();
		} catch (IOException e) {
			throw new RuntimeException("Can not read /templates/ dir", e);
		}
		Mustache.Compiler c = Mustache.compiler().withLoader(
				new Mustache.TemplateLoader() {
					public Reader getTemplate(String name)
							throws FileNotFoundException {
						return new FileReader(new File(templateDir, name
								+ ".html"));
					}
				});
		return c;
	}
	
	
	// Generates the HTML out of the prepared tree
	private static String getContent(Fragment fragment, Mustache.Compiler compiler) {
		// TODO: moeglichkeit bilden, dass kinderknoten daten dem
		// uebergeordneten Inhalt bereitstellen
		// Vielleicht zwei-pass anbieten?		
		
		StringBuilder start = new StringBuilder(TemplateRenderer.parseTemplate(
				fragment.getStartTemplate(), fragment.getData(), fragment, compiler));
		for (Fragment child : fragment.getChilds()) {
			start.append(ContentBuilder.getContent(child, compiler));
		}
		// End - Template
		Resource endTemplateResource = new ClassPathResource("/templates/"
				+ fragment.getStartTemplate() + "_end" + ".html");
		if (endTemplateResource.exists()) {
			start.append(TemplateRenderer.parseTemplate(fragment.getStartTemplate() + "_end",
					fragment.getData(), fragment, compiler));
		}
		return start.toString();
	}
	
	// Prepares the tree
	private static void contentLoad(Map<String, Object> globals, Fragment fragment, EventBus eventBus) {
		// RFE: Do this async?
		fragment.collectData(globals, eventBus);
		for (Fragment child : fragment.getChilds()) {
			ContentBuilder.contentLoad(globals, child, eventBus);
		}
	}

	// Prepares the tree
	private static void registerListeners(Fragment fragment, EventBus eventBus) {
		fragment.registerListeners(eventBus);
		for (Fragment child : fragment.getChilds()) {
			ContentBuilder.registerListeners(child, eventBus);
		}
	}

	
}
