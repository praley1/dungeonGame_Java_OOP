/** Patrick Raley
 *  CMSC335 OOP
 *  Project Final Sorcerers Cave
 */
import javax.swing.tree.DefaultMutableTreeNode;

class Artifact extends CaveElement {
    int artifactIndex;
    String artifactType;
    int creatureByIndex;
    String artifactName;

    public Artifact(int artifactIndex, String artifactType, int creatureByIndex, String artifactName) {
        this.artifactIndex = artifactIndex;
        this.artifactType = artifactType;
        this.creatureByIndex = creatureByIndex;
        this.artifactName = artifactName;
    }

    public void addTreeNodes(DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode aNode = new DefaultMutableTreeNode(toString());
        parent.add(aNode);
    }

    public String toString() {
        return artifactName + "  " + "Type: " + artifactType + "  " + "Index: " + artifactIndex + " " + creatureByIndex;
    }

}
