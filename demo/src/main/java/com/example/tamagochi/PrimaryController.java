package com.example.tamagochi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private Button menuButton;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void returnToMenu() {
        if (primaryStage != null) {
            primaryStage.setScene(App.getMenuScene());
            primaryStage.show();
        }
    }

    // Rest of your code

}
