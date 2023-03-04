package com.example.inventory_manage_application.View_Controller;

import com.example.inventory_manage_application.Model.InHouse;
import com.example.inventory_manage_application.Model.OutSourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.inventory_manage_application.Model.Inventory.getAllParts;

/**
 * The AddPartController class is a JavaFX controller for an Add Part window.
 * It has fields that correspond to UI elements in the view, such as text fields and buttons.
 * The class has a 'radioadd' method that updates a label based on the selection of a radio button,
  a 'onActionCancel' method that closes the current window and opens the main window,
  and a 'onActionSave' method that creates a new part object based on user input and adds it to the inventory.
 * The 'onActionSave' method also checks for errors in the user input and displays an error dialog if necessary.
 * */



public class AddPartController implements Initializable {
    @FXML private RadioButton outsourced;
    @FXML private Label inhouseoroutsourced;
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;
    @FXML private TextField companyORmachineID;
    private Stage stage;
    private Object scene;


    public void radioadd()
    {
        if (outsourced.isSelected())
            this.inhouseoroutsourced.setText("Company Name");
        else
            this.inhouseoroutsourced.setText("Machine ID");
    }
    
    @FXML public void onActionCancel(ActionEvent event) throws IOException {
        if(MainWindowController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(AddPartController.class.getResource("/com/example/inventory_manage_application/MainWindow.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
    //save part
    @FXML public void onActionSave(ActionEvent event) {
        if (Name.getText().isEmpty() || Inventory.getText().isEmpty() || Price.getText().isEmpty() || Minimum.getText().isEmpty() || Maximum.getText().isEmpty()) {
            MainWindowController.infoDialog("Input Error", "Cannot have blank fields", "Check all the fields.");
            return;
        }
        int partInventory=0;
        try {

                int newID = getNewID();
                try{
                    String name = Name.getText();
                }
                catch (Exception e){
                    MainWindowController.infoDialog("Input Error", "Error in inventory field", "Name field mustn't not be empty" );
                    return;
                }
                try{
                     partInventory = Integer.parseInt(Inventory.getText());
                }
                catch (Exception e){
                    MainWindowController.infoDialog("Input Error", "Error in inventory field", "Name field mustn't not be empty" );
                return;
                 }
                int stock = partInventory;
                try{
                    double price = Double.parseDouble(Price.getText());
                }
                catch (Exception e){
                    MainWindowController.infoDialog("Input Error", "Error in inventory field", "Price is double" );
                    return;
                }
                try{
                    int max = Integer.parseInt(Maximum.getText());
                }
                catch (Exception e){
                    MainWindowController.infoDialog("Input Error", "Error in inventory field", "Price is double" );
                    return;
                }
                try{
                    int min = Integer.parseInt(Minimum.getText());
                }
                catch (Exception e){
                    MainWindowController.infoDialog("Input Error", "Error in inventory field", "Price is double" );
                    return;
                }
                String name = Name.getText();
                double price = Double.parseDouble(Price.getText());
                int partMin = Integer.parseInt(Minimum.getText());
                int partMax = Integer.parseInt(Maximum.getText());
                if(MainWindowController.confirmDialog("Save?", "Would you like to save this part?"))
                    if(partMax < partMin) {
                        MainWindowController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
                    }
                    else if(partInventory < partMin || partInventory> partMax) {
                        MainWindowController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
                    }

                int min = partMin;
                int max = partMax;
                if (outsourced.isSelected()) {
                    String companyName = companyORmachineID.getText();
                    OutSourced temp = new OutSourced(newID, stock, min, max, name, price, companyName);
                    com.example.inventory_manage_application.Model.Inventory.addPart(temp);
                } else {
                    int machineID = Integer.parseInt(companyORmachineID.getText());
                    InHouse temp = new InHouse(newID, stock, min, max, name, price, machineID);
                    com.example.inventory_manage_application.Model.Inventory.addPart(temp);
                }
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(AddPartController.class.getResource("/com/example/inventory_manage_application/MainWindow.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene((Parent) scene));
                stage.show();

        }
        catch (Exception e){
            MainWindowController.infoDialog("Input Error", "Error in adding part", "Check fields for correct input" );
        }
    }

    public static int getNewID(){
        int newID = 1;
        for (int i = 0; i < getAllParts().size(); i++) {
                newID++;
            }
        return newID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

