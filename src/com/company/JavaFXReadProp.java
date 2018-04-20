package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

import static javafx.scene.control.TextAreaBuilder.create;


public class JavaFXReadProp extends Application {
    // create tableview
    public TableView<Record> tableView = new TableView<>();
    // create list
    public final ObservableList<Record> dataList
           = FXCollections.observableArrayList();

    TextField idInput, f2Input, f3Input;

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        primaryStage.setTitle("test");
        // columns in table
        TableColumn<Record, String> columnF1 = new TableColumn("id");
        columnF1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Record, String> columnF2 = new TableColumn("f2");
        columnF2.setCellValueFactory(new PropertyValueFactory<>("f2"));
        TableColumn<Record, String> columnF3 = new TableColumn("f3");
        columnF3.setCellValueFactory(new PropertyValueFactory<>("f3"));



        // Id textfield
        idInput = new TextField();
        idInput.setPromptText("Id");
        idInput.setMinWidth(100);
        // F2 textfield
        f2Input = new TextField();
        f2Input.setPromptText("F2");
        f2Input.setMinWidth(100);
        // F3 textfield
        f3Input = new TextField();
        f3Input.setPromptText("F3");
        f3Input.setMinWidth(100);


        final TextArea textArea = create()
                .prefWidth(400)
                .wrapText(true)
                .build();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setContent(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(900);
        scrollPane.setPrefHeight(960);

        // Buttons
        Button addButton = new Button("Add");
            addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> deleteButtonClicked());

        Button buttonLoad = new Button("Load");
        buttonLoad.setOnAction(arg0 -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "TXT files (*.txt)", "*.txt" ,"*.csv","*.properties");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file != null){
                textArea.setText(readFile(file));
            }
        });

        // Hbox, add textfield inputs, buttons
        HBox hBox = new HBox();
            hBox.setPadding(new Insets(10,10,10,10));
            hBox.setSpacing(10);
            hBox.getChildren().addAll(idInput, f2Input, f3Input, addButton, deleteButton, buttonLoad);

        tableView = new TableView<>();
        // Make columnF3 editable
        tableView.setEditable(true);
        columnF3.setEditable(true);
        columnF3.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.setItems(dataList);
        // tableView.setItems(getAllRecords());

         tableView.getColumns().addAll(
                columnF1, columnF2, columnF3);

        VBox vBox = new VBox();
            vBox.getChildren().addAll(tableView, hBox);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
        printAllProp();



    }


    private void deleteButtonClicked() {
        ObservableList<Record> rowSelected;ObservableList<Record> allRows;
        allRows = tableView.getItems();
        rowSelected = tableView.getSelectionModel().getSelectedItems();
        allRows.removeAll(rowSelected);
    }

    private void addButtonClicked(){
        Record record = new Record();
        record.setId(idInput.getText());
        record.setF2(f2Input.getText());
        record.setF3(f3Input.getText());
        tableView.getItems().add(record);
        idInput.clear();
        f2Input.clear();
        f3Input.clear();

    }

    private void printAllProp() {
        // OrderedProperties returns in original order as properties file
        Properties prop = new OrderedProperties();
        InputStream input = null;
        try {
            String filename = "Res_es.properties";
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("File not found " + filename);
                return;
            }
            prop.load(input);

            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                // String f3comment = prop.getProperty(key);
                Record record = new Record(key, value); // remove f3comment
                dataList.add(record);
                System.out.println("Key : " + key + ", Value : " + value);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                dataList.removeAll();
                Record record = new Record();
                stringBuffer.append(text);
                dataList.add(record);
            }

        } catch (IOException ex) {
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
            }
        }

        return stringBuffer.toString();
    }
}






