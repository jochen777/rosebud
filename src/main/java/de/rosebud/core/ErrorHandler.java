package de.rosebud.core;

import java.io.IOException;

/**
 * Handles Error-Displaying, digging for debug infos.
 * All that is useful for finding the bugs quickly.
 * TODO: Think about doing this whithin a Fragment that is dynamically entered inside the tree!
 * 
 * @author jochen
 *
 */
public class ErrorHandler {
	
	/**
	 * Renders an error-message
	 * @param type	The type/general description of the error-Message  
	 * @param message The short, good understandable error-message
	 * @param e The Exception coming from the grounds
	 * @param fragment The Framgent, that is causing the error
	 * @return the nicely rendered Error-Message 
	 */
	private static String renderError(String type, String message, Exception e, Fragment fragment) {
		return "<div style='border:bold;background:red'><strong>"+type + ":\n</strong><br>" +
				"Template-File:" + fragment.getStartTemplate() +"\n<br>"+message+"</div>";
	}
	
	// returns an error message, if a template render error happens (missing vars, wrong template-syntax...)
	public static String getTemplateRenderErrorMessage(Exception e, Fragment fragment) {
		// TODO: Give more infos about current fragment!
		return ErrorHandler.renderError("Template Error: ", e.getMessage(), e, fragment);
	}

	// returns an error message, if the template can be found.
	public static String getTemplateReadErrorMessage(IOException e,
			Fragment fragment) {
		return ErrorHandler.renderError("Template not found: ", e.getMessage(), e, fragment);
	}
	

}
