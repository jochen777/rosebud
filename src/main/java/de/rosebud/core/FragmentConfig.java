package de.rosebud.core;

import java.util.List;
import java.util.Map;

// class that can preconfig a fragment (used for configuring fragments from json)
public class FragmentConfig {

	String template;
	Map<String, Object> data;
	List<FragmentConfig> childs;
	String name;

	String beanName;
	String classname;
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public List<FragmentConfig> getChilds() {
		return childs;
	}

	public void setChilds(List<FragmentConfig> childs) {
		this.childs = childs;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
