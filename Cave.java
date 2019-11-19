/** Patrick Raley
 *  CMSC335 OOP
 *  Project Final Sorcerers Cave
 */
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;

class Cave {
    ArrayList<Party> parties = new ArrayList<Party>();
    ArrayList<Treasure> unassignedTreasures = new ArrayList<Treasure>();
    ArrayList<Creature> unassignedCreatures = new ArrayList<Creature>();
    ArrayList<Artifact> unassignedArtifacts = new ArrayList<Artifact>();

    //                          FINDING PARTIES/CREATURES/TREASURE/ARTIFACTS
    public Party findPartyByIndex(int partyIndex) {
        for (int i = 0; i < parties.size(); i++) {
            Party p = parties.get(i);
            if (partyIndex == p.index) {
                return p;
            }
        }
        return null;
    }
    public Party findPartyByName(String partyName) {
        for (int i = 0; i < parties.size(); i++) {
            Party p = parties.get(i);
            if (partyName.compareToIgnoreCase(p.name) == 0) {
                return p;
            }
        }
        return null;
    }

    public Creature findCreatureByIndex(int creatureIndex) {
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            if (c.index == creatureIndex)
                return c;
        }
        return  null;
    }
    public Creature findCreatureByName(String creatureName) {
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            if (c.name.compareToIgnoreCase(creatureName) == 0)
                return c;
        }
        return  null;
    }
    public ArrayList<Creature> findCreatureByType(String creatureType) {
        ArrayList<Creature> list = new ArrayList<Creature>();
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            if (c.creatureType.compareToIgnoreCase(creatureType) ==0 )
                list.add(c);
        }
        return list;
    }

    public Treasure findTreasureByIndex(int treasureIndex){
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            Treasure t = c.findTreasureByIndex(treasureIndex);
            if(t != null)
                return t;
        }
        for (Treasure t: unassignedTreasures){
            if (t.treasureIndex == treasureIndex)
                return t;
        }
        return null;
    }
    public ArrayList<Treasure> findTreasureByType(String treasureType){
        ArrayList<Treasure> list = new ArrayList<Treasure>();
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            list.addAll(c.findTreasureByType(treasureType));
        }
        for (Treasure t: unassignedTreasures){
            if (treasureType.compareToIgnoreCase(t.treasureType) == 0)
                list.add(t);
        }
        return list;
    }

    public Artifact findArtifactByIndex(int artifactIndex){
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            Artifact a = c.findArtifactByIndex(artifactIndex);
            if(a != null)
                return a;
        }
        for (Artifact a: unassignedArtifacts){
            if (a.artifactIndex == artifactIndex)
                return a;
        }
        return null;
    }
    public Artifact findArtifactByName(String artifactName){
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            Artifact a = c.findArtifactByName(artifactName);
            if(a != null)
                return a;
        }
        for (Artifact a: unassignedArtifacts){
            if (a.artifactName.compareToIgnoreCase(artifactName) == 0)
                return a;
        }
        return null;
    }
    public ArrayList<Artifact> findArtifactByType(String artifactType){
        ArrayList<Artifact> list = new ArrayList<Artifact>();
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            list.addAll(c.findArtifactByType(artifactType));
        }
        for (Artifact a: unassignedArtifacts){
            if (a.artifactType.compareToIgnoreCase(artifactType) == 0)
                list.add(a);
        }
        return list;
    }

    //                          ADDITIONAL METHOD TO HELP FINDING & SORTING
    public ArrayList<Creature> getAllCreatures(){
        ArrayList<Creature> creatureList = new ArrayList<Creature>();
        for (int i = 0; i < parties.size(); i++) {
            Party p = parties.get(i);
            creatureList.addAll(p.members);
        }
        creatureList.addAll(unassignedCreatures);
        return creatureList;
    }

    //                          SORTING FUNCTIONS
    public void sortCreaturesByName() {
        for (int i = 0; i < parties.size(); i++) {
            Party p = parties.get(i);
            p.sortCreaturesByName();
        }
        Collections.sort(unassignedCreatures, Creature.CompareByName);
    }
    public void sortCreaturesByEmpathy() {
        for (int i = 0; i < parties.size(); i++) {
            Party p = parties.get(i);
            p.sortCreaturesByEmpathy();
        }
        Collections.sort(unassignedCreatures, Creature.CompareByEmpathy);
    }
    public void sortCreaturesByFear() {
        for (int i = 0; i < parties.size(); i++) {
            Party p = parties.get(i);
            p.sortCreaturesByFear();
        }
        Collections.sort(unassignedCreatures, Creature.CompareByFear);
    }
    public void sortCreaturesByCarryingCapacity() {
        for (int i = 0; i < parties.size(); i++) {
            Party p = parties.get(i);
            p.sortCreaturesByCarryingCapacity();
        }
        Collections.sort(unassignedCreatures, Creature.CompareByCarryingCapacity);
    }

    public void sortTreasuresByWeight(){
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            c.sortTreasureByWeight();
        }
        Collections.sort(unassignedTreasures, Treasure.CompareByWeight);
    }
    public void sortTreasuresByValue(){
        ArrayList<Creature> creatureList = getAllCreatures();
        for (Creature c: creatureList){
            c.sortTreasureByValue();
        }
        Collections.sort(unassignedTreasures, Treasure.CompareByValue);
    }

    //                          TREE
    public void addTreeNodes(DefaultMutableTreeNode parent) {

        // create party nodes
        DefaultMutableTreeNode pNode = new DefaultMutableTreeNode("Parties");
        // loop through parties calling their addTreeNodes
        for (Party p : parties)
            p.addTreeNodes(pNode);
        parent.add(pNode);


        // create treasure nodes under "treasures" node
        DefaultMutableTreeNode treasures = new DefaultMutableTreeNode("Unassigned Treasures");
        for (Treasure t : unassignedTreasures)
            t.addTreeNodes(treasures);
        parent.add(treasures);
        DefaultMutableTreeNode cNode = new DefaultMutableTreeNode("Wandering Monster");
        for (Creature c : unassignedCreatures)
            c.addTreeNodes(cNode);
        parent.add(cNode);
        DefaultMutableTreeNode artifacts = new DefaultMutableTreeNode("Unassigned Artifacts");
        for (Artifact a : unassignedArtifacts)
            a.addTreeNodes(artifacts);
        parent.add(artifacts);
    }

    public void setupResourcePoolDisplay(JPanel panel) {
        for (Party p : parties)
            p.pool.setupDisplay(panel);
    }

    public String toString() {
        String st = "Cave.toString:\nThe Parties\n";
        for (Party p : parties)
            st += p + "\n";
        st += "\n+++++++\nThe unassociated unassignedCaveElements:\n";
        for (Treasure t : unassignedTreasures)
            st += t + "\n";
        return st;

    }
}
