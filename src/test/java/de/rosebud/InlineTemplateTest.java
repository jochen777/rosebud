package de.rosebud;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import de.rosebud.core.ContentBuilder;

// Tests rosebud inline template ability
public class InlineTemplateTest {

    @Test
    public void testInlineTemplates() {
        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.setRequestURI("test00");

        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(mockedRequest);
        String htmlOutput = cb.run("/pagetypes/test/inline/inline_test");

        assertEquals("Parent-Start Child Child_inline Parent-End", htmlOutput);

    }

}
