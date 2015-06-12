package de.rosebud.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.samskivert.mustache.Mustache;

public class TemplateRenderer {
	
	public static String parseTemplate(String file, Map<String, Object> data,
			 Fragment source) {
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
			try {
				return c.compile(text).execute(data);
			} catch (Exception e) {
				return ErrorHandler.getTemplateRenderErrorMessage(e, source);
			}
		} catch (IOException e) {
			return ErrorHandler.getTemplateReadErrorMessage(e, source);
		}
	}

}
