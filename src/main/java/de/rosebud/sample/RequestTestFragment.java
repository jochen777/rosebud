package de.rosebud.sample;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;

import de.rosebud.core.Fragment;

@Service("normal")
@Scope("prototype")
public class RequestTestFragment extends Fragment {

	public RequestTestFragment() {
		super();
	}

	public RequestTestFragment(String startTemplate) {
		super(startTemplate);
	}

	@Override
	public void collectData(Map<String, Object> additionalData,
			EventBus eventBus) {
		// do not this in production! req. parameter should never be outputted directly to the template (security-issues!)
		data.put("name", this.getEnv().getReq().getParameter("name"));
	}

}
