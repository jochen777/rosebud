package de.rosebud.sample;

import java.util.Map;

import com.google.common.eventbus.EventBus;

import de.rosebud.core.Fragment;
import de.rosebud.sample.events.AddResourceToHeaderEvent;

public class ExampleJSIncluddeFragment extends Fragment {

	@Override
	public void collectData(Map<String, Object> additionalData, EventBus eventBus) {
		eventBus.post(new AddResourceToHeaderEvent("/js/example.js"));
	}


}
