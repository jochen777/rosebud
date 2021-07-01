package de.rosebud.sample;

import java.util.Map;

import com.google.common.eventbus.EventBus;

import de.rosebud.core.Behaviour;
import de.rosebud.core.DefaultBehaviour;
import de.rosebud.core.Environment;
import de.rosebud.sample.events.AddResourceToHeaderEvent;

public class ExampleJSIncluddeFragment extends DefaultBehaviour implements Behaviour {

    @Override
    public void collectData(Map<String, Object> additionalData, EventBus eventBus, Environment env) {
        eventBus.post(new AddResourceToHeaderEvent("/js/example.js"));
    }


}
