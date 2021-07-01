package de.rosebud;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;

// tests if global data is in right namespace
public class GlobalDataTest {

    @Test
    public void testGlobalNamespace() {
        Fragment root = new Fragment("/templates/test/unittest_global");

        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.setRequestURI("test1");

        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(mockedRequest);

        String testTitle = "Globaltest";

        String htmlOutput = cb
                .createPage(provideGlobalData(testTitle), root);

        assertEquals(
                "Hello World " + testTitle,
                htmlOutput);

    }

    private Data provideGlobalData(String globalTitle) {
        Data data = new Data();
        data.addData("title", globalTitle);
        return data;
    }

}
