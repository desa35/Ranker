package Ranker.GUI;

import Ranker.*;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static java.util.Collections.shuffle;

public class Ranker extends JFrame{
    private JPanel contentPane;
    private JButton newEntry;
    private JButton comparisonEntry;
    private JLabel decisionPrompt;
    private JLabel nameNewEntry;
    private JLabel nameComparisonEntry;
    private JLabel descNewEntry;
    private JLabel descComparisonEntry;

    public void RankingAlgorithm() {


        setTitle("Ranker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        decisionPrompt.setText("Welcher dieser beiden ist besser?");

        setLocationRelativeTo(null);

        setVisible(true);

        HashMap<Integer, String> entries = new HashMap<Integer, String>();
        HashMap<Integer, String> descriptions = new HashMap<Integer, String>();
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
                descriptions.put(entryNumber, desc.trim());
                entryNumber++;
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Sets up what will be the final Array containing the rankings
        ArrayList<String> finalRankedList = new ArrayList<>();
        ArrayList<String> tempRankedList = new ArrayList<>();

        // Sets up number array for randomization
        List<Integer> whichEntryNext = new ArrayList<>();

        // Populates with numbers from 1 to amount of list entries and shuffles them afterwards
        for (int i = 1; i <= entries.size(); i++) {
            whichEntryNext.add(i);
        }
        shuffle(whichEntryNext);

        int newEntryIndex = 0;
        int rankedEntryIndex = 0;
        int middleEntryOfRankedIndex = 0;

        boolean needNewEntry = true;

        String newEntryName = "";
        String rankedEntryName;

        while(finalRankedList.size() < entries.size()) {

            if (needNewEntry) {
                newEntryIndex = whichEntryNext.remove(0);
                newEntryName = entries.get(newEntryIndex);
                tempRankedList.addAll(finalRankedList);
                needNewEntry = false;
            }

            // Adds newly chosen list entry to final ranking (First entry so no choice required)
            if (finalRankedList.isEmpty()) {
                finalRankedList.add(entries.get(newEntryIndex));
                needNewEntry = true;
                continue;
            }

            // Skips calculation of remaining entry if condition is met, otherwise checks for "middle" entry
            if (finalRankedList.size() == 1) {
                rankedEntryName = finalRankedList.get(0);
            } else {

                middleEntryOfRankedIndex = (int) Math.floor((tempRankedList.size() - 1) / 2.0);
                rankedEntryName = tempRankedList.get(middleEntryOfRankedIndex);
                rankedEntryIndex = tempRankedList.indexOf(rankedEntryName);
                rankedEntryName = tempRankedList.get(rankedEntryIndex);
            }

            System.out.println("Welche Folge ist die bessere, 1 oder 2?");
            System.out.println(newEntryName + " oder " + rankedEntryName);
//            nameNewEntry.setText(newEntryName);

            boolean decisionValid = false;

            while (!decisionValid) {

                Scanner sc = new Scanner(System.in);
                String decision = sc.nextLine();

                // 1 = new entry is better than current comparison; 2 = new entry is worse than current comparison
                if (decision.equals("1")) {

                    if (tempRankedList.size() == 1) {
                        finalRankedList.add(finalRankedList.indexOf(rankedEntryName) + 1, entries.get(newEntryIndex));
                        needNewEntry = true;
                        tempRankedList.clear();
                    } else {

                        // Removes irrelevant half of the list (Anything worse than current comparison including itself)
                        for (int i = tempRankedList.size(); i >= 0; i--) {
                            if (rankedEntryIndex >= i) {
                                tempRankedList.remove(i);
                            }
                        }

                    }

                    decisionValid = true;

                } else if (decision.equals("2")) {

                    if (middleEntryOfRankedIndex == 0) {
                        finalRankedList.add(finalRankedList.indexOf(rankedEntryName), entries.get(newEntryIndex));
                        needNewEntry = true;
                        tempRankedList.clear();
                    } else {

                        // Removes irrelevant half of the list (Anything better than the current comparison including itself)
                        for (int i = tempRankedList.size() - 1; i >= 0; i--) {
                            if (rankedEntryIndex <= i) {
                                tempRankedList.remove(i);
                            }
                        }
                    }

                    decisionValid = true;

                } else {

                    System.out.println("Nur 1 oder 2 sind als Auswahl möglich");

                }
            }

            // Temporary solution to check final list. Will be replaced / removed in the future
            System.out.println("fRL content: " + finalRankedList);
        }
    }


}
