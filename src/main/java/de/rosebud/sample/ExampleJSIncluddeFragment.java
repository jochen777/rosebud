package de.rosebud.sample;

import java.util.Map;

import com.google.common.eventbus.EventBus;

import de.rosebud.core.Fragment;
import de.rosebud.sample.events.AddResourceToHeaderEvent;

public class ExampleJSIncluddeFragment extends Fragment {

	public ExampleJSIncluddeFragment() {
		super();
	}

	public ExampleJSIncluddeFragment(String startTemplate){
		super(startTemplate);
	}

	@Override
	public void collectData(Map<String, Object> additionalData, EventBus eventBus) {
		eventBus.post(new AddResourceToHeaderEvent("/js/example.js"));
	}


}
