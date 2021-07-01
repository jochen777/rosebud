package de.rosebud;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;
import de.rosebud.core.Loader;

public class RosebudTest {

    @Test
    public void testExampleTree() {
        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.setRequestURI("test00");

        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(mockedRequest);
        String htmlOutput = cb.run("/pagetypes/test/sample_test");

        assertEquals("Hello World mytestChild Child1Child Child2", htmlOutput);
    }

    @Test
    public void testJSHead() {

        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.setRequestURI("test0");


        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(mockedRequest);
        String htmlOutput = cb.run("/pagetypes/test/sample_js_head");


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

        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(mockedRequest);


        String htmlOutput = cb
                .createPage(null, root);

        assertEquals(
                "Hello World testcontentChild childcontentChild childcontent",
                htmlOutput);
    }

    @Test
    public void testRequestParams() {

        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.setRequestURI("test2");

        String name = "Jochen Pier";
        mockedRequest.setParameter("name", name);


        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(mockedRequest);
        String htmlOutput = cb.run("/pagetypes/test/sample_test_request");


        assertEquals("Hello World mytest - " + name
                + "Child Child1Child Child2", htmlOutput);
    }

}
