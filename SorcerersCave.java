/** Patrick Raley
 *  CMSC335 OOP
 *  Project Final Sorcerers Cave
 */
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SorcerersCave extends JFrame {
    static final long serialVersionUID = 123L;
    JTextArea jta = new JTextArea();
    JComboBox<String> searchTypeCmbBox;
    JComboBox<String> sortMethodCmbBox;
    JTextField searchTxtField;
    JPanel toolBarPanel;
    JPanel treePanel;
    JPanel jobPanel;
    JPanel poolPanel;
    Cave cave = new Cave();
    JTree jtree;
    DefaultMutableTreeNode treeRoot;
    DefaultTreeModel treeModel;

    boolean resourcePoolsSetup = false;

    public SorcerersCave() {
        //                           SETUP GUI/PANEL/BUTTONS
        setTitle("Sorcerer's Cave");
        setVisible(true);
        setSize(950, 500);

        JToolBar toolBar = new JToolBar();
        toolBarAddButtonsAndListeners(toolBar);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        //JScrollPane jsp = new JScrollPane(jta);
        //add(jsp, BorderLayout.EAST);

        treeRoot = new DefaultMutableTreeNode("Cave");
        treeModel = new DefaultTreeModel(treeRoot);
        jtree = new JTree(treeModel);
        jtree.setRootVisible(false);
        JScrollPane jsp2 = new JScrollPane(jtree);

        toolBarPanel = new JPanel();
        add(toolBarPanel, BorderLayout.PAGE_START);
        toolBarPanel.add(toolBar, BorderLayout.PAGE_START);

        treePanel = new JPanel(new BorderLayout());
        treePanel.setMaximumSize(new Dimension(1050, 200));
        add(treePanel, BorderLayout.CENTER);
        treePanel.add(jsp2);


        poolPanel = new JPanel();
        poolPanel.setLayout(new GridLayout(0,5, 5, 5));

        JPanel poolPanelWrapper = new JPanel();
        add(poolPanelWrapper , BorderLayout.LINE_END);
        JScrollPane poolPanelScrollPane = new JScrollPane(poolPanel);
        poolPanelScrollPane.getViewport().setPreferredSize(new Dimension(800, 250));
        poolPanelWrapper.add(poolPanelScrollPane);

        jobPanel = new JPanel();
        jobPanel.setLayout(new GridLayout(0,5, 5, 5));

        JPanel jobPanelWrapper = new JPanel();
        add(jobPanelWrapper , BorderLayout.PAGE_END);
        JScrollPane jobPanelScrollPane = new JScrollPane(jobPanel);
        jobPanelScrollPane.getViewport().setPreferredSize(new Dimension(935, 150));
        jobPanelWrapper.add(jobPanelScrollPane);



        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void toolBarAddButtonsAndListeners(JToolBar toolBar) {
        Font serifPlain = new Font("Serif", Font.PLAIN, 12);
        Font serifBold = new Font("Serif", Font.BOLD, 12);

        JButton findReadFileBtn = new JButton("Find/Load File");
        findReadFileBtn.setFont(serifBold);
        JButton displayCaveBtn = new JButton("Display");
        displayCaveBtn.setFont(serifBold);
        toolBar.add(findReadFileBtn);
        toolBar.add(displayCaveBtn);
        toolBar.addSeparator();
        toolBar.addSeparator();

        JLabel searchTxtFieldLbl = new JLabel("Search Method & Value ");
        searchTxtFieldLbl.setFont(serifBold);
        toolBar.add(searchTxtFieldLbl);
        searchTypeCmbBox = new JComboBox<String>();
        searchTypeCmbBox.addItem("Party Index");
        searchTypeCmbBox.addItem("Party Name");
        searchTypeCmbBox.addItem("Creature Index");
        searchTypeCmbBox.addItem("Creature Type");
        searchTypeCmbBox.addItem("Creature Name");
        searchTypeCmbBox.addItem("Treasure Index");
        searchTypeCmbBox.addItem("Treasure Type");
        searchTypeCmbBox.addItem("Artifact Index");
        searchTypeCmbBox.addItem("Artifact Type");
        searchTypeCmbBox.addItem("Artifact Name");
        searchTypeCmbBox.setFont(serifPlain);
        toolBar.add(searchTypeCmbBox);
        searchTxtField = new JTextField(10);
        searchTxtField.setFont(serifPlain);
        toolBar.add(searchTxtField);
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(serifBold);
        toolBar.add(searchBtn);
        toolBar.addSeparator();
        toolBar.addSeparator();

        JLabel sortTxtLbl = new JLabel("Sorting Method ");
        sortTxtLbl.setFont(serifBold);
        toolBar.add(sortTxtLbl);
        sortMethodCmbBox = new JComboBox<String>();
        sortMethodCmbBox.addItem("Creature by Name");
        //jcb2.addItem("Creature by Age");
        //jcb2.addItem("Creature by Height");
        //jcb2.addItem("Creature by Weight");
        sortMethodCmbBox.addItem("Creature by Empathy");
        sortMethodCmbBox.addItem("Creature by Fear");
        sortMethodCmbBox.addItem("Creature by Carrying Capacity");
        sortMethodCmbBox.addItem("Treasure by Weight");
        sortMethodCmbBox.addItem("Treasure by Value");
        sortMethodCmbBox.setFont(serifPlain);
        toolBar.add(sortMethodCmbBox);
        JButton sortBtn = new JButton("Sort");
        sortBtn.setFont(serifBold);
        toolBar.add(sortBtn);

        //                              ACTION LISTENERS
        findReadFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFile();
            }
        });
        displayCaveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCave();
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchTypeCmbBox.getSelectedIndex(), searchTxtField.getText());
            }
        });
        sortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sort(sortMethodCmbBox.getSelectedIndex());
            }
        });
    }

        //                              BUTTON ACTIONS
    public void readFile() {
        jta.setText("File Read\n");
        JFileChooser fc = new JFileChooser(".");

            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                readCave(file);
            }
    }

    public void readCave(File file){
        BufferedReader br = null;
        cave = new Cave();
        System.out.println(file);
        HashMap<Integer,Party> partyMap = new HashMap<Integer,Party>();
        HashMap<Integer,Creature> creatureMap = new HashMap<Integer,Creature>();
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(file));

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                if(!sCurrentLine.startsWith("//") && (sCurrentLine.length() > 0) ) {
                    char caveElementType = sCurrentLine.charAt(0);
                    switch (caveElementType){
                        case 'p': loadParty(sCurrentLine, partyMap);
                            break;
                        case 'c': loadCreature(sCurrentLine, creatureMap, partyMap);
                            break;
                        case 't': loadTreasure(sCurrentLine, creatureMap);
                            break;
                        case 'a': loadArtifact(sCurrentLine, creatureMap);
                            break;
                        case 'j':{
                            if (!resourcePoolsSetup)
                                setupResourcePoolDisplay();
                            loadJob(sCurrentLine, creatureMap);
                        }
                            break;
                        default:
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        displayCave();
    }

    public void displayCave() {
        jta.setText("" + cave);

        treeRoot.removeAllChildren();
        cave.addTreeNodes(treeRoot);

        treeModel.reload();
    }

    public void search(int typeIndex, String target) {
        String output = "processing error: " + typeIndex + " " + target;

        try {
            switch (typeIndex) {
                case 0: {
                    Party p = cave.findPartyByIndex(Integer.valueOf(target));
                    if (p == null)
                        output = "Party not found for index " + target;
                    else{
                        treeRoot.removeAllChildren();
                        p.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 1:{
                    Party p = cave.findPartyByName(target);
                    if (p == null)
                        output = "Party not found by name " + target;
                    else{
                        treeRoot.removeAllChildren();
                        p.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 2: {
                    Creature c = cave.findCreatureByIndex(Integer.valueOf(target));
                    if (c == null)
                        output = "Creature not found for index " + target;
                    else{
                        treeRoot.removeAllChildren();
                        c.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 3:{
                    ArrayList<Creature> list = cave.findCreatureByType(target);
                    if (list.size() ==0)
                        output = "Creature not found by type " + target;
                    else{
                        treeRoot.removeAllChildren();
                        for(Creature c: list)
                            c.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 4:{
                    Creature c = cave.findCreatureByName(target);
                    if (c == null)
                        output = "Creature not found by name " + target;
                    else{
                        treeRoot.removeAllChildren();
                        c.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 5: {
                    Treasure t = cave.findTreasureByIndex(Integer.valueOf(target));
                    if (t == null)
                        output = "Treasure not found for index " + target;
                    else{
                        treeRoot.removeAllChildren();
                        t.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 6:{
                    ArrayList<Treasure> list = cave.findTreasureByType(target);
                    if (list.size() ==0)
                        output = "Treasure not found by type " + target;
                    else{
                        treeRoot.removeAllChildren();
                        for(Treasure t: list)
                            t.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 7: {
                    Artifact a = cave.findArtifactByIndex(Integer.valueOf(target));
                    if (a == null)
                        output = "Artifact not found for index " + target;
                    else{
                        treeRoot.removeAllChildren();
                        a.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 8:{
                    ArrayList<Artifact> list = cave.findArtifactByType(target);
                    if (list.size() == 0)
                        output = "Artifact not found by type " + target;
                    else{
                        treeRoot.removeAllChildren();
                        for(Artifact a: list)
                            a.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}
                case 9:{
                    Artifact a = cave.findArtifactByName(target);
                    if (a == null)
                        output = "Artifact not found by name " + target;
                    else{
                        treeRoot.removeAllChildren();
                        a.addTreeNodes(treeRoot);
                        treeModel.reload();
                    }
                    break;}

                default:
            }
        } catch (java.lang.NumberFormatException e) {}
        jta.setText(output);
    }

    public void sort(int typeIndex) {

            switch (typeIndex) {
                case 0:{
                    cave.sortCreaturesByName();
                    break;}
                //case 1:{ //("Creature by Age")
                //case 2:{ //("Creature by Height");
                //case 3:{ //("Creature by Weight");
                case 1:{
                    cave.sortCreaturesByEmpathy();
                    break;}
                case 2:{
                    cave.sortCreaturesByFear();
                    break;}
                case 3:{
                    cave.sortCreaturesByCarryingCapacity();
                    break;}
                case 4:{
                    cave.sortTreasuresByWeight();
                    break;}
                case 5:{
                    cave.sortTreasuresByValue();
                    break;}

                default:
            }

        displayCave();
    }

    public static void main(String args[]) {
        SorcerersCave sc = new SorcerersCave();
    }



        //                              LOADING AND CREATING
    private void loadParty(String source, HashMap<Integer,Party> partyMap){
        StringTokenizer tokenizer = new StringTokenizer(source, ":");
        String ignoretoken = tokenizer.nextToken();

        String partyIndexString = tokenizer.nextToken().trim();
        int partyIndex = Integer.valueOf(partyIndexString);

        String partyName = tokenizer.nextToken().trim();

        Party p = new Party(partyIndex, partyName);
        cave.parties.add(p);
        partyMap.put(p.index, p);
    }

    private void loadCreature(String source, HashMap<Integer,Creature> creatureMap, HashMap<Integer,Party> partyMap){
        StringTokenizer tokenizer = new StringTokenizer(source, ":");
        String ignoretoken = tokenizer.nextToken();

        String creatureIndexString = tokenizer.nextToken().trim();
        int creatureIndex = Integer.valueOf(creatureIndexString);

        String creatureType = tokenizer.nextToken().trim();

        String creatureName = tokenizer.nextToken().trim();

        String partyIndexString = tokenizer.nextToken().trim();
        int partyIndex = Integer.valueOf(partyIndexString);

        String creatureEmpathyString = tokenizer.nextToken().trim();
        int creatureEmpathy = Integer.valueOf(creatureEmpathyString);

        String creatureFearString = tokenizer.nextToken().trim();
        int creatureFear = Integer.valueOf(creatureFearString);

        String creatureCarryingCapacityString = tokenizer.nextToken().trim();
        int creatureCarryingCapacity = Integer.valueOf(creatureCarryingCapacityString);

        if (partyIndex == 0){
            Creature c = new Creature(null , creatureIndex, creatureType, creatureName, 0, creatureEmpathy, creatureFear, creatureCarryingCapacity);
            cave.unassignedCreatures.add(c);
            creatureMap.put(creatureIndex, c);
        }
        else {
            Party p = partyMap.get(partyIndex);
            Creature c = new Creature(p, creatureIndex, creatureType, creatureName, partyIndex, creatureEmpathy, creatureFear, creatureCarryingCapacity);
            p.members.add(c);
            creatureMap.put(creatureIndex, c);
        }
    }

    private void loadTreasure(String source, HashMap<Integer,Creature> creatureMap) {
        StringTokenizer tokenizer = new StringTokenizer(source, ":");
        String ignoretoken = tokenizer.nextToken();

        String treasureIndexString = tokenizer.nextToken().trim();
        int treasureIndex = Integer.valueOf(treasureIndexString);

        String treasureType = tokenizer.nextToken().trim();

        String creatureIndexString = tokenizer.nextToken().trim();
        int creatureIndex = Integer.valueOf(creatureIndexString);

        String treasureWeightString = tokenizer.nextToken().trim();
        float weight = Float.valueOf(treasureWeightString);

        String treasureValueString = tokenizer.nextToken().trim();
        int value = Integer.valueOf(treasureValueString);

        if (creatureIndex == 0){
            cave.unassignedTreasures.add(new Treasure("", treasureIndex, treasureType, creatureIndex, weight, value));
        }
        else {
            Creature c = creatureMap.get(creatureIndex);
            c.treasures.add(new Treasure("", treasureIndex, treasureType, creatureIndex, weight, value));
        }
    }

    private void loadArtifact(String source, HashMap<Integer,Creature> creatureMap){
        StringTokenizer tokenizer = new StringTokenizer(source, ":");
        String ignoretoken = tokenizer.nextToken();

        String artifactIndexString = tokenizer.nextToken().trim();
        int artifactIndex = Integer.valueOf(artifactIndexString);

        String artifactType = tokenizer.nextToken().trim();

        String creatureIndexString = tokenizer.nextToken().trim();
        int creatureIndex = Integer.valueOf(creatureIndexString);

        String artifactName = "";

        if (tokenizer.hasMoreTokens())
            artifactName = tokenizer.nextToken().trim();


        if (creatureIndex == 0){
            cave.unassignedArtifacts.add(new Artifact(artifactIndex, artifactType, creatureIndex, artifactName));
        }
        else {
            Creature c = creatureMap.get(creatureIndex);
            Artifact a = new Artifact(artifactIndex, artifactType, creatureIndex, artifactName);
            c.artifacts.add(a);
            if (c.party != null)
                c.party.pool.addResource(a);
        }
    }

    private void setupResourcePoolDisplay() {
        System.out.println("setupResourcePoolDisplay");
        cave.setupResourcePoolDisplay(poolPanel);
        resourcePoolsSetup = true;
    }
    private void loadJob(String source, HashMap<Integer,Creature> creatureMap) {
        Scanner scanner = new Scanner(source);
        scanner.useDelimiter("\\s*:\\s*");
        Job job = new Job(creatureMap, jobPanel, scanner);

        System.out.println(job.toString());
    }


}