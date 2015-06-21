package de.rosebud.core;

import java.util.Map;

import com.google.common.eventbus.EventBus;

// A behaviour that adds extra functionality to a node
// RFE: Consider visitor patterhn
public interface Behaviour {
	
	public Environment getEnv();

	public void setEnv(Environment env);
	
	public void collectData(Map<String, Object> additionalData,
			EventBus eventBus);
	
	public void registerListeners(EventBus eventBus);
	
	public Fragment getHostFragment();
	
	public void setHostFragment(Fragment fragment);


}
