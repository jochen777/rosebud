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
	
	public static String parseTemplate(String filecontent, Map<String, Object> data,
			 Fragment source, Mustache.Compiler compiler) {
			try {
				return compiler.compile(filecontent).execute(data);
			} catch (Exception e) {
				return ErrorHandler.getTemplateRenderErrorMessage(e, source);
			}
	}

}
