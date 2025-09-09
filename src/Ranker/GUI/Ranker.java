package Ranker.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static java.util.Collections.shuffle;

public class Ranker extends JFrame{
    private JPanel contentPane;
    private JButton newEntryWins;
    private JButton comparisonEntryWins;
    private JLabel decisionPrompt;
    private JLabel nameNewEntry;
    private JLabel nameComparisonEntry;
    private JLabel descNewEntry;
    private JLabel descComparisonEntry;

    HashMap<Integer, String> entries = new HashMap<Integer, String>();
    HashMap<String, String> descriptions = new HashMap<String, String>();

    // Sets up what will be the final Array containing the rankings
    ArrayList<String> finalRankedList = new ArrayList<>();
    ArrayList<String> tempRankedList = new ArrayList<>();

    // Sets up number array for randomization
    List<Integer> whichEntryNext = new ArrayList<>();

    int newEntryIndex = 0;
    int comparisonEntryIndex = 0;
    int middleEntryOfRankedIndex = 0;

    String newEntryName = "";
    String comparisonEntryName = "";

    String newEntryDesc = "";
    String comparisonEntryDesc = "";

    public void RankingAlgorithm() {

        setTitle("Ranker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        decisionPrompt.setText("Welcher dieser beiden Optionen ist besser?");

        setLocationRelativeTo(null);

        try {
            File myObj = new File("shortTest.tsv");
            FileReader input = new FileReader(myObj);
            Scanner sc = new Scanner(input);

            String name;
            String desc;
            int entryNumber = 1;

            sc.useDelimiter("\t");

            // Converts .tsv file into usable HasMaps
            while (sc.hasNextLine()) {
                name = sc.next();
                entries.put(entryNumber, name);
                desc = sc.nextLine();
                descriptions.put(entries.get(entryNumber), desc.trim());
                entryNumber++;
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("entries: " + entries);
        // Populates with numbers from 1 to amount of list entries and shuffles them afterwards
        for (int i = 1; i <= entries.size(); i++) {
            whichEntryNext.add(i);
        }
        shuffle(whichEntryNext);

        // No comparisons were left after the last round meaning the new Entry has been successfully ranked and a new one is required
        // Currently always true. Condition is for future "Save & Continue" functionality
//        if (tempRankedList.isEmpty() && !entries.isEmpty()) {
            newEntrySetup(entries, descriptions, tempRankedList, finalRankedList, whichEntryNext);
//        }

        // Adds newly chosen list entry to final ranking (First entry so no choice required)
        // Currently always true. Condition is for future "Save & Continue" functionality
//        if (finalRankedList.isEmpty()) {
            finalRankedList.add(entries.get(newEntryIndex));
            tempRankedList.clear();
//        }

        // Currently always true. Condition is for future "Save & Continue" functionality
//        if (tempRankedList.isEmpty() && !entries.isEmpty()) {
            newEntrySetup(entries, descriptions, tempRankedList, finalRankedList, whichEntryNext);
//        }

        getComparisonEntry(tempRankedList);
        setLabels(nameNewEntry, nameComparisonEntry, descNewEntry, descComparisonEntry, newEntryName, comparisonEntryName, newEntryDesc, comparisonEntryDesc);

        setVisible(true);

        newEntryWins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntryWins(entries, tempRankedList, finalRankedList, comparisonEntryName, newEntryIndex, comparisonEntryIndex);
                if (tempRankedList.isEmpty()) {
                    newEntrySetup(entries, descriptions, tempRankedList, finalRankedList, whichEntryNext);
                }
                setLabels(nameNewEntry, nameComparisonEntry, descNewEntry, descComparisonEntry, newEntryName, comparisonEntryName, newEntryDesc, comparisonEntryDesc);
            }
        });

        comparisonEntryWins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {comparisonEntryWins(entries, tempRankedList, finalRankedList, comparisonEntryName, newEntryIndex, comparisonEntryIndex, middleEntryOfRankedIndex);}
        });
    }

    private void newEntrySetup(HashMap entries, HashMap descriptions, ArrayList tempRankedList, ArrayList finalRankedList, List whichEntryNext) {
        newEntryIndex = (int) whichEntryNext.remove(0);
        newEntryName = (String) entries.get(newEntryIndex);
        newEntryDesc = (String) descriptions.get(newEntryName);
        tempRankedList.addAll(finalRankedList);
    }

    private void getComparisonEntry(ArrayList tempRankedList) {
        middleEntryOfRankedIndex = (int) Math.floor((tempRankedList.size() - 1) / 2.0);
        comparisonEntryName = (String) tempRankedList.get(middleEntryOfRankedIndex);
        comparisonEntryIndex = tempRankedList.indexOf(comparisonEntryName);
        comparisonEntryDesc = descriptions.get(comparisonEntryName);
    }

    private void setLabels(JLabel nameNewEntry, JLabel nameComparisonEntry, JLabel descNewEntry, JLabel descComparisonEntry, String newEntryName, String comparisonEntryName, String newEntryDesc, String comparisonEntryDesc) {
        nameNewEntry.setText(newEntryName);
        nameComparisonEntry.setText(comparisonEntryName);
        descNewEntry.setText(newEntryDesc);
        descComparisonEntry.setText(comparisonEntryDesc);
    }

    private void newEntryWins(HashMap entries, ArrayList tempRankedList, ArrayList finalRankedList, String comparisonEntryName, int newEntryIndex, int comparisonEntryIndex) {
        if (tempRankedList.size() == 1) {
                        finalRankedList.add(finalRankedList.indexOf(comparisonEntryName) + 1, entries.get(newEntryIndex));
                        tempRankedList.clear();
                    } else {

                        // Removes irrelevant half of the list (Anything worse than current comparison including itself)
                        for (int i = tempRankedList.size(); i >= 0; i--) {
                            if (comparisonEntryIndex >= i) {
                                tempRankedList.remove(i);
                            }
                        }

                    }
    }

    private void comparisonEntryWins(HashMap entries, ArrayList tempRankedList, ArrayList finalRankedList, String comparisonEntryName, int newEntryIndex, int comparisonEntryIndex, int middleEntryOfRankedIndex) {
        if (middleEntryOfRankedIndex == 0) {
                        finalRankedList.add(finalRankedList.indexOf(comparisonEntryName), entries.get(newEntryIndex));
                        tempRankedList.clear();
                    } else {

                        // Removes irrelevant half of the list (Anything better than the current comparison including itself)
                        for (int i = tempRankedList.size() - 1; i >= 0; i--) {
                            if (comparisonEntryIndex <= i) {
                                tempRankedList.remove(i);
                            }
                        }
                    }
    }

}