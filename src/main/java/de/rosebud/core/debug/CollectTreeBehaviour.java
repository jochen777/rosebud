package de.rosebud.core.debug;

import java.util.Map;

import com.google.common.eventbus.EventBus;

import de.rosebud.core.Behaviour;
import de.rosebud.core.DefaultBehaviour;
import de.rosebud.core.Environment;
import de.rosebud.core.Fragment;

// adds a representation of the current page tree to the data
public class CollectTreeBehaviour extends DefaultBehaviour implements Behaviour {

    class node {
        String name;
        String indent;
    }

    @Override
    public void collectData(Map<String, Object> additionalData,
                            EventBus eventBus, Environment env) {
        // find root
        Fragment root = this.getHostFragment();
        while (root.getParent() != null) {
            root = root.getParent();
        }
        String treeAsString = createTreeAsString(root, "");
        this.getHostFragment().addSingleData("tree", treeAsString);

    }

    private String createTreeAsString(Fragment node, String indent) {
        StringBuilder content = new StringBuilder();
        content.append(indent).append(node.getStartTemplate()).append("<br>");
        content.append("<strong>" + node.getData() + "</strong><br>");
        for (Fragment child : node.getChilds()) {
            content.append(createTreeAsString(child, indent + "-"));
        }
        return content.toString();
    }

}
