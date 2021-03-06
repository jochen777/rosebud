package de.rosebud;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.rosebud.core.ContentBuilder;
import de.rosebud.core.Fragment;
import de.rosebud.core.Loader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RosebudApplication.class)
@WebAppConfiguration
public class RosebudApplicationTests {

    @Autowired
    Loader loader;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testExampleTree() {
        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.setRequestURI("test00");

        ContentBuilder cb = ContentBuilder.getSimpleContentBuilder(mockedRequest);
        String htmlOutput = cb.run("/pagetypes/test/sample_test");

        assertEquals("Hello World mytestChild Child1Child Child2", htmlOutput);
    }

}
