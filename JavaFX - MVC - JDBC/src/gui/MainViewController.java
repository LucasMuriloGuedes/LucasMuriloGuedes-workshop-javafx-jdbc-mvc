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
        
    }
    
    public void onMenuitemDepartamentAction(){
        loadView2("/gui/DepartamentList.fxml");
        
    }
    
    public void onMenuItemAboutAction(){
        loadView("/gui/About.fxml");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private synchronized void loadView(String absoluteName){
        
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();
            Scene mainScene = Main.getMaiScene();
            
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVbox.getChildren());
        }
        catch(IOException e ){
            Alerts.showAlert("Error", "ErrorLoading", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
     private synchronized void loadView2(String absoluteName){
        
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();
            Scene mainScene = Main.getMaiScene();
            
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVbox.getChildren());
            
            DepartamentListController controller = loader.getController();
            
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        }
        catch(IOException e ){
            Alerts.showAlert("Error", "ErrorLoading", e.getMessage(), Alert.AlertType.ERROR);
        }
      
        
    }
    
}
