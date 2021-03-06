/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import application.Main;
import gui.util.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

/**
 * FXML Controller class
 *
 * @author Lucas Murilo
 */
public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;
    
    @FXML
    
    public void onMenuItemSellerAction(){
        loadView("/gui/SellerList.fxml",(SellerListController controller) -> {
            controller.setSellerService(new SellerService());
            controller.updateTableView();
        });
        
    }
    
    public void onMenuitemDepartamentAction(){
        loadView("/gui/DepartamentList.fxml",(DepartamentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });
        
    }
    
    public void onMenuItemAboutAction(){
        loadView("/gui/About.fxml", x -> {});  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
    
    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializngAction){
    
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();
            Scene mainScene = Main.getMaiScene();
            
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVbox.getChildren());
            
            T controller = loader.getController();
            initializngAction.accept(controller);
        }
        catch(IOException e ){
            Alerts.showAlert("Error", "ErrorLoading", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
