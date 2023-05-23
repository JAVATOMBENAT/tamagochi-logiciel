package com.example.tamagochi;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private ObservableList<Consomable> boutiqueItems;
    private ObservableList<String> tasks;
    private int points;
    private static Scene menuScene;
    private ObservableList<Consomable> inventaireItems;

    @Override
    public void start(Stage primaryStage) throws Exception {
        inventaireItems = FXCollections.observableArrayList();
        boutiqueItems = FXCollections.observableArrayList();
        ListView<Consomable> boutiqueListView = new ListView<>(boutiqueItems);

        boutiqueItems.add(new Consomable("Potion de soin", 10.0));
        boutiqueItems.add(new Consomable("Gateau de force", 15.0));
        boutiqueItems.add(new Consomable("Elixir de bonheur", 15.0));
        boutiqueItems.add(new Consomable("Boisson de joie", 5.0));

        tasks = FXCollections.observableArrayList("Manger", "Acheter un item", "Consommer un item");

        ListView<String> taskListView = new ListView<>(tasks);
        taskListView.setPrefWidth(300);

        TextField taskField = new TextField();
        taskField.setPromptText("Ajouter une nouvelle tâche");
        taskField.setPrefWidth(200);

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(event -> {
            String task = taskField.getText().trim();
            if (!task.isEmpty()) {
                tasks.add(task);
                taskField.clear();
            }
        });

        Button editButton = new Button("Éditer");
        editButton.setOnAction(event -> {
            int index = taskListView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                String newTask = taskField.getText().trim();
                if (!newTask.isEmpty()) {
                    tasks.set(index, newTask);
                    taskField.clear();
                }
            }
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(event -> {
            int index = taskListView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                tasks.remove(index);
            }
        });

        Label pointDeRecompense = new Label("argent :  0 €");
        Label pointDeRecompenseshop = new Label("argent :  0 €");
        Label pointDeRecompenseinventory = new Label("argent :  0 €");
        pointDeRecompense.setText("Argent : " + pointDeRecompense);
        pointDeRecompenseshop.setText("Argent : " + points);
        pointDeRecompenseinventory.setText("Argent : " + points);

        Button validateButton = new Button("Valider");
        validateButton.setOnAction(event -> {
            int index = taskListView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                tasks.remove(index);

                points += 10;
                pointDeRecompense.setText("Argent : " + points);
                System.out.print(points);
            }
        });

        Button shopButton = new Button("Boutique");
        Button inventaireButton = new Button("Inventaire");
        Button toDoListButton = new Button("Liste de tâches");

        VBox menuChoix = new VBox(10);
        menuChoix.getChildren().addAll(shopButton, inventaireButton, toDoListButton);
        menuChoix.setAlignment(Pos.CENTER);

        menuScene = new Scene(menuChoix, 400, 400);

        Button retourMenuButton = new Button("Retour au menu");
        retourMenuButton.setOnAction(event -> {
            primaryStage.setScene(menuScene);
        });
        Button retourMenuButtonInventory = new Button("Retour au menu");
        retourMenuButtonInventory.setOnAction(event -> {
            primaryStage.setScene(menuScene);
        });
        Button retourMenuButtonToDo = new Button("Retour au menu");
        retourMenuButtonToDo.setOnAction(event -> {
            primaryStage.setScene(menuScene);
        });

        VBox containerShop = new VBox(10);
        containerShop.getChildren().addAll(boutiqueListView, retourMenuButton, pointDeRecompenseshop);

        boutiqueListView.setCellFactory(param -> new ListCell<>() {
            private final Button acheterButton = new Button("Acheter");

            {
                acheterButton.setOnAction(event -> {
                    Consomable consomable = getItem();
                    if (points >= consomable.getPrix()) {
                        inventaireItems.add(consomable); // Ajoute le consommable à l'inventaire
                        points -= consomable.getPrix(); // Déduit le prix du consommable des points
                        pointDeRecompense.setText("Argent : " + points);
                    } else {
                        // Display an error message if the user doesn't have enough money
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Fonds insuffisants");
                        alert.setContentText("Vous n'avez pas assez d'argent pour acheter cet objet.");
                        alert.showAndWait();
                    }
                });
            }

            @Override
            protected void updateItem(Consomable item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    HBox ligneShop = new HBox(10);
                    ligneShop.getChildren().addAll(new Label(item.getNom()),
                            new Label(String.valueOf(item.getPrix())), acheterButton);
                    setGraphic(ligneShop);
                }
            }
        });

        ListView<Consomable> inventaireListView = new ListView<>(inventaireItems);

        VBox containerInventaire = new VBox(10);
        containerInventaire.getChildren().addAll(inventaireListView, retourMenuButtonInventory,
                pointDeRecompenseinventory);

        inventaireListView.setCellFactory(param -> new ListCell<>() {
            private final Button consommerButton = new Button("Consommer");

            {
                consommerButton.setOnAction(event -> {
                    Consomable consomable = getItem();
                    inventaireItems.remove(consomable); // Supprime l'élément de l'inventaire
                });
            }

            @Override
            protected void updateItem(Consomable item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    HBox ligneInventaire = new HBox(10);
                    ligneInventaire.getChildren().addAll(new Label(item.getNom()), consommerButton);
                    setGraphic(ligneInventaire);
                }
            }
        });

        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(taskField, addButton, editButton, deleteButton, validateButton);

        VBox containerListTasks = new VBox(10);
        containerListTasks.getChildren().addAll(taskListView, inputBox, pointDeRecompense);
        containerListTasks.setAlignment(Pos.CENTER);

        Parent shopRoot = loadFXML("shop");
        Scene shop = new Scene(shopRoot, 400, 400);
        ((VBox) shopRoot).getChildren().addAll(containerShop);

        Parent inventoryRoot = loadFXML("inventory");
        Scene inventory = new Scene(inventoryRoot, 400, 400);
        ((VBox) inventoryRoot).getChildren().addAll(containerInventaire, retourMenuButtonInventory);

        Parent todoRoot = loadFXML("toDoList");
        Scene toDoList = new Scene(todoRoot, 400, 400);
        ((VBox) todoRoot).getChildren().addAll(containerListTasks, retourMenuButtonToDo);

        primaryStage.setTitle("TamaGochi");
        primaryStage.setScene(menuScene);
        primaryStage.show();

        shopButton.setOnAction(event -> {
            primaryStage.setScene(shop);
        });

        inventaireButton.setOnAction(event -> {
            primaryStage.setScene(inventory);
        });

        toDoListButton.setOnAction(event -> {
            primaryStage.setScene(toDoList);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Scene getMenuScene() {
        return menuScene;
    }

    public static void setRoot(String string) {
    }
}
