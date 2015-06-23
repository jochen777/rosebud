package de.rosebud.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.eventbus.EventBus;
import com.samskivert.mustache.Mustache;

// RFE: Perhaps rename this to "rosebud" :)
public class ContentBuilder {

	public static String run(String pageName, Loader loader,
			HttpServletRequest req) {
		String cachedVersion = CacheProvider.getInstance().get(
				req.getRequestURI());
		if (cachedVersion == null) {
			Fragment root = loader.load(pageName);
			cachedVersion = ContentBuilder.createPage(null, root, req);
			CacheProvider.getInstance().put(req.getRequestURI(), cachedVersion);
		}
		return cachedVersion;
	}

	public static String createPage(Map<String, Object> globals, Fragment root,
			HttpServletRequest req, TemplateBroker templateBroker) {
		Mustache.Compiler compiler = prepareTemplateRenderer();
		
		EventBus eventBus = new EventBus("rosebud-page" + root.getName());
		Environment env = new Environment();
		env.setReq(req);
		ContentBuilder.registerListeners(root, eventBus);
		ContentBuilder.contentLoad(globals, root, eventBus, env);
		return ContentBuilder.getContent(root, compiler, templateBroker);
	}
	
	
	public static String createPage(Map<String, Object> globals, Fragment root,
			HttpServletRequest req) {
		TemplateBroker templateBroker = new TemplateBroker();
		return ContentBuilder.createPage(globals, root, req, templateBroker);
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
	private static String getContent(Fragment fragment,
			Mustache.Compiler compiler, TemplateBroker templateBroker) {
		StringBuilder start = new StringBuilder(TemplateRenderer.parseTemplate(
				templateBroker.getTemplate(fragment.getStartTemplate()) + ".html", fragment.getData(), fragment,
				compiler));
		for (Fragment child : fragment.getChilds()) {
			start.append(ContentBuilder.getContent(child, compiler, templateBroker));
		}
		// End - Template
		String endTemplateName = templateBroker.getTemplate(fragment.getStartTemplate() + "_end") + ".html"; 
		Resource endTemplateResource = new ClassPathResource(endTemplateName);
		if (endTemplateResource.exists()) {
			start.append(TemplateRenderer.parseTemplate(endTemplateName, fragment.getData(),
					fragment, compiler));
		}
		return start.toString();
	}

	// Prepares the tree
	private static void contentLoad(Map<String, Object> globals,
			Fragment fragment, EventBus eventBus, Environment env) {
		// RFE: Do this async?
		List<Behaviour> behaviours = fragment.getBehavoiours();
		for (Behaviour behaviour : behaviours) {
			behaviour.setEnv(env);
			behaviour.collectData(globals, eventBus);
		}
		for (Fragment child : fragment.getChilds()) {
			ContentBuilder.contentLoad(globals, child, eventBus, env);
		}
	}

	// Prepares the tree
	private static void registerListeners(Fragment fragment, EventBus eventBus) {
		List<Behaviour> behaviours = fragment.getBehavoiours();
		for (Behaviour behaviour : behaviours) {
			behaviour.registerListeners(eventBus);
		}
		for (Fragment child : fragment.getChilds()) {
			ContentBuilder.registerListeners(child, eventBus);
		}
	}

}
