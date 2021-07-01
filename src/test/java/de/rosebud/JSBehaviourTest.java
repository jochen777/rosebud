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

public class JSBehaviourTest {

    @Test
    public void testJSBehaviour() {
        Loader loader = new Loader();
        Fragment root = loader.load("/pagetypes/test/sample_js");

        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.setRequestURI("test00");
        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(mockedRequest);
        String htmlOutput = cb.createPage(null, root);

        assertEquals("Hello World Javascript output!Child Child1Child Child2", htmlOutput);
    }


}
