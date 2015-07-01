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

import scala.annotation.meta.getter;

import com.google.common.eventbus.EventBus;
import com.samskivert.mustache.Mustache;

// RFE: Perhaps rename this to "rosebud" :)
public class ContentBuilder {

	Loader loader;
	Configuration configuration;
	public Configuration getConfiguration() {
		return configuration;
	}

	Environment env;
	TemplateBroker templateBroker;

	public void setTemplateBroker(TemplateBroker templateBroker) {
		this.templateBroker = templateBroker;
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public static ContentBuilder getSimpleContentBuilder(HttpServletRequest req) {
		ContentBuilder cb = new ContentBuilder();
		cb.loader = new Loader();
		cb.configuration = new Configuration();
		cb.env = new Environment();
		cb.env.setReq(req);
		cb.templateBroker = new TemplateBroker();
		return cb;
	}

	public String run(String pageName) {
		String cachedVersion = CacheProvider.getInstance().get(
				this.env.req.getRequestURI());
		if (cachedVersion == null) {
			Fragment root = loader.load(pageName);
			cachedVersion = createPage(null, root);
			CacheProvider.getInstance().put(this.env.req.getRequestURI(),
					cachedVersion);
		}
		return cachedVersion;
	}

	public String createPage(Map<String, Object> globals, Fragment root,
			TemplateBroker templateBroker) {
		Mustache.Compiler compiler = prepareTemplateRenderer();

		if (configuration.getDebugLevel() == configuration.debugLevel.DEBUG) {
			Fragment debugComponent = loader.load("/de/rosebud/core/debug/debug_component");
			root.addChild(debugComponent);
		}
		
		EventBus eventBus = new EventBus("rosebud-page" + root.getName());
		ContentBuilder.registerListeners(root, eventBus);
		ContentBuilder.contentLoad(globals, root, eventBus, env);
		return getContent(root, compiler, templateBroker);
	}

	public String createPage(Map<String, Object> globals, Fragment root) {
		return createPage(globals, root, templateBroker);
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
	// TODO: DO this in a an own class
	private String getContent(Fragment fragment, Mustache.Compiler compiler,
			TemplateBroker templateBroker) {
		StringBuilder start = new StringBuilder();
		if (configuration.getDebugLevel().getLevel() <= configuration.debugLevel.DEBUG
				.getLevel()) {
			start.append("<!-- START: " + fragment.getStartTemplate() + "-->");
		}
		start.append(TemplateRenderer.parseTemplate(
				templateBroker.getTemplate(fragment.getStartTemplate())
						+ ".html", fragment.getData(), fragment, compiler));
		if (configuration.getDebugLevel().getLevel() <= configuration.debugLevel.DEBUG
				.getLevel()) {
			start.append("<!-- END: " + fragment.getStartTemplate() + "-->");
		}
		for (Fragment child : fragment.getChilds()) {
			start.append(getContent(child, compiler, templateBroker));
		}
		// End - Template
		String endTemplateName = templateBroker.getTemplate(fragment
				.getStartTemplate() + "_end")
				+ ".html";
		Resource endTemplateResource = new ClassPathResource(endTemplateName);
		if (endTemplateResource.exists()) {
			if (configuration.getDebugLevel().getLevel() <= configuration.debugLevel.DEBUG
					.getLevel()) {
				start.append("<!-- ENDTemplate: " + fragment.getStartTemplate() + "-->");
			}
			start.append(TemplateRenderer.parseTemplate(endTemplateName,
					fragment.getData(), fragment, compiler));
			if (configuration.getDebugLevel().getLevel() <= configuration.debugLevel.DEBUG
					.getLevel()) {
				start.append("<!-- ENDTemplate_END: " + fragment.getStartTemplate() + "-->");
			}
		}
		return start.toString();
	}

	// Prepares the tree
	private static void contentLoad(Map<String, Object> globals,
			Fragment fragment, EventBus eventBus, Environment env) {
		// RFE: Do this async?
		List<Behaviour> behaviours = fragment.getBehavoiours();
		for (Behaviour behaviour : behaviours) {
			behaviour.collectData(globals, eventBus, env);
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
