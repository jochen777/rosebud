package de.rosebud.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

// some helper/conveniance methods that are not directly related to the core 
public class RosebudHelper {

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
	static String readFile(InputStream inputStream) throws IOException {
		String theString = IOUtils.toString(inputStream, "UTF-8");
		IOUtils.closeQuietly(inputStream);
		return theString;
	}

}
