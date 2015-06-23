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
			 Fragment source, Mustache.Compiler compiler) {
		try {
			Resource resource = new ClassPathResource(file
					);
			String text = RosebudHelper.readFile(resource.getInputStream());
			try {
				return compiler.compile(text).execute(data);
			} catch (Exception e) {
				return ErrorHandler.getTemplateRenderErrorMessage(e, source);
			}
		} catch (IOException e) {
			return ErrorHandler.getTemplateReadErrorMessage(e, source);
		}
	}

}
