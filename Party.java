/** Patrick Raley
 *  CMSC335 OOP
 *  Project Final Sorcerers Cave
 */
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;

class Party extends CaveElement {
    ArrayList<Creature> members = new ArrayList<Creature>();
    ResourcePool pool;

    public Party(int index, String name) {
        this.index = index;
        this.name = name;
        pool = new ResourcePool(this);

    }

    //                              TREE
    public void addTreeNodes(DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode pNode = new DefaultMutableTreeNode(name + " " + index);

        for (Creature c: members)
            c.addTreeNodes(pNode);
        parent.add(pNode);
    }

    //                              FINDING FUNCTIONS
//    public Creature findCreatureByIndex(int creatureIndex) {
//        for (int i = 0; i < members.size(); i++) {
//            Creature c = members.get(i);
//            if (creatureIndex == c.index) {
//                return c;
//            }
//        }
//        return null;
//    }
//    public Creature findCreatureByName(String creatureName) {
//        for (int i = 0; i < members.size(); i++) {
//            Creature c = members.get(i);
//            if (creatureName.compareToIgnoreCase(c.name) == 0) {
//                return c;
//            }
//        }
//        return null;
//    }
//    public ArrayList<Creature> findCreatureByType(String creatureType) {
//        ArrayList<Creature> list = new ArrayList<Creature>(0);
//        for (int i = 0; i < members.size(); i++) {
//            Creature c = members.get(i);
//            if (creatureType.compareToIgnoreCase(c.creatureType) == 0) {
//                list.add(c);
//            }
//        }
//        return list;
//    }

    //                              SORTING FUNCTIONS
    public void sortCreaturesByName() {
        Collections.sort(members, Creature.CompareByName);
    }
    public void sortCreaturesByEmpathy() {
        Collections.sort(members, Creature.CompareByEmpathy);
    }
    public void sortCreaturesByFear() {
        Collections.sort(members, Creature.CompareByFear);
    }
    public void sortCreaturesByCarryingCapacity() {
        Collections.sort(members, Creature.CompareByCarryingCapacity);
    }

    public String toString() {
        String st = name + " " + index + pool +"\n " + "   Members:\n" ;
        for (Creature c : members)
            st += "    " + c + "\n";
        return st;
    }
}
