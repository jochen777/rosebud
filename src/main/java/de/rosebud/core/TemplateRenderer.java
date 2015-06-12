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

import com.samskivert.mustache.Mustache;

public class TemplateRenderer {
	
	public static String parseTemplate(String file, Map<String, Object> data,
			Map<String, Object> globals, Fragment source) {
		try {
			Resource resource = new ClassPathResource("/templates/" + file
					+ ".html");
			String text = RosebudHelper.readFile(resource.getInputStream());
			Resource resourceDir = new ClassPathResource("/templates/");
			final File templateDir = resourceDir.getFile();
			Mustache.Compiler c = Mustache.compiler().withLoader(
					new Mustache.TemplateLoader() {
						public Reader getTemplate(String name)
								throws FileNotFoundException {
							return new FileReader(new File(templateDir, name
									+ ".html"));
						}
					});
			Map<String, Object> merged = new HashMap<String, Object>();
			// first collect all globals
			if (globals != null) {
				merged.putAll(globals);
			}
			// then put on top of this the fragment "local" data
			// --> so local data in .json pagetypes will override globals.
			// RFE: Perhaps make this configurable or discuss this!
			if (data != null) {
				merged.putAll(data);
			}
			try {
				return c.compile(text).execute(merged);
			} catch (Exception e) {
				return ErrorHandler.getTemplateRenderErrorMessage(e, source);
			}
		} catch (IOException e) {
			return ErrorHandler.getTemplateReadErrorMessage(e, source);
		}
	}

}
