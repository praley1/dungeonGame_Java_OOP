/** Patrick Raley
 *  CMSC335 OOP
 *  Project Final Sorcerers Cave
 */
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Comparator;

class Treasure extends CaveElement {
    String treasureType;
    int treasureIndex;
    int creatureByIndex;
    double weight;
    int value;

    public static final Comparator<Treasure> CompareByWeight = new Comparator<Treasure>() {
        public int compare(Treasure t1, Treasure t2) {
            if (t1.weight > t2.weight)
                return 1;
            else if (t1.weight < t2.weight)
                return -1;
            else
                return 0;
        }
    };

    public static final Comparator<Treasure> CompareByValue = new Comparator<Treasure>() {
        public int compare(Treasure t1, Treasure t2) {
            return t1.value - t2.value;
        }
    };

    public Treasure(String n, int treasureIndex, String treasureType, int creatureByIndex, double weight, int value) {
        name = n;
        this.treasureIndex = treasureIndex;
        this.treasureType = treasureType;
        this.creatureByIndex = creatureByIndex;
        this.weight = weight;
        this.value = value;
    }

    public void addTreeNodes(DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode tNode = new DefaultMutableTreeNode(toString());
        parent.add(tNode);
    }

        public String toString() {
        return "Type: " + treasureType + "  " + "Weight: " + weight + "  " + "Value: " + value + "  " + "Index: " + treasureIndex + " " + creatureByIndex;
    }

}
