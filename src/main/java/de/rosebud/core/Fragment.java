package de.rosebud.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Core fragment Object. Multiple fragments form a tree. A fragment gets or
 * collects some data and render this with a template
 * 
 * @author jochen
 *
 */
public class Fragment {

	// typical node informations
	Fragment parent;
	List<Fragment> childs = new ArrayList<Fragment>();

	public List<Fragment> getChilds() {
		return childs;
	}

	// Template-Name that should be rendered. (Automatically added an +"_end"
	// Template, if exists
	String startTemplate;
	// Internal Name for fragment. Can be used to find a typical fragment within
	// a tree (example: "navi", "head", "breadcrump"..)
	String name;

	// Type of fragment: Means: Which concrete implementation of fragment object
	// should be used.
	String type = "normal"; // normal is the simplest form.

	// data that should be rendered.
	protected Map<String, Object> data = new HashMap<String, Object>();

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
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

	public void collectData() {
		// collect here your data! -> should be overriden
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartTemplate() {
		return startTemplate;
	}
}
