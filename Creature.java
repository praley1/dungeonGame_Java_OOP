/** Patrick Raley
 *  CMSC335 OOP
 *  Project Final Sorcerers Cave
 */
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Creature extends CaveElement {
    Party party;
    boolean busyFlag = false;
    ArrayList<Treasure> treasures = new ArrayList<Treasure>();
    ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
    String creatureType;
    int creatureEmpathy;
    int creatureFear;
    int partyByIndex;
    double creatureCarryingCapacity;

    //                              COMPARATORS
    public static final Comparator<Creature> CompareByName = new Comparator<Creature>() {
        public int compare(Creature c1, Creature c2) {
            return c1.name.compareTo(c2.name);
        }
    };
    public static final Comparator<Creature> CompareByEmpathy = new Comparator<Creature>() {
        public int compare(Creature c1, Creature c2) {
            return c1.creatureEmpathy - c2.creatureEmpathy;
        }
    };
    public static final Comparator<Creature> CompareByFear = new Comparator<Creature>() {
        public int compare(Creature c1, Creature c2) {
            return c1.creatureFear - c2.creatureFear;
        }
    };
    public static final Comparator<Creature> CompareByCarryingCapacity = new Comparator<Creature>() {
        public int compare(Creature c1, Creature c2) {
            if (c1.creatureCarryingCapacity > c2.creatureCarryingCapacity)
                return 1;
            else if (c1.creatureCarryingCapacity < c2.creatureCarryingCapacity)
                return -1;
            else
                return 0;
        }
    };

    //                              TREE
    public void addTreeNodes(DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(name + "  " + "Type: " + creatureType + "  " + "Fear: " + creatureFear + "  " + "Empathy: " + creatureEmpathy + "  " + "Carrying Capacity: " + creatureCarryingCapacity + "  " + "Index: " + index + " " + partyByIndex);


        for (Treasure t : treasures)
            t.addTreeNodes(cNode);


        for (Artifact a: artifacts)
            a.addTreeNodes(cNode);
        parent.add(cNode);
    }

    //                              SORTING FUNCTIONS
    public void sortTreasureByValue() {
        Collections.sort(treasures, Treasure.CompareByValue);
    }
    public void sortTreasureByWeight() {
        Collections.sort(treasures, Treasure.CompareByWeight);
    }

    //                              CONSTRUCTOR
    public Creature(Party party, int index, String creatureType, String creatureName, int partyByIndex, int creatureEmpathy, int creatureFear, double creatureCarryingCapacity) {
        this.party = party;
        this.index = index;
        this.creatureType = creatureType;
        this.name = creatureName;
        this.partyByIndex = partyByIndex;
        this.creatureEmpathy = creatureEmpathy;
        this.creatureFear = creatureFear;
        this.creatureCarryingCapacity = creatureCarryingCapacity;
    }

    //                              FINDING FUNCTIONS
    public Treasure findTreasureByIndex(int treasureIndex){
        for (Treasure t: treasures){
            if(t.treasureIndex == treasureIndex)
                return t;
        }
        return null;
    }
    public ArrayList<Treasure> findTreasureByType(String treasureType){
        ArrayList<Treasure> list = new ArrayList<Treasure>();
        for (Treasure t: treasures){
            if(treasureType.compareToIgnoreCase(t.treasureType) == 0)
                list.add(t);
        }
        return list;
    }

    public Artifact findArtifactByIndex(int artifactIndex){
        for (Artifact a: artifacts){
            if(a.artifactIndex == artifactIndex)
                return a;
        }
        return null;
    }
    public Artifact findArtifactByName(String artifactName){
        for (Artifact a: artifacts){
            if(artifactName.compareToIgnoreCase(a.artifactName) == 0)
                return a;
        }
        return null;
    }
    public ArrayList<Artifact> findArtifactByType(String artifactType){
        ArrayList<Artifact> list = new ArrayList<Artifact>();
        for (Artifact a: artifacts){
            if(artifactType.compareToIgnoreCase(a.artifactType) == 0)
                list.add(a);
        }
        return list;
    }

    public String toString() {
        String st = "     " + index + " " + creatureType + " " + name + " " + partyByIndex + " " + creatureEmpathy + " " + creatureFear + " " + creatureCarryingCapacity + "\n      Artifacts:\n";
        for (Artifact a : artifacts)
            st += "       " + a + "\n";
        st += "        Treasures:\n";
        for (Treasure t : treasures)
            st += "      " + t + "\n";
        return st;
    }

}
