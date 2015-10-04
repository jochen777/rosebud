package de.rosebud.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.eventbus.EventBus;

/**
 * Core fragment Object. Multiple fragments form a tree. A fragment gets or
 * collects some data and render this with a template
 * 
 * @author jochen
 *
 */
public class Fragment {

    public Fragment(String startTemplate) {
        this.startTemplate = startTemplate;
    }

    public Fragment() {

    }

    CacheType cacheType = null;

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

    // typical node informations
    Fragment parent;

    public Fragment getParent() {
        return parent;
    }

    // RFE: Only create new ArrayList if wanted!
    List<Fragment> childs = new ArrayList<Fragment>();

    // RFE: Only create new ArrayList if wanted!
    List<Behaviour> behavoiours = new ArrayList<Behaviour>();

    public List<Behaviour> getBehavoiours() {
        return behavoiours;
    }

    public void addBehaviour(Behaviour behaviour) {
        behavoiours.add(behaviour);
        behaviour.setHostFragment(this);
    }

    public List<Fragment> getChilds() {
        return childs;
    }

    // Template-Name that should be rendered.
    String startTemplate;
    // Internal Name for fragment. Can be used to find a typical fragment within
    // a tree (example: "navi", "head", "breadcrump"..)
    String name;

    // data that should be rendered.
    protected Map<String, Object> data = new HashMap<String, Object>();

    public Map<String, Object> getData() {
        return data;
    }

    // initial data
    public void setData(Map<String, Object> data) {
        if (data != null) {
            this.data = data;
        }
    }

    public void addSingleData(String key, Object data) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        this.data.put(key, data);
    }

    public String toString() {
        return " StartTemplate: " + startTemplate;
    }

    public void addChild(Fragment child) {
        childs.add(child);
        child.setParent(this);
    }

    public void setParent(Fragment parent) {
        this.parent = parent;
    }

    public Fragment setStartTemplate(String startTemplate) {
        this.startTemplate = startTemplate;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTemplate() {
        return startTemplate;
    }
}
