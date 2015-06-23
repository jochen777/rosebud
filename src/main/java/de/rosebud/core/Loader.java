package de.rosebud.core;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * loads a fragment config
 * 
 * @author jochen
 *
 */
@Service
public class Loader {

	@Autowired
	private ApplicationContext appContext;

	public Fragment load(String config) {
		// load json
		try {
			String url = "/pagetypes/" + config + ".json";
			// http://stackoverflow.com/questions/22096983/spring-mvc-read-file-from-src-main-resources
			Resource resource = new ClassPathResource(url);
			// http://stackoverflow.com/questions/19697555/simple-spring-code-to-parse-json
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(
					DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			FragmentConfig fc = mapper.readValue(resource.getFile(),
					new TypeReference<FragmentConfig>() {
					});

			return loadFragment(fc);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Fragment loadFragment(FragmentConfig fc) {
		Fragment fragment = new Fragment();
		fragment.setStartTemplate(fc.getTemplate());
		fragment.setName(fc.getName());

		fragment.setData(fc.getData());
		if (fc.getCache() != null) {
			if (fc.getCache().equals("normal")) {
				fragment.setCacheType(CacheType.normal);
			} else {
				// TBE: implement correct logging system
				System.err.println("wrong caching!");
			}
			
		}

		if (fc.getBehavs() != null){
			// load behaviour
			for (String behavName : fc.getBehavs()) {
				// convention: If the behav name starts with "bean:" it will load it out of a spring context
				if (behavName.startsWith("bean:")) {
					try {
						Behaviour behav = (Behaviour) appContext.getBean(behavName);
						fragment.addBehaviour(behav);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				} else {
					// class given? instanciate class
					try {
						Behaviour behav = (Behaviour)  Class.forName(behavName)
								.newInstance();
						fragment.addBehaviour(behav);
					} catch (InstantiationException | IllegalAccessException
							| ClassNotFoundException e) {
						// TODO Auto-generated catch block
						throw new RuntimeException(e);
					}
					
				}
				
				
			}
		}

		
		if (fc.getChilds() != null) {
			for (FragmentConfig fcChild : fc.getChilds()) {
				fragment.addChild(loadFragment(fcChild));
			}
		}
		return fragment;
	}

}
