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

public class TemplateBrokerTest {

	@Test
	public void testMobileTree() {
		Loader loader = new Loader();
		Fragment root = loader.load("test/sample_test");

		MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
		mockedRequest.setRequestURI("test00");
		mockedRequest.addHeader("User-Agent", "something phone 88");
		MobileAwareTemplateBroker templateBroker = new MobileAwareTemplateBroker();
		templateBroker.init(mockedRequest);

		String htmlOutput = ContentBuilder.createPage(null, root,
				mockedRequest, templateBroker);

		assertEquals("Hello World Phone mytestChild Phone Child1Child Phone Child2", htmlOutput);
	}



}
