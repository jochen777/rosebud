package de.rosebud.sample.frags.navi;

import java.util.List;
import java.util.Map;

import com.google.common.eventbus.EventBus;

import de.rosebud.core.Behaviour;
import de.rosebud.core.DefaultBehaviour;
import de.rosebud.core.Environment;
import de.rosebud.core.Fragment;

public class NaviFragment extends DefaultBehaviour implements Behaviour{

	@Override
	public void collectData(Map<String, Object> additionalData,
			EventBus eventBus, Environment env) {
		// check current path
		String currentPath = env.getReq().getRequestURI(); 
		// add to data-structure "active" element
		List<Map<String, Object>> navi = (List<Map<String, Object>>)this.getHostFragment().getData().get("navi");
		Map<String, Object> currentNavi = null;
		for (Map<String, Object> singleNavi : navi) {
			if (singleNavi.get("link").equals(currentPath)) {
				singleNavi.put("active", true);
				break;
			}
		}
	}

}
