package com.example.inventory_manage_application.View_Controller;

import com.example.inventory_manage_application.Model.Inventory;
import com.example.inventory_manage_application.Model.Parts;
import com.example.inventory_manage_application.Model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class ModifyProductController implements Initializable {

    private Stage stage;
    private Object scene;

    //Un Associated Parts Table
    @FXML private TableView<Parts> PartsTableView;
    @FXML private TableColumn<Parts, Integer> PartsIDColumn;
    @FXML private TableColumn<Parts, String> PartsNameColumn;
    @FXML private TableColumn<Parts, Integer> PartsInventoryColumn;
    @FXML private TableColumn<Parts, Double> PartsCostColumn;

    //Associated Parts Table
    @FXML private TableView<Parts> AssociatedPartsTableView;
    @FXML private TableColumn<Products, Integer> AssociatedPartsIDColumn;
    @FXML private TableColumn<Products, String> AssociatedPartsNameColumn;
    @FXML private TableColumn<Products, Integer> AssociatedPartsInventoryColumn;
    @FXML private TableColumn<Products, Double> AssociatedPartsCostColumn;

    //Modify Fields
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;
    @FXML private TextField ID;

    //Other Buttons/Fields
    @FXML private TextField SearchField;
    private Products selectedProduct;
    private Products modProduct;
    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();
    private int productID;

    public void setProduct(Products selectedProduct) {
        this.selectedProduct = selectedProduct;
        productID = com.example.inventory_manage_application.Model.Inventory.getAllProducts().indexOf(selectedProduct);
        ID.setText(Integer.toString(selectedProduct.getProductID()));
        Name.setText(selectedProduct.getName());
        Inventory.setText(Integer.toString(selectedProduct.getStock()));
        Price.setText(Double.toString(selectedProduct.getProductPrice()));
        Maximum.setText(Integer.toString(selectedProduct.getMax()));
        Minimum.setText(Integer.toString(selectedProduct.getMin()));
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());
    }

    @FXML public void searchPartButton(ActionEvent event) {

        String term = SearchField.getText();
        try {
            ObservableList foundParts = com.example.inventory_manage_application.Model.Inventory.lookupPartid(parseInt(term));
            if(foundParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate a part name with: " + term);
                alert.showAndWait();
            } else {
                PartsTableView.setItems(foundParts);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            ObservableList foundParts = com.example.inventory_manage_application.Model.Inventory.lookupPart(term);
            if(foundParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate a part name with: " + term);
                alert.showAndWait();
            } else {
                PartsTableView.setItems(foundParts);
            }
        }

    }

    @FXML public void modifyProductCancel(ActionEvent event) throws IOException {
        if (MainWindowController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(ModifyProductController.class.getResource("/com/example/inventory_manage_application/MainWindow.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML void modifyProductSave(ActionEvent event) throws IOException {
    int productInventory=0,productMinimum = 0, productMaximum = 0;
        if (Name.getText().isEmpty() || Inventory.getText().isEmpty() || Price.getText().isEmpty() || Maximum.getText().isEmpty()||Minimum.getText().isEmpty() ) {
            MainWindowController.infoDialog("Input Error", "Cannot have blank fields", "Check all the fields.");
            return;
        }
        try{
            Integer.parseInt(ID.getText());
        }
        catch (Exception e){
            MainWindowController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            return;
        }
        try{
            productInventory = Integer.parseInt(Inventory.getText());
        }
        catch (Exception e){
            MainWindowController.infoDialog("Input Error", "Error in inventory field", "Inventory must be valid int" );
            return;
        }
        try{
            Double.parseDouble(Price.getText());
        }
        catch (Exception e){
            MainWindowController.infoDialog("Input Error", "Error in Price field", "Price is not valid Int or Double" );
            return;
        }
        try{
             productMinimum = Integer.parseInt(Minimum.getText());
             productMaximum = Integer.parseInt(Maximum.getText());
        }
        catch (Exception e){
            MainWindowController.infoDialog("Input Error", "Error in Min & Max field", "Minimum and Maximum must be valid int" );
            return;
        }

         if(MainWindowController.confirmDialog("Save?", "Would you like to save this part?"))
            if(productMaximum < productMinimum) {
                MainWindowController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
            }
            else if(productInventory < productMinimum || productInventory > productMaximum) {
                MainWindowController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            }
            else {
                this.modProduct = selectedProduct;
                selectedProduct.setProductID(Integer.parseInt(ID.getText()));
                selectedProduct.setName(Name.getText());
                selectedProduct.setStock(Integer.parseInt(Inventory.getText()));
                selectedProduct.setProductPrice(Double.parseDouble(Price.getText()));
                selectedProduct.setMax(Integer.parseInt(Maximum.getText()));
                selectedProduct.setMin(Integer.parseInt(Minimum.getText()));
                selectedProduct.getAllAssociatedParts().clear();
                selectedProduct.addAssociatedPart(associatedParts);
                com.example.inventory_manage_application.Model.Inventory.updateProduct(productID, selectedProduct);

                //Back to Main Screen
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(ModifyProductController.class.getResource("/com/example/inventory_manage_application/MainWindow.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
    }

    @FXML void addPartToProduct(ActionEvent event) {
        Parts selectedPart = PartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            associatedParts.add(selectedPart);
            updateAssociatedPartTable();
        }

        else {
            MainWindowController.infoDialog("Select a part","Select a part", "Select a part to add to the Product");
        }
    }

    @FXML
    void deletePartFromProduct(ActionEvent event) {
        Parts selectedPart = AssociatedPartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            MainWindowController.confirmDialog("Deleting Parts","Are you sure you want to delete " + selectedPart.getName() + " from the Product?");
            associatedParts.remove(selectedPart);
            updateAssociatedPartTable();
        }

        else {
            MainWindowController.infoDialog("No Selection","No Selection", "Please choose something to remove");
        }
    }

    public void updatePartTable() {
        PartsTableView.setItems(com.example.inventory_manage_application.Model.Inventory.getAllParts());
    }

    private void updateAssociatedPartTable() {
        AssociatedPartsTableView.setItems(associatedParts);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Columns and Table for un-associated parts.
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        PartsTableView.setItems(com.example.inventory_manage_application.Model.Inventory.getAllParts());

        //Columns and Table for associated parts
        AssociatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        AssociatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        AssociatedPartsTableView.setItems(associatedParts);

        updatePartTable();
        updateAssociatedPartTable();
    }
}
