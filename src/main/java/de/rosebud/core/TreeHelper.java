package de.rosebud.core;

import de.rosebud.Data;


// convenient methods to work with the fragment-tree
public class TreeHelper {

    static String standardHoleName = "content_container";

    // search standard-hole, put fragment in it with provided data and template
    public static void putAdHocFragmentInHole(Fragment root, Data fragData, String fragmentTemplate) {
        Fragment parentOfHole = RosebudHelper.getFragmentWithName(root, standardHoleName);
        Fragment adHocFragment = new Fragment();
        adHocFragment.setData(fragData.getMap());
        adHocFragment.setParent(parentOfHole);
        adHocFragment.setStartTemplate(fragmentTemplate);
        parentOfHole.addChild(adHocFragment);
    }

}
