package de.rosebud.core;

import com.google.common.eventbus.EventBus;

public abstract class DefaultBehaviour implements Behaviour {
	
	Environment env;
	Fragment hostFragment;

	@Override
	public Environment getEnv() {
		return env;
	}

	@Override
	public void setEnv(Environment env) {
		this.env = env;
	}

	public Fragment getHostFragment(){
		return hostFragment;
	}
	
	public void setHostFragment(Fragment fragment){
		hostFragment = fragment;
	}
	
	@Override
	public void registerListeners(EventBus eventBus) {
		// Normally the event bus is not needed.
	}

}
