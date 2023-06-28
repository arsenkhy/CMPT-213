package ca.cmpt213.ui;

import ca.cmpt213.model.Tokimon;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * TokimonApp class models the information about a
 * application UI. It supports getting, posting,
 * deleting, changing tokimons on the server as well
 * as displaying all of the tokimons
 */
public class TokimonApp extends Application {
    private ArrayList<Tokimon> tokimons = new ArrayList<>();
    public static final int WIDTH_SIZE = 170;                   // For images
    public static final int HEIGHT_SIZE = 120;                  // For images
    GridPane gridPane;                                          // For the tokimons list
    Button addNewBtn;                                           // Add tokimon button
    Stage primaryStage;                                         // Main stage
    VBox fullApp;                                               // Main page layout

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tokimon app");       // Title

        this.primaryStage = primaryStage;

        // Window text
        Text text = new Text();
        text.setText("Your Tokimons collection");
        text.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 18));

        // Gets the updated view of a gridpane
        gridPane = getUpdatedView();

        addNewBtn = new Button("ADD NEW");
        addNewBtn.setPrefSize(100, 50);

        fullApp = new VBox(text, gridPane, addNewBtn);
        fullApp.setAlignment(Pos.TOP_CENTER);
        fullApp.setPadding(new Insets(10));
        fullApp.setSpacing(20);

        Scene scene = new Scene(fullApp, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        addNewBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage subStage = new Stage();
                subStage.setTitle("Add Tokimon");

                // Make stage not cancelable
                subStage.initModality(Modality.WINDOW_MODAL);
                subStage.initOwner(primaryStage);

                Text text1 = new Text();
                text1.setText("Fill in the information");
                text1.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 18));

                // Gridpane for the list of attributes
                GridPane gp = new GridPane();

                // For taking inputs from user
                Text nameText = new Text("Name");
                TextField nameField = new TextField();
                HBox nameBox = new HBox(nameText, nameField);
                nameBox.setPadding(new Insets(10));
                nameBox.setSpacing(10);
                gp.add(nameBox, 0, 0);

                Text weightText = new Text("Weight");
                TextField weightField = new TextField();
                HBox weightBox = new HBox(weightText, weightField);
                weightBox.setPadding(new Insets(10));
                weightBox.setSpacing(10);
                gp.add(weightBox, 0, 1);

                Text heightText = new Text("Height");
                TextField heightField = new TextField();
                HBox heightBox = new HBox(heightText, heightField);
                heightBox.setPadding(new Insets(10));
                heightBox.setSpacing(10);
                gp.add(heightBox, 0, 2);

                Text flyText = new Text("Fly ability");
                TextField flyField = new TextField();
                HBox flyBox = new HBox(flyText, flyField);
                flyBox.setPadding(new Insets(10));
                flyBox.setSpacing(10);
                gp.add(flyBox, 0, 3);

                Text fireText = new Text("Fire ability");
                TextField fireField = new TextField();
                HBox fireBox = new HBox(fireText, fireField);
                fireBox.setPadding(new Insets(10));
                fireBox.setSpacing(10);
                gp.add(fireBox, 1, 0);

                Text electroText = new Text("Electrify ability");
                TextField electroField = new TextField();
                HBox electroBox = new HBox(electroText, electroField);
                electroBox.setPadding(new Insets(10));
                electroBox.setSpacing(10);
                gp.add(electroBox, 1, 1);

                Text freezeText = new Text("Freeze ability");
                TextField freezeField = new TextField();
                HBox freezeBox = new HBox(freezeText, freezeField);
                freezeBox.setPadding(new Insets(10));
                freezeBox.setSpacing(10);
                gp.add(freezeBox, 1, 2);

                Text strengthText = new Text("Strength");
                TextField strengthField = new TextField();
                HBox strengthBox = new HBox(strengthText, strengthField);
                strengthBox.setPadding(new Insets(10));
                strengthBox.setSpacing(10);
                gp.add(strengthBox, 1, 3);

                Text colorText = new Text("Color");
                TextField colorField = new TextField();
                HBox colorBox = new HBox(colorText, colorField);
                colorBox.setPadding(new Insets(10));
                colorBox.setSpacing(10);
                gp.add(colorBox, 0, 4);

                // Warning text for displaying the errors
                Text warningText = new Text("Error! invalid arguments");
                warningText.setVisible(false);

                Button submit = new Button("SUBMIT");
                submit.setPrefSize(80, 30);

                VBox layout = new VBox(text1, gp, warningText, submit);
                layout.setSpacing(10);
                layout.setAlignment(Pos.CENTER);
                layout.setPadding(new Insets(20));

                Scene subScene = new Scene(layout, 500, 400);
                subStage.setScene(subScene);
                subStage.show();

                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        // Check if inputs are empty
                        if (nameField.getText().isEmpty() || colorField.getText().isEmpty() ||
                                weightField.getText().isEmpty() || heightField.getText().isEmpty() ||
                                flyField.getText().isEmpty() || fireField.getText().isEmpty() ||
                                electroField.getText().isEmpty() || freezeField.getText().isEmpty() ||
                                strengthField.getText().isEmpty()) {
                            warningText.setText("Fill in all boxes!");
                            warningText.setVisible(true);
                        } else {
                            try {
                                // get all attributes
                                String name = nameField.getText();
                                String weight = weightField.getText();
                                String height = heightField.getText();
                                String fly = flyField.getText();
                                String electro = electroField.getText();
                                String fire = fireField.getText();
                                String freeze = freezeField.getText();
                                String strength = strengthField.getText();
                                String color = colorField.getText();

                                // URL connection
                                URL url = new URL("http://localhost:8080/api/tokimon/add");
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setDoOutput(true);
                                connection.setRequestMethod("POST");
                                connection.setRequestProperty("Content-Type", "application/json");

                                // Writing th json onto server
                                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                                wr.write("{\"abilityFire\":" + fire + ",\"abilityFly\":" + fly + ",\"strength\":" + strength + ",\"color\":\"" + color + "\",\"abilityFreeze\":" + freeze + ",\"name\":\"" + name + "\",\"weight\":" + weight + ",\"abilityElctrify\":" + electro + ",\"height\":" + height + "}");
                                wr.flush();
                                wr.close();

                                connection.connect();
                                int responseCode = connection.getResponseCode();
                                System.out.println(connection.getResponseCode());
                                connection.disconnect();

                                // Bad data past to a server
                                if (responseCode == 400) {
                                    warningText.setText("Passing illegal arguments");
                                    warningText.setVisible(true);
                                } else {
                                    // Update the view of all tokimons
                                    tokimons = new ArrayList<>();
                                    gridPane = getUpdatedView();

                                    fullApp = new VBox(text, gridPane, addNewBtn);
                                    fullApp.setAlignment(Pos.TOP_CENTER);
                                    fullApp.setPadding(new Insets(10));
                                    fullApp.setSpacing(20);

                                    Scene scene = new Scene(fullApp, 600, 600);
                                    primaryStage.setScene(scene);
                                    primaryStage.show();
                                    subStage.hide();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    // Class for clicking the one tokimon
    public class TokimonClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Stage subStage = new Stage();
            subStage.setTitle("Tokimon info");

            // Make stage not cancelable
            subStage.initModality(Modality.WINDOW_MODAL);
            subStage.initOwner(primaryStage);

            VBox cell = (VBox) mouseEvent.getSource();                    // Clicked image view
            Integer row = GridPane.getRowIndex(cell);                     // Position: row
            Integer column = GridPane.getColumnIndex(cell);               // Position: column

            Tokimon thisToki = new Tokimon();

            // Find taped tokimon
            for (Tokimon tokimon : tokimons) {
                if (tokimon.getRow() == row && tokimon.getColumn() == column) {
                    thisToki = tokimon;
                }
            }

            // Tokimon Name
            Text text1 = new Text();
            text1.setText(thisToki.getName());
            text1.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 18));

            // Tokimon image
            ImageView tokimonImage = new ImageView(getImageForToki(thisToki));
            tokimonImage.setFitWidth(WIDTH_SIZE);
            tokimonImage.setFitHeight(HEIGHT_SIZE);
            tokimonImage.setPreserveRatio(true);

            // The gridpane for the attributes
            GridPane gp = new GridPane();

            // For displaying and taking inputs from user
            Text nameText = new Text("Name");
            TextField nameField = new TextField();
            nameField.setText(thisToki.getName());
            nameField.setEditable(false);
            HBox nameBox = new HBox(nameText, nameField);
            nameBox.setPadding(new Insets(10));
            nameBox.setSpacing(10);
            gp.add(nameBox, 0, 0);

            Text weightText = new Text("Weight");
            TextField weightField = new TextField();
            weightField.setText(thisToki.getWeight() + "");
            weightField.setEditable(false);
            HBox weightBox = new HBox(weightText, weightField);
            weightBox.setPadding(new Insets(10));
            weightBox.setSpacing(10);
            gp.add(weightBox, 0, 1);

            Text heightText = new Text("Height");
            TextField heightField = new TextField();
            heightField.setText(thisToki.getHeight() + "");
            heightField.setEditable(false);
            HBox heightBox = new HBox(heightText, heightField);
            heightBox.setPadding(new Insets(10));
            heightBox.setSpacing(10);
            gp.add(heightBox, 0, 2);

            Text flyText = new Text("Fly ability");
            TextField flyField = new TextField();
            flyField.setText(thisToki.getAbilityFly() + "");
            flyField.setEditable(false);
            HBox flyBox = new HBox(flyText, flyField);
            flyBox.setPadding(new Insets(10));
            flyBox.setSpacing(10);
            gp.add(flyBox, 0, 3);

            Text fireText = new Text("Fire ability");
            TextField fireField = new TextField();
            fireField.setText(thisToki.getAbilityFire() + "");
            fireField.setEditable(false);
            HBox fireBox = new HBox(fireText, fireField);
            fireBox.setPadding(new Insets(10));
            fireBox.setSpacing(10);
            gp.add(fireBox, 1, 0);

            Text electroText = new Text("Electrify ability");
            TextField electroField = new TextField();
            electroField.setText(thisToki.getAbilityElctrify() + "");
            electroField.setEditable(false);
            HBox electroBox = new HBox(electroText, electroField);
            electroBox.setPadding(new Insets(10));
            electroBox.setSpacing(10);
            gp.add(electroBox, 1, 1);

            Text freezeText = new Text("Freeze ability");
            TextField freezeField = new TextField();
            freezeField.setText(thisToki.getAbilityFreeze() + "");
            freezeField.setEditable(false);
            HBox freezeBox = new HBox(freezeText, freezeField);
            freezeBox.setPadding(new Insets(10));
            freezeBox.setSpacing(10);
            gp.add(freezeBox, 1, 2);

            Text strengthText = new Text("Strength");
            TextField strengthField = new TextField();
            strengthField.setText(thisToki.getStrength() + "");
            strengthField.setEditable(false);
            HBox strengthBox = new HBox(strengthText, strengthField);
            strengthBox.setPadding(new Insets(10));
            strengthBox.setSpacing(10);
            gp.add(strengthBox, 1, 3);

            Text colorText = new Text("Color");
            TextField colorField = new TextField();
            colorField.setText(thisToki.getColor() + "");
            colorField.setEditable(false);
            HBox colorBox = new HBox(colorText, colorField);
            colorBox.setPadding(new Insets(10));
            colorBox.setSpacing(10);
            gp.add(colorBox, 0, 4);

            // Warning text
            Text warningText = new Text("Error! invalid arguments");
            warningText.setVisible(false);

            // Buttons
            Button change = new Button("CHANGE");
            change.setPrefSize(80, 30);
            Button delete = new Button("DELETE");
            delete.setPrefSize(80, 30);
            Button submit = new Button("SUBMIT");
            submit.setPrefSize(80, 30);
            submit.setVisible(false);

            // buttons container
            HBox buttonsBox = new HBox(delete, change, submit);
            buttonsBox.setSpacing(15);
            buttonsBox.setAlignment(Pos.CENTER);

            // The layout
            VBox layout = new VBox(text1, tokimonImage, gp, warningText, buttonsBox);
            layout.setSpacing(10);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));

            Scene subScene = new Scene(layout, 500, 500);
            subStage.setScene(subScene);
            subStage.show();

            // Change button
            change.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // Make all attributes changeable
                    nameField.setEditable(true);
                    weightField.setEditable(true);
                    heightField.setEditable(true);
                    flyField.setEditable(true);
                    fireField.setEditable(true);
                    electroField.setEditable(true);
                    freezeField.setEditable(true);
                    strengthField.setEditable(true);
                    colorField.setEditable(true);

                    change.setVisible(false);       // hide
                    submit.setVisible(true);        // show
                }
            });

            Tokimon finalThisToki = thisToki;
            // Submit button
            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // Check if inputs are empty
                    if (nameField.getText().isEmpty() || colorField.getText().isEmpty() ||
                            weightField.getText().isEmpty() || heightField.getText().isEmpty() ||
                            flyField.getText().isEmpty() || fireField.getText().isEmpty() ||
                            electroField.getText().isEmpty() || freezeField.getText().isEmpty() ||
                            strengthField.getText().isEmpty()) {
                        warningText.setText("Fill in all boxes!");
                        warningText.setVisible(true);
                    } else {
                        try {
                            // get all attributes
                            String name = nameField.getText();
                            String weight = weightField.getText();
                            String height = heightField.getText();
                            String fly = flyField.getText();
                            String electro = electroField.getText();
                            String fire = fireField.getText();
                            String freeze = freezeField.getText();
                            String strength = strengthField.getText();
                            String color = colorField.getText();

                            // URL connection
                            URL url = new URL("http://localhost:8080/api/tokimon/change/" + finalThisToki.getId());
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoOutput(true);
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("Content-Type", "application/json");

                            // Writing th json onto server
                            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                            wr.write("{\"abilityFire\":" + fire + ",\"abilityFly\":" + fly + ",\"strength\":" + strength + ",\"color\":\"" + color + "\",\"abilityFreeze\":" + freeze + ",\"name\":\"" + name + "\",\"weight\":" + weight + ",\"abilityElctrify\":" + electro + ",\"height\":" + height + "}");
                            wr.flush();
                            wr.close();

                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            System.out.println(connection.getResponseCode());
                            connection.disconnect();

                            // Bad data was passed
                            if (responseCode == 400) {
                                warningText.setText("Passing illegal arguments");
                                warningText.setVisible(true);
                            } else {
                                // Update the view of all tokimons
                                tokimons = new ArrayList<>();
                                gridPane = getUpdatedView();

                                Text text = new Text();
                                text.setText("Your Tokimons collection");
                                text.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 18));

                                fullApp = new VBox(text, gridPane, addNewBtn);
                                fullApp.setAlignment(Pos.TOP_CENTER);
                                fullApp.setPadding(new Insets(10));
                                fullApp.setSpacing(20);

                                Scene scene = new Scene(fullApp, 600, 600);
                                primaryStage.setScene(scene);
                                primaryStage.show();
                                subStage.hide();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            // Delete button
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        // URL connection
                        URL url = new URL("http://localhost:8080/api/tokimon/" + finalThisToki.getId());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");

                        connection.connect();
                        System.out.println(connection.getResponseCode());
                        connection.disconnect();

                        // Update the view of all tokimons
                        tokimons = new ArrayList<>();
                        gridPane = getUpdatedView();

                        Text text = new Text();
                        text.setText("Your Tokimons collection");
                        text.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 18));

                        fullApp = new VBox(text, gridPane, addNewBtn);
                        fullApp.setAlignment(Pos.TOP_CENTER);
                        fullApp.setPadding(new Insets(10));
                        fullApp.setSpacing(20);

                        Scene scene = new Scene(fullApp, 600, 600);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        subStage.hide();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public GridPane getUpdatedView() {
        // Get information from server
        getApiInfo();

        // New tokimons layout
        GridPane gridPane = new GridPane();

        int rowCounter = 0;
        int colCounter = 0;
        for (Tokimon tokimon : tokimons) {
            // Image for the tokimon
            Image image = getImageForToki(tokimon);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(WIDTH_SIZE);
            imageView.setFitHeight(HEIGHT_SIZE);
            imageView.setPreserveRatio(true);
            tokimon.setRow(rowCounter);             // Save row
            tokimon.setColumn(colCounter);          // Save column

            Text text = new Text();
            text.setText(tokimon.getName());
            text.setFont(Font.font("arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
            VBox vb = new VBox(imageView, text);
            vb.setAlignment(Pos.CENTER);
            vb.addEventHandler(MouseEvent.MOUSE_CLICKED, new TokimonClickHandler());

            gridPane.add(vb, colCounter, rowCounter);

            if (colCounter == 2) {
                colCounter = 0;
                rowCounter++;
            } else {
                colCounter++;
            }
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(50);
        gridPane.setVgap(30);

        return gridPane;
    }

    private Image getImageForToki(Tokimon tokimon) {
        // The balanced tokimon: difference between height and weight is within +- 20 points
        int pictureNumber = 1;

        // if tokimon height is bigger by 20 points than weight display tall tokimon
        if(tokimon.getHeight() - tokimon.getWeight() > 20) {
            pictureNumber = 3;
        }
        // if tokimon weight is bigger by 20 points than height display thick tokimon
        else if (tokimon.getWeight() - tokimon.getHeight() > 20) {
            pictureNumber = 2;
        }

        // Image reference: https://www.seekpng.com/ipng/u2r5o0w7u2w7w7y3_pokemon-clipart-vector/
        // Image reference: https://www.iconninja.com/go-game-play-pokemon-charcter-icon-572
        // Image reference: https://www.nicepng.com/ourpic/u2y3w7y3q8i1u2t4_pokemon-logo-black-and-white-pokemon-vectors-transparent/
        String color = tokimon.getColor().toLowerCase();
        if (pictureNumber == 1) {
            return switch (color) {
                case "red" -> new Image("file:img/tokimon1_red.png");
                case "blue" -> new Image("file:img/tokimon1_blue.png");
                case "yellow" -> new Image("file:img/tokimon1_yellow.png");
                case "green" -> new Image("file:img/tokimon1_green.png");
                default -> new Image("file:img/tokimon1.png");
            };
        } else if (pictureNumber == 2) {
            return switch (color) {
                case "red" -> new Image("file:img/tokimon2_red.png");
                case "blue" -> new Image("file:img/tokimon2_blue.png");
                case "yellow" -> new Image("file:img/tokimon2_yellow.png");
                case "green" -> new Image("file:img/tokimon2_green.png");
                default -> new Image("file:img/tokimon2.png");
            };
        } else {
            return switch (color) {
                case "red" -> new Image("file:img/tokimon3_red.png");
                case "blue" -> new Image("file:img/tokimon3_blue.png");
                case "yellow" -> new Image("file:img/tokimon3_yellow.png");
                case "green" -> new Image("file:img/tokimon3_green.png");
                default -> new Image("file:img/tokimon3.png");
            };
        }
    }

    private void getApiInfo() {
        try {
            URL url = new URL("http://localhost:8080/api/tokimon/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String output;
            output = br.readLine();

            // Extracting data from the string
            storeData(output);

            System.out.println(connection.getResponseCode());
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeData(String output) {
        ArrayList<String> oneObject = new ArrayList<>();
        int startIndex = 0;
        // all object on the server
        for (int i = 0; i < output.length(); i++) {
            if (output.charAt(i) == '}') {
                oneObject.add(output.substring(startIndex + 1, i + 1));
                startIndex = i + 1;
            }
        }

        // for each tokimon on the server
        for (String element : oneObject) {
            String[] stringArray = element.split(",");

            String[] attributes = new String[10];

            // for each attribute of a tokimon
            for (int i = 0; i < stringArray.length; i++) {
                String attribute = stringArray[i];
                for (int j = 0; j < attribute.length(); j++) {
                    if (attribute.charAt(j) == ':') {
                        attributes[i] = attribute.substring(j + 1);
                    }
                }
            }
            // Create an instance of atokimon and save it
            tokimons.add(new Tokimon(Integer.parseInt(attributes[0]),
                    attributes[1].replace('"', ' ').substring(1, attributes[1].length() - 1),
                    Double.parseDouble(attributes[2]),
                    Double.parseDouble(attributes[3]),
                    Integer.parseInt(attributes[4]),
                    Integer.parseInt(attributes[5]),
                    Integer.parseInt(attributes[6]),
                    Integer.parseInt(attributes[7]),
                    Integer.parseInt(attributes[8]),
                    attributes[9].replace('"', ' ').substring(1, attributes[9].length() - 2)));
            }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
