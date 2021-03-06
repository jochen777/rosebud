package de.rosebud.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.eventbus.EventBus;
import com.samskivert.mustache.Mustache;

import de.rosebud.Data;

// RFE: Perhaps rename this to "rosebud" :)
public class ContentBuilder {

    Loader loader;
    Configuration configuration;

    public static int instancecount = 0;

    public ContentBuilder() {
        instancecount++;
        System.err.println("I was instanciated:" + instancecount);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    Environment env;
    TemplateBroker templateBroker;
    TemplateRenderer templateRenderer;

    public void setTemplateRenderer(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

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

    public static ContentBuilder getSimpleContentBuilder(
            HttpServletRequest req) {
        ContentBuilder cb = new ContentBuilder();
        cb.loader = new Loader();
        cb.configuration = new Configuration();
        cb.env = new Environment();
        cb.env.setReq(req);
        cb.templateBroker = new TemplateBroker();
        TemplateRendererMustache mustacheTemplateRenderer = new TemplateRendererMustache();
        mustacheTemplateRenderer.setCompiler(prepareTemplateRenderer());
        cb.templateRenderer = mustacheTemplateRenderer;

        return cb;
    }

    public Fragment load(String pageTemplateName) {
        return loader.load(pageTemplateName);
    }

    // RFE: Reduce number of arguments
    public String runWithHole(String pageTemplateName, Data holeData,
                              Data globalData, String fragmentTemplate, HttpServletRequest req) {
        Fragment root = loader.load(pageTemplateName);
        TreeHelper.putAdHocFragmentInHole(root, holeData, fragmentTemplate);
        // getConfiguration().setDebugLevel(Configuration.DebugLevel.DEBUG);

        this.env.setReq(req);
        return createPage(globalData, root);
    }

    public String run(String pageName) {
        Fragment root = loader.load(pageName);
        return createPage(null, root);
    }

    public String run(String pageName, HttpServletRequest req) {
        this.env.setReq(req);
        return this.run(pageName);
    }

    public String run(String pageName, Data globals, HttpServletRequest req) {
        this.env.setReq(req);
        return this.run(pageName, globals);

    }

    public String run(String pageName, Data globals) {
        Fragment root = loader.load(pageName);
        return createPage(globals, root);
    }

    public String createPage(Data globals, Fragment root,
                             HttpServletRequest req) {
        this.env.setReq(req);
        return createPage(globals, root);
    }

    public String createPage(Map<String, Object> globals, Fragment root,
                             TemplateBroker templateBroker) {

        if (configuration.getDebugLevel() == configuration.debugLevel.DEBUG) {
            Fragment debugComponent = loader
                    .load("/de/rosebud/core/debug/debug_component");
            root.addChild(debugComponent);
        }

        EventBus eventBus = new EventBus("rosebud-page" + root.getName());
        ContentBuilder.registerListeners(root, eventBus);
        ContentBuilder.contentLoad(globals, root, eventBus, env);
        return getContent(root, templateBroker);
    }

    public String createPage(Data globals, Fragment root) {
        return createPage(globals != null ? globals.getMap() : null, root, templateBroker);
    }

    public static Mustache.Compiler prepareTemplateRenderer() {
        Resource resourceDir = new ClassPathResource("/templates/");
        File templateDir;
        try {
            templateDir = resourceDir.getFile();
        } catch (IOException e) {
            throw new RuntimeException("Can not read /templates/ dir", e);
        }
        Mustache.Compiler c = Mustache.compiler()
                .withLoader(new Mustache.TemplateLoader() {
                    public Reader getTemplate(String name)
                            throws FileNotFoundException {
                        return new FileReader(
                                new File(templateDir, name + ".html"));
                    }
                });
        return c;
    }

    // Generates the HTML out of the prepared tree
    // TODO: DO this in a an own class
    private String getContent(Fragment fragment,
                              TemplateBroker templateBroker) {
        StringBuilder start = new StringBuilder();

        String templateText;
        if (fragment.getStartTemplate() != null) {
            Resource resource = new ClassPathResource(
                    templateBroker.getTemplate(fragment.getStartTemplate())
                            + ".html");
            try {
                templateText = RosebudHelper
                        .readFile(resource.getInputStream());
            } catch (IOException e) {
                return ErrorHandler.getTemplateRenderErrorMessage(e, fragment);
            }
        } else {
            templateText = fragment.getInline();
        }

        String[] templateChunks = RosebudHelper
                .getChunksOfTemplate(templateText);

        start.append(templateRenderer.parseTemplate(templateChunks[0],
                fragment.getData(), fragment));

        for (Fragment child : fragment.getChilds()) {
            start.append(getContent(child, templateBroker));
        }
        // End - Template
        if (templateChunks.length == 2) {
            start.append(templateRenderer.parseTemplate(templateChunks[1],
                    fragment.getData(), fragment));
        }
        return start.toString();
    }

    // Prepares the tree
    private static void contentLoad(Map<String, Object> globals,
                                    Fragment fragment, EventBus eventBus, Environment env) {

        // add globals to fragment-data
        // Beware: globals will overwrite locals!!
        if (globals != null) {
            fragment.getData().put("_globals", globals);
            //fragment.getData().putAll(globals);
        }

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
    private static void registerListeners(Fragment fragment,
                                          EventBus eventBus) {
        List<Behaviour> behaviours = fragment.getBehavoiours();
        for (Behaviour behaviour : behaviours) {
            behaviour.registerListeners(eventBus);
        }
        for (Fragment child : fragment.getChilds()) {
            ContentBuilder.registerListeners(child, eventBus);
        }
    }

}
