package de.rosebud.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.eventbus.EventBus;

// wrapper for a behaviour that is written in javascript
public class JSBehaviour extends DefaultBehaviour implements Behaviour{
	
	String jsFile = null;
	

	public String getJsFile() {
		return jsFile;
	}


	public void setJsFile(String jsFile) {
		this.jsFile = jsFile;
	}


	@Override
	public void collectData(Map<String, Object> additionalData,
			EventBus eventBus) {
		// RFE: Request only one Engine for the server!
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			Resource resource = new ClassPathResource(jsFile);
			engine.eval(new FileReader(resource.getFile()));
		} catch (ScriptException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	

}
