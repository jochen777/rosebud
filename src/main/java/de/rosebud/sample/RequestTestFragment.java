package de.rosebud.sample;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;

import de.rosebud.core.Behaviour;
import de.rosebud.core.DefaultBehaviour;
import de.rosebud.core.Fragment;

@Scope("prototype")
public class RequestTestFragment extends DefaultBehaviour implements Behaviour {

	@Override
	public void collectData(Map<String, Object> additionalData,
			EventBus eventBus) {
		// do not this in production! req. parameter should never be outputted directly to the template (security-issues!)
		this.getHostFragment().getData().put("name", this.getEnv().getReq().getParameter("name"));
	}

}
