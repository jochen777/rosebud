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

public class RosebudTest extends Mockito{

	@Test
	public void testExampleTree() {
		Loader loader = new Loader();
		Fragment root = loader.load("test/sample_test");
		Map<String, Object> model = new HashMap<String, Object>();

		MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
		mockedRequest.setRequestURI("test00");

		String htmlOutput = ContentBuilder.createPage(model, root,
				mockedRequest);

		assertEquals("Hello World mytestChild Child1Child Child2", htmlOutput);
	}

	@Test
	public void testJSHead() {
		Loader loader = new Loader();
		Fragment root = loader.load("test/sample_js_head");
		Map<String, Object> model = new HashMap<String, Object>();

		MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
		mockedRequest.setRequestURI("test0");


		String htmlOutput = ContentBuilder.createPage(model, root,
				mockedRequest);

		assertEquals(
				"Header Elemnt JS:/js/example.js\nChild Child1Child Child2",
				htmlOutput);
	}

	@Test
	public void testDynamicPageCreation() {

		Fragment root = new Fragment("/templates/test/unittest");
		root.addSingleData("test", "testcontent");

		Fragment child1 = new Fragment("/templates/test/unittest_child");
		child1.addSingleData("cont", "childcontent");

		Fragment child2 = new Fragment("/templates/test/unittest_child");
		child2.addSingleData("cont", "childcontent");

		root.addChild(child1);
		root.addChild(child2);

		MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
		mockedRequest.setRequestURI("test1");

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
		mockedRequest.setRequestURI("test2");

		String name = "Jochen Pier";
		mockedRequest.setParameter("name", name);
		String htmlOutput = ContentBuilder.createPage(model, root,
				mockedRequest);

		assertEquals("Hello World mytest - " + name
				+ "Child Child1Child Child2", htmlOutput);
	}

}
