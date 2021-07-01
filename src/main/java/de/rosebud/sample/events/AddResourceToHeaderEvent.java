package de.rosebud.sample.events;

// Event that can be thrown to add a resource to the header
public class AddResourceToHeaderEvent {

    private String javascriptPath;

    public String getJavascriptPath() {
        return javascriptPath;
    }

    public AddResourceToHeaderEvent(String javascriptPath) {
        this.javascriptPath = javascriptPath;
    }
}
