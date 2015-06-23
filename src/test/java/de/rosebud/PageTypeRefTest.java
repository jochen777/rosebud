package de.rosebud;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;
import de.rosebud.core.Loader;
import de.rosebud.core.TemplateBroker;
import de.rosebud.sample.MobileAwareTemplateBroker;

public class PageTypeRefTest {

	@Test
	public void testPageTypeRef() {
		// tests, if "ref" will load a foreign template tree
		Loader loader = new Loader();
		Fragment root = loader.load("test/sample_test_ref");

		MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
		mockedRequest.setRequestURI("test00");

		String htmlOutput = ContentBuilder.createPage(null, root,
				mockedRequest);

		assertEquals("Hello World ParentHello World mytestChild Child1Child Child2", htmlOutput);
	}



}
