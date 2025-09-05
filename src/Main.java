import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

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

            System.out.println(episodes);
            System.out.println(descriptions);
            sc.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}