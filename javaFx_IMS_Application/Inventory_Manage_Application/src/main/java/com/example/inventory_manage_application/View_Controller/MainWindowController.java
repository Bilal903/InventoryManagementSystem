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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * The MainWindowController class has several methods that are called when buttons in the view are clicked.
 * The addpartbuttonpushed method opens the Add Part window.
 * The modifypartbuttonpushed and modifyproductbuttonpushed methods open the Modify Part and Modify Product windows,
   respectively,and pass the selected part or product to the respective controller.
 * The searchPartButton and searchProductButton methods search for parts and products,
   respectively, based on user-entered search terms and display the results in the respective table views.
 * The deletePartButton and deleteProductButton methods delete the selected part or product from the inventory.
 * The addproductbuttonpushed method opens the Add Product window. The exitbuttonpushed method closes the application.
 * */

public class MainWindowController implements Initializable {

    // Parts Table
    @FXML private TableView<Parts> partsTableView;
    @FXML private TableColumn<Parts, Integer> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, Integer> partInvLevelColumn;
    @FXML private TableColumn<Parts, Double> partCostColumn;
    @FXML private TextField partSearchArea;

    //Products Table
    @FXML private TableView<Products> productsTableView;
    @FXML private TableColumn<Products, Integer> productIDColumn;
    @FXML private TableColumn<Products, String> productNameColumn;
    @FXML private TableColumn<Products, Integer> productInvLevelColumn;
    @FXML private TableColumn<Products, Double> productCostColumn;
    @FXML private TextField productSearchArea;

    private static ObservableList<Products> addAssociatedPart = FXCollections.observableArrayList();

    public static ObservableList<Products> getAllPart() {
        return addAssociatedPart;
    }


    private Parent scene;

    public void addpartbuttonpushed(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainWindowController.class.getResource("/com/example/inventory_manage_application/AddPart.fxml"));
        stage.setTitle("Add Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void modifypartbuttonpushed(ActionEvent event){
        try {
            Parts selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                infoDialog("Warning!", "No Part Selected","Please choose part from the above list");
                return;
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("/com/example/inventory_manage_application/ModifyPart.fxml"));
            scene = loader.load();
            ModifyPartController controller = loader.getController();
            controller.setParts(selectedPart);
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void modifyproductbuttonpushed(ActionEvent event){
        try {
            Products selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                infoDialog("Warning!", "No Part Selected","Please choose part from the above list");
                return;
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("/com/example/inventory_manage_application/ModifyProduct.fxml"));
            scene = loader.load();
            ModifyProductController controller = loader.getController();
            controller.setProduct(selectedProduct);
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void searchPartButton(ActionEvent event) {
        String term = partSearchArea.getText();
        try {

            ObservableList foundParts = Inventory.lookupPartid(parseInt(term));
            if(foundParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate a part name with: " + term);
                alert.showAndWait();
            } else {
                partsTableView.setItems(foundParts);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            ObservableList foundParts = Inventory.lookupPart(term);
            if(foundParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate a part name with: " + term);
                alert.showAndWait();
            } else {
                partsTableView.setItems(foundParts);
            }
        }

    }

    public void searchProductButton(ActionEvent event) {
        String term = productSearchArea.getText();
        try {

            ObservableList foundProduct = Inventory.lookupProductid(parseInt(term));
            if(foundProduct.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate a product name with: " + term);
                alert.showAndWait();
            } else {
                productsTableView.setItems(foundProduct);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            ObservableList foundProduct = Inventory.lookupProduct(term);
            if(foundProduct.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate a product name with: " + term);
                alert.showAndWait();
            } else {
                productsTableView.setItems(foundProduct);
            }
        }
    }

    public void deletePartButton(ActionEvent event) {
        if (partsTableView.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Part Selected","Please choose part from the above list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this part?")){
            int selectedPart = partsTableView.getSelectionModel().getSelectedIndex();
            partsTableView.getItems().remove(selectedPart);
        }
    }

    public void deleteProductButton(ActionEvent event) {
        Products selectedProduct = productsTableView.getSelectionModel().getSelectedItem();


        if (productsTableView.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Product Selected","Please choose product from the above list");
            return;
        }
        if(selectedProduct.getAssociatedParts().isEmpty()) {
            if (confirmDialog("Warning!", "Would you like to delete this product?")){
                int selectedPart = productsTableView.getSelectionModel().getSelectedIndex();
            productsTableView.getItems().remove(selectedPart);
            infoDialog("Warning!", "This product is associated", "Plese remove associated part first");
        }
        }
        else {
            infoDialog("Warning!", "This product is associated", "Plese remove associated part first");
        }
    }

    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }

    static void infoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML public void addProductButtonPushed(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainWindowController.class.getResource("/com/example/inventory_manage_application/AddProduct.fxml"));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML public void exitButton(ActionEvent event) throws IOException{
        confirmDialog("Exit", "Are you sure you would like to exit the program?");
        {
                System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Parts, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<Parts, Integer>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Parts, Double>("partCost"));

        productsTableView.setItems((Inventory.getAllProducts()));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<Products, Double>("productPrice"));


    }

}
