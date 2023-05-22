module com.example.tamagochi {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.example.tamagochi to javafx.fxml;

    exports com.example.tamagochi;
}
