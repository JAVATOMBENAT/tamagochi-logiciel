module com.example.tamagochi {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.example.tamagochi to javafx.fxml;
    exports com.example.tamagochi;
}
