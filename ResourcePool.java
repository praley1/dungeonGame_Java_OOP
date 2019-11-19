import javax.swing.*;
import java.awt.*;

/** Patrick Raley
 *  CMSC335 OOP
 *  Project Final Sorcerers Cave
 */

public class ResourcePool {

    Party party;

    int wandsAvailable;
    int potionsAvailable;
    int stonesAvailable;
    int weaponsAvailable;
    int wands;
    int potions;
    int stones;
    int weapons;

    JPanel panel = null;
    JLabel partyLabel;
    JLabel wandsLabel;
    JLabel potionsLabel;
    JLabel stonesLabel;
    JLabel weaponsLabel;

    public ResourcePool(Party p){
        party = p;
    }

    public boolean getResources(int wands, int potions, int stones, int weapons){
        if(wandsAvailable < wands || potionsAvailable < potions || stonesAvailable < stones || weaponsAvailable < weapons)
            return false;
        else {
            wandsAvailable -= wands;
            potionsAvailable -= potions;
            stonesAvailable -= stones;
            weaponsAvailable -= weapons;
            updateLabels();
            return true;
        }
    }
    public void returnResources(int wands, int potions, int stones, int weapons){
        wandsAvailable += wands;
        potionsAvailable += potions;
        stonesAvailable += stones;
        weaponsAvailable += weapons;
        updateLabels();
    }

    public void addResource(Artifact a){
        if(a.artifactType.compareToIgnoreCase("wand") == 0) {
            wandsAvailable++;
            wands++;
        }
        if(a.artifactType.compareToIgnoreCase("potion") == 0) {
            potionsAvailable++;
            potions++;
        }
        if(a.artifactType.compareToIgnoreCase("stone") == 0) {
            stonesAvailable++;
            stones++;
        }
        if(a.artifactType.compareToIgnoreCase("weapon") == 0) {
            weaponsAvailable++;
            weapons++;
        }
    }

    public void setupDisplay(JPanel panel) {
        this.panel = panel;
        partyLabel = new JLabel(party.name, SwingConstants.LEFT);
        wandsLabel = new JLabel("wands", SwingConstants.LEFT);
        potionsLabel = new JLabel("potions", SwingConstants.LEFT);
        stonesLabel = new JLabel("stones", SwingConstants.LEFT);
        weaponsLabel = new JLabel("weapons", SwingConstants.LEFT);


        partyLabel.setOpaque(true);
        partyLabel.setBackground(new Color(255, 204, 204));
        wandsLabel.setOpaque(true);
        wandsLabel.setBackground(new Color(229, 255, 204));
        potionsLabel.setOpaque(true);
        potionsLabel.setBackground(new Color(204, 255, 229));
        stonesLabel.setOpaque(true);
        stonesLabel.setBackground(new Color(229, 255, 204));
        weaponsLabel.setOpaque(true);
        weaponsLabel.setBackground(new Color(204, 255, 229));

        panel.add(partyLabel);
        panel.add(wandsLabel);
        panel.add(potionsLabel);
        panel.add(stonesLabel);
        panel.add(weaponsLabel);

        System.out.println("setupDisplay  " + party.name);
    }

    private void updateLabels() {
        wandsLabel.setText("Wands free:" + wandsAvailable + " of " + wands);
        potionsLabel.setText("Potions free:" + potionsAvailable + " of " + potions);
        stonesLabel.setText("Stones free:" + stonesAvailable + " of " + stones);
        weaponsLabel.setText("Weapons free:" + weaponsAvailable + " of " + weapons);
    }

    public String toString() {
        String st = "Resource pool" + "wands: " + wands+ "  " + "potions " + potions+ "  " + "stones: " +  stones + "  " + "weapons: " + weapons;
        return st;
    }

}
