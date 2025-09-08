import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

public class Main {
    public static void main(String[] args) {

        HashMap<Integer, String> episodes = new HashMap<Integer, String>();
        HashMap<Integer, String> descriptions = new HashMap<Integer, String>();
        try {
            File myObj = new File("test.tsv");
            FileReader input = new FileReader(myObj);
            Scanner sc = new Scanner(input);

            String name;
            String desc;
            int episodeNumber = 1;

            sc.useDelimiter("\t");
            while (sc.hasNextLine()) {
                name = sc.next();
                episodes.put(episodeNumber, name);
                desc = sc.nextLine();
                descriptions.put(episodeNumber, desc.trim());
                episodeNumber++;
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Sets up what will be the final Array containing the rankings
        ArrayList<String> finalRankedList = new ArrayList<>();

        // Sets up number array for randomization
        List<Integer> whichEpisodeNext = new ArrayList<>();

        // Populates with numbers from 1 to amount of list entries
        for (int i = 1; i <= episodes.size(); i++) {
            whichEpisodeNext.add(i);
        }

        shuffle(whichEpisodeNext);

        int rankNewEpisodeIndex = 0;
        int alreadyRankedIndex = 0;
        int middleEntryOfRanked;

        boolean needNewEpisode = true;

        String newEpisode = "";
        String rankedEpisode = "";

        while(finalRankedList.size() < episodes.size()) {

            if (needNewEpisode) {
                rankNewEpisodeIndex = whichEpisodeNext.remove(0);
                newEpisode = episodes.get(rankNewEpisodeIndex);
//                needNewEpisode = false;
            }

            // Adds newly chosen list entry to final ranking (First entry so no choice required)
            if (finalRankedList.isEmpty()) {
                finalRankedList.add(episodes.get(rankNewEpisodeIndex));
            }

            if (finalRankedList.size() == 1) {
                rankedEpisode = finalRankedList.get(0);
            } else {
                middleEntryOfRanked = (int) Math.ceil(finalRankedList.size() / 2.0);

                alreadyRankedIndex = finalRankedList.indexOf(finalRankedList.get(middleEntryOfRanked));
                rankedEpisode = finalRankedList.get(alreadyRankedIndex);
            }

                System.out.println("Welche Folge ist die bessere, 1 oder 2?");
                System.out.println(newEpisode + " oder " + rankedEpisode);

            boolean decisionValid = false;

            while (!decisionValid) {

                Scanner sc = new Scanner(System.in);
                String decision = sc.nextLine();

                if (decision.equals("1")) {

                    finalRankedList.add(finalRankedList.indexOf(finalRankedList.get(alreadyRankedIndex)) + 1, episodes.get(rankNewEpisodeIndex));
                    decisionValid = true;

                } else if (decision.equals("2")) {

                    finalRankedList.add(finalRankedList.indexOf(finalRankedList.get(alreadyRankedIndex)), episodes.get(rankNewEpisodeIndex));
                    decisionValid = true;

                } else {

                    System.out.println("Nur 1 oder 2 sind als Auswahl möglich");

                }
            }

            System.out.println(finalRankedList);
        }


    }
}