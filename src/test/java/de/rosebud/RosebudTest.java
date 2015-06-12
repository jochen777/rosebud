package de.rosebud;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;
import de.rosebud.core.Loader;

public class RosebudTest {
	
	@Test
	public void testExampleTree() {
		Loader loader = new Loader();
		Fragment root = loader.load("sample_test");
		Map<String, Object> model = new HashMap<String, Object>();

		String htmlOutput = ContentBuilder.createPage(model, root);

		assertEquals("Hello World mytestChild Child1Child Child2", htmlOutput);
	}

}
