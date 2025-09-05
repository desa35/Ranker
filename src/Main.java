import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {

        HashMap<String, String> Liste = new HashMap<String, String>();
        try {
            File myObj = new File("test.txt");
            FileReader input = new FileReader(myObj);
            BufferedReader br = new BufferedReader(input);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("\"Episode\"")) {
                    String episodeName = br.readLine().trim();
                    String episodeDescription = br.readLine().trim();
                    Liste.put(episodeName, episodeDescription);
                }
            }
            br.close();
            System.out.println(Liste);
            System.out.println(Liste.size());
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}