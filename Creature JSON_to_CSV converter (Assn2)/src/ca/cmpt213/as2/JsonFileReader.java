package ca.cmpt213.as2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * JsonFileReader class models the reader for json
 * file. Data includes list of json files. It supports
 * extracting all json files from directory, reading
 * json files, checking the file format, and placing
 * tokimons to the teams.
 */
public class JsonFileReader {
    ArrayList<File> listOfJson = new ArrayList<>();        // List to store json files

    public ArrayList<File> getListOfJson() {
        return listOfJson;
    }

    public void populateJsonList(File path) {
        if (path.isDirectory()) {
            File[] insidePath = path.listFiles();   // Open the directory files
            for (File newFile : insidePath) {
                populateJsonList(newFile);          // Recursion: call function unless is not directory
            }
        } else {// The base case: file is not a directory
            if (path.getAbsolutePath().toLowerCase().endsWith(".json")) {   // FileFormat
                listOfJson.add(path);       // Add json files if found
            }
        }
    }

    public void extractInfoFromJson(File jsonFile, ArrayList<TokimonTeam> teamList) throws FileNotFoundException {
        JsonElement fileAttribute = JsonParser.parseReader(new FileReader(jsonFile));    // Json reader
        JsonObject fileObject = fileAttribute.getAsJsonObject();                         // Json object of whole file

        String leaderID = "";                       // ID of the team leader
        String fromToki;                            // From Toki for CSV
        String toToki;                              // To Toki for CSV
        String extraComments;                       // Extra comment
        int counter = 0;                            // To find the leader

        // If file has following attributes it is correct
        boolean validFormat = fileObject.has("team") &&
                fileObject.has("extra_comments");
        // Exit the program to prevent error in reading invalid json
        if (!validFormat) {
            exitReader(jsonFile);
        }

        // Object for team array
        JsonArray newArray = fileObject.get("team").getAsJsonArray();

        for (JsonElement teamElement : newArray) {
            JsonObject teamJsonObject = teamElement.getAsJsonObject();
            // The list containing information about one tokimon
            ArrayList<String> oneTokimon = new ArrayList<>();

            // If object has following attributes it is correct
            boolean isValid = teamJsonObject.has("id") &&
                    teamJsonObject.has("name") &&
                    teamJsonObject.has("compatibility");
            // Exit the program to prevent error in reading invalid json
            if (!isValid) {
                exitReader(jsonFile);
            }

            // The leader
            if (counter == 0) {
                leaderID = teamJsonObject.get("id").getAsString();
                fromToki = teamJsonObject.get("id").getAsString();
                toToki = "-";
                extraComments = fileObject.get("extra_comments").getAsString();    // Json file extra comments
            } else {    // The leader followers
                fromToki = leaderID;
                toToki = teamJsonObject.get("id").getAsString();
                extraComments = "";
            }

            // Get json object to get @score and @comment
            JsonObject compatibility = teamJsonObject.get("compatibility").getAsJsonObject();

            // If object has following attributes it is correct
            isValid = compatibility.has("score") &&
                      compatibility.has("comment") &&
                      compatibility.get("score").getAsDouble() >= 0;
            // Exit the program to prevent error in reading invalid json
            if (!isValid) {
                exitReader(jsonFile);
            }

            // Edit information about the tokimon
            oneTokimon.add("");                                                         // Team #
            oneTokimon.add(fromToki);                                                   // From tokimon id
            oneTokimon.add(toToki);                                                     // To tokimon id
            oneTokimon.add(compatibility.get("score").getAsString());                   // Score
            char ch='"';                                                                // Char to put comment in ""
            oneTokimon.add(ch +
                    compatibility.get("comment").getAsString() + ch);                   // Comment
            oneTokimon.add("");                                                         // Empty field
            oneTokimon.add(extraComments);                                              // Extra field

            // Add the new tokimon to the list
            String name = teamJsonObject.get("name").getAsString();
            String association = name.substring(0, 5);                  // Team association tokimon belongs to

            ArrayList<ArrayList<String>> oneTeamTokimons = new ArrayList<>();

            boolean noTeamExists = true;
            for (TokimonTeam tokimonTeam : teamList) {
                if (association.equals(tokimonTeam.getAssociation())) {
                    tokimonTeam.add(oneTokimon);    // Tokimon placed to a team
                    noTeamExists = false;           // Team found
                }
            }

            // Create new team when no associations found
            if (noTeamExists) {
                oneTeamTokimons.add(oneTokimon);
                teamList.add(new TokimonTeam(oneTeamTokimons, association));
            }
            counter++;      // Leader tracker
        }
    }

    private void exitReader(File jsonFile) {
        // Prompt where issue happened
        System.out.println("ERROR invalid json format in file> " +
                jsonFile.toString());
        System.exit(-1);            // Exit, error found
    }
}
