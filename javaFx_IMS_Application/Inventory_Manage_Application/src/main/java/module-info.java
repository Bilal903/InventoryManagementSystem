module com.example.inventory_manage_application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.inventory_manage_application to javafx.fxml;
    opens com.example.inventory_manage_application.Model to javafx.fxml;

    exports com.example.inventory_manage_application;
    exports com.example.inventory_manage_application.Model;
    exports com.example.inventory_manage_application.View_Controller;
    opens com.example.inventory_manage_application.View_Controller to javafx.fxml;

}