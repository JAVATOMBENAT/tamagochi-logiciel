package com.example.tamagochi;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private ListView<String> taskListView;
    private ObservableList<String> tasks;
    private int points = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Initialize task list with sample tasks
        tasks = FXCollections.observableArrayList("Buy groceries", "Go to the gym", "Call mom");

        // Create list view to display tasks
        taskListView = new ListView<>(tasks);
        taskListView.setPrefWidth(300);

        // Create text field for adding new tasks
        TextField taskField = new TextField();
        taskField.setPromptText("Add new task");
        taskField.setPrefWidth(200);

        // Create button for adding new tasks
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String task = taskField.getText().trim();
            if (!task.isEmpty()) {
                tasks.add(task);
                taskField.clear();
            }
        });

        // Create button for editing selected task
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                String newTask = taskField.getText().trim();
                if (!newTask.isEmpty()) {
                    tasks.set(selectedIndex, newTask);
                    taskField.clear();
                }
            }
        });

        // Create button for deleting selected task
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                tasks.remove(selectedIndex);
            }
        });

        Button validateButton = new Button("Valider");
        validateButton.setOnAction(event -> {
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                tasks.remove(selectedIndex);

                points++;

            }
        });

        //
        String message = ("Points: " + points);
        Label messageLabel = new Label();
        messageLabel.setText(message);

        VBox textBox = new VBox();
        textBox.getChildren().addAll(messageLabel);

        // Create input box for task field and associated buttons
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(taskField, addButton, editButton, deleteButton, validateButton);

        // Create container for list view and input box
        VBox container = new VBox(10);
        container.getChildren().addAll(taskListView, inputBox, textBox);
        container.setAlignment(Pos.CENTER);

        // Create scene for to-do list
        Scene scene = new Scene(container, 400, 400);

        // Set title and show primary stage
        primaryStage.setTitle("To-Do List");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // private static Parent loadFXML(String fxml) throws IOException {
    // FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml +
    // ".fxml"));
    // return fxmlLoader.load();
    // }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setRoot(String string) {
    }

}
