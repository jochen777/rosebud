package de.rosebud;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;
import de.rosebud.core.Loader;
import de.rosebud.sample.NormalFragment;

public class RosebudTest {

	@Test
	public void testExampleTree() {
		Loader loader = new Loader();
		Fragment root = loader.load("test/sample_test");
		Map<String, Object> model = new HashMap<String, Object>();

		HttpServletRequest mockedRequest = Mockito
				.mock(HttpServletRequest.class);

		String htmlOutput = ContentBuilder.createPage(model, root,
				mockedRequest);

		assertEquals("Hello World mytestChild Child1Child Child2", htmlOutput);
	}

	@Test
	public void testJSHead() {
		Loader loader = new Loader();
		Fragment root = loader.load("test/sample_js_head");
		Map<String, Object> model = new HashMap<String, Object>();

		HttpServletRequest mockedRequest = Mockito
				.mock(HttpServletRequest.class);

		String htmlOutput = ContentBuilder.createPage(model, root,
				mockedRequest);

		assertEquals(
				"Header Elemnt JS:/js/example.js\nChild Child1Child Child2",
				htmlOutput);
	}

	@Test
	public void testDynamicPageCreation() {

		NormalFragment root = new NormalFragment("test/unittest");
		root.addSingleData("test", "testcontent");

		NormalFragment child1 = new NormalFragment("test/unittest_child");
		child1.addSingleData("cont", "childcontent");

		NormalFragment child2 = new NormalFragment("test/unittest_child");
		child2.addSingleData("cont", "childcontent");

		root.addChild(child1);
		root.addChild(child2);

		HttpServletRequest mockedRequest = Mockito
				.mock(HttpServletRequest.class);

		String htmlOutput = ContentBuilder
				.createPage(null, root, mockedRequest);

		assertEquals(
				"Hello World testcontentChild childcontentChild childcontent",
				htmlOutput);
	}

	@Test
	public void testRequestParams() {

		Loader loader = new Loader();
		Fragment root = loader.load("test/sample_test_request");
		Map<String, Object> model = new HashMap<String, Object>();

		MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
		String name = "Jochen Pier";
		mockedRequest.setParameter("name", name);
		String htmlOutput = ContentBuilder.createPage(model, root,
				mockedRequest);

		assertEquals("Hello World mytest - " + name
				+ "Child Child1Child Child2", htmlOutput);
	}

}
