package de.rosebud.core;

import java.util.List;
import java.util.Map;

// class that can preconfig a fragment (used for configuring fragments from json)
public class FragmentConfig {

	String template;
	Map<String, Object> data;
	List<FragmentConfig> childs;
	String name;
	
	List<String> behavs;
	
	String cache;
	
	
	String ref;
	
	FragmentWrapper wrap;
	
	// inline template
	String inline;

	public String getInline() {
        return inline;
    }

    public void setInline(String inline) {
        this.inline = inline;
    }

	
	public FragmentWrapper getWrap() {
        return wrap;
    }

    public void setWrap(FragmentWrapper wrap) {
        this.wrap = wrap;
    }

    public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public List<String> getBehavs() {
		return behavs;
	}

	public void setBehavs(List<String> behaves) {
		this.behavs = behaves;
	}

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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
