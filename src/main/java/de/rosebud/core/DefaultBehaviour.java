package de.rosebud.core;

import com.google.common.eventbus.EventBus;

public abstract class DefaultBehaviour implements Behaviour {

    Fragment hostFragment;

    public Fragment getHostFragment() {
        return hostFragment;
    }

    public void setHostFragment(Fragment fragment) {
        hostFragment = fragment;
    }

    @Override
    public void registerListeners(EventBus eventBus) {
        // Normally the event bus is not needed.
    }

}
