package de.rosebud.core;

import java.util.Map;

import com.google.common.eventbus.EventBus;

// A behaviour that adds extra functionality to a node
// RFE: Consider visitor patterhn
public interface Behaviour {

    public void collectData(Map<String, Object> additionalData,
                            EventBus eventBus, Environment env);

    public void registerListeners(EventBus eventBus);

    public Fragment getHostFragment();

    public void setHostFragment(Fragment fragment);


}
