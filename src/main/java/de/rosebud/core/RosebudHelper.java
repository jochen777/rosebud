package de.rosebud.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Splitter;

// some helper/conveniance methods that are not directly related to the core 
public class RosebudHelper {
	
	 static String DELIM = "##CONT##";


	// convenient method to quickly set a new template to a specific fragment
	public static void setTemplateToFragmentName(Fragment startFragment,
			String name, String templateName) {
		RosebudHelper.getFragmentWithName(startFragment, name)
				.setStartTemplate(templateName);
	}

	// search a fragment with the specified name within the tree of the
	// rootFragment
	public static Fragment getFragmentWithName(Fragment rootFragment,
			String name) {
		if (rootFragment.getName() != null
				&& rootFragment.getName().equals(name)) {
			return rootFragment;
		}
		for (Fragment child : rootFragment.getChilds()) {
			Fragment correctFragment = RosebudHelper.getFragmentWithName(child,
					name);
			if (correctFragment != null) {
				return correctFragment;
			}
		}
		return null;
	}

	// returns the first fragment with beanName. 
	/*public static Fragment getFirstFragmentWithBehaviour(Fragment rootNode, Class<Behaviour> type) {
		List<Behaviour> behaviours = rootNode.getBehavoiours();
		for (Behaviour behaviour : behaviours) {
			if (behaviour instanceof type) {	// TODO: Does not work this way
				return rootNode;
			}
		}
		for (Fragment child : rootNode.getChilds()) {
			Fragment correctFragment = RosebudHelper.getFirstFragmentWithBehaviour(child,type);
			if (correctFragment != null) {
				return correctFragment;
			}
		}
		return null;
	}*/

	
	// reads the file into a string. (do we really need this?!?)
	// RFE: Introduce a cachedFileReader
	static String readFile(InputStream inputStream)  {
		String theString;
		try {
			theString = IOUtils.toString(inputStream, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		IOUtils.closeQuietly(inputStream);
		return theString;
	}
	
	static String [] getChunksOfTemplate(String template) {
		// RFE: Check if guava Splitter is faster : Iterable<String> it = Splitter.on(delim).split(ff);
		 int p = template.indexOf(DELIM);
		 if (p >= 0) {
			 String [] result = new String [2];
			 result[0]= template.substring(0, p);
			 result[1] = template.substring(p + DELIM.length());
			 return result;
		 } else {
			 String [] result = new String [1];
			 result[0] = template;
			 return result;
		 }

	}

	
	
}
