package ca.cmpt213.as2;


import java.io.*;
import java.util.ArrayList;

/**
 * TokimonProcessor class takes directories arguments
 * to create the output .csv file. It supports processing
 * arguments and writing on csv file.
 */
public class TokimonProcessor {

    public static void main(String[] args) throws FileNotFoundException {

        // Checking if exactly 2 arguments were passed to run the program
        if (args.length != 2) {
            System.out.println("ERROR! Invalid number of arguments given");
            System.out.println("1. Pass directory with json files");
            System.out.println("2. Pass directory to output CSV file to.");
            System.exit(-1);
        }

        // Get the directory of the inputs
        File setOfInputs = new File(args[0]);
        // Check whether it is directory to avoid FileNotFound
        if(!setOfInputs.isDirectory()) {
            System.out.println("ERROR! Invalid inputs directory path entered");
            System.exit(-1);
        }

        // Get the directory to create the csv file on
        File csvOutput = new File(args[1]);
        // Check whether it is directory to avoid FileNotFound
        if(!csvOutput.isDirectory()) {
            System.out.println("ERROR! Invalid output directory path entered");
            System.exit(-1);
        }

        JsonFileReader reader = new JsonFileReader();       // Instantiate a class to perform reading
        reader.populateJsonList(setOfInputs);               // Populate files to have the list of json files
        ArrayList<File> jsonFiles =                         // The list to store json
                reader.getListOfJson();

        // Check to avoid IndexOutOfBound
        if (jsonFiles.isEmpty()) {
            System.out.println("ERROR! json files not found");
            System.exit(-1);
        }

        // The list for teams of tokimons
        ArrayList<TokimonTeam> listOfteams = new ArrayList<>();
        // From every json extract information
        for (File jsonFile : jsonFiles) {
            reader.extractInfoFromJson(jsonFile, listOfteams);      // Extracted and placed to the team
        }

        // Sorting the teams of tokimons by "id"
        for (TokimonTeam team : listOfteams) {
            team.sortTeam();
        }

        // Create and output the teams to the file
        makeCSV(csvOutput, listOfteams);
    }

    public static void makeCSV(File directory, ArrayList<TokimonTeam> teamList) {
        try {
            File newCsvFile = new File(directory, "team_info.csv");     // Directory to place new file
            FileWriter outputFile = new FileWriter(newCsvFile);              // FileWriter class
            PrintWriter out = new PrintWriter(outputFile);                   // Printing to the file

            String fileHeader = "Team#,From Toki,To Toki,Score,Comment,,Extra";     // The header for the file
            out.println(fileHeader);

            int teamNumber = 1;
            int count = 0;      // Team counter to print commas after team printed

            for (TokimonTeam team : teamList) {
                out.println("Team " + teamNumber++ + ",,,,,,");             // Team header
                for (ArrayList<String> strings : team.getTokimonList()) {
                    for (int i = 0; i < strings.size() - 1; i++) {
                        out.print(strings.get(i) + ",");                    // Printing the tokimon information
                    }
                    // Printing last without comma to obey to CSV format
                    out.print(strings.get(strings.size() - 1));
                    out.println();
                }
                // The last team need no line of commas
                if (count == teamList.size() - 1) {
                    break;
                }
                out.println(",,,,,,");
                count++;
            }

            out.flush();            // Flush the stream
            out.close();            // Close the stream

        } catch (Exception E) {
            // File path is not a directory
            System.err.println("Sorry exception " + E + " was found");
        }
    }
}
