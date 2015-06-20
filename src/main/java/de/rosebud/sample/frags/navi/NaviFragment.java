package de.rosebud.sample.frags.navi;

import java.util.List;
import java.util.Map;

import com.google.common.eventbus.EventBus;

import de.rosebud.core.Fragment;

public class NaviFragment extends Fragment{

	public NaviFragment() {
		super();
	}

	public NaviFragment(String startTemplate) {
		super(startTemplate);
	}

	@Override
	public void collectData(Map<String, Object> additionalData,
			EventBus eventBus) {
		// check current path
		String currentPath = this.getEnv().getReq().getRequestURI(); 
		// add to data-structure "active" element
		List<Map<String, Object>> navi = (List<Map<String, Object>>)data.get("navi");
		Map<String, Object> currentNavi = null;
		for (Map<String, Object> singleNavi : navi) {
			if (singleNavi.get("link").equals(currentPath)) {
				singleNavi.put("active", true);
				break;
			}
		}
	}

}
