package com.example.inventory_manage_application;

import com.example.inventory_manage_application.Model.InHouse;
import com.example.inventory_manage_application.Model.Inventory;
import com.example.inventory_manage_application.Model.OutSourced;
import com.example.inventory_manage_application.Model.Products;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Add Parts InHouse
        Inventory.addPart(new InHouse(1, 20, 1, 50, "Paddle", 59.99, 19));
        Inventory.addPart(new InHouse(2, 25, 1, 50, "Gear Shift", 99.99, 12));
        Inventory.addPart(new InHouse(3, 15, 1, 50, "Handle", 25.99, 82));
        Inventory.addPart(new InHouse(4, 10, 1, 50, "Seat", 15.99, 99));

        //Add Parts OutSourced
        Inventory.addPart(new OutSourced(5, 10, 1, 50, "Cover", 39.99, "CD125"));
        Inventory.addPart(new OutSourced(6, 5, 1, 50, "Modified Skin", 9.99, "Yamaha R5, Harley"));
        Inventory.addPart(new OutSourced(7, 20, 1, 50, "Break", 0.00, "CD125"));
        Inventory.addPart(new OutSourced(8, 25, 1, 50, "Disk Break", 44.99, "Harley"));

        //Add Products
        Inventory.addProduct(new Products(1, 10, 1, 50, "Kawasaki Ninja", 249.99));
        Inventory.addProduct(new Products(2, 8, 1, 50, "Yamaha R5", 159.99));
        Inventory.addProduct(new Products(3, 8, 1, 50, "Harley", 159.99));

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 580);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
