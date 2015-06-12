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
		Fragment fragment = null;
		// TODO: If both classname and type are given, throw an error!
		// nothing given? preset type with "normal"
		if (fc.getBeanName() == null && fc.getClassname() == null) {
			fc.setBeanName("normal");
		}
		// class given? instanciate class
		if (fc.getClassname() != null) {
			try {
				fragment = (Fragment) Class.forName(fc.getClassname())
						.newInstance();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}
		// type given? load type
		if (fc.getBeanName() != null) {
			try {
				fragment = (Fragment) appContext.getBean(fc.getBeanName());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		fragment.setStartTemplate(fc.getTemplate());
		fragment.setName(fc.getName());
		fragment.setType(fc.getBeanName());

		fragment.setData(fc.getData());

		if (fc.getChilds() != null) {
			for (FragmentConfig fcChild : fc.getChilds()) {
				fragment.addChild(loadFragment(fcChild));
			}
		}
		return fragment;
	}

}
