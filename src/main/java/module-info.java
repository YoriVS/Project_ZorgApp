module org.example.zorgapp_maven {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens org.example.zorgapp_maven to javafx.fxml;
    exports org.example.zorgapp_maven;
}