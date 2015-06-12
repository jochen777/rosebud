package de.rosebud.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.rosebud.core.Fragment;
import de.rosebud.sample.events.AddResourceToHeaderEvent;

// is an html - head element with title, js-includes etc.
public class HeadFragment extends Fragment {
	
	List<String> jsFileUrls = new ArrayList<String>();

	@Subscribe
	public void listen(AddResourceToHeaderEvent addResourceToHeaderEvent) {
		jsFileUrls.add(addResourceToHeaderEvent.getJavascriptPath());
		String jsFilesKey = "jsfiles";
		data.put(jsFilesKey, jsFileUrls);
	}
	
	
	@Override
	public void registerListeners(EventBus eventBus){
		// RFE: This is always the same! So perhaps better: Just expose a boolean, if this fragment wants to listen
		eventBus.register(this);
	}

}