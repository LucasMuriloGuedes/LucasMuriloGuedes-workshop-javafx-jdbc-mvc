/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import DB.DbException;
import application.Main;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import static gui.util.Alerts.showConfirmation;
import gui.util.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

/**
 * FXML Controller class
 *
 * @author Lucas Murilo
 */
public class SellerListController implements Initializable, DataChangeListener {
    
    private SellerService service;
    

    @FXML
    private Button btnNew;
    @FXML
    private TableView<Seller> tableViewSeller;
    @FXML
    private TableColumn<Seller, Integer> tableColumnId;
    @FXML
    private TableColumn<Seller, String> tableColumnName;
    
    @FXML
    private TableColumn<Seller, String> tableColumnEmail;
     
    @FXML
    private TableColumn<Seller, Date> tableColumnBirthDate;
      
    @FXML
    private TableColumn<Seller, Double> tableColumnBaseSalary;
   
    private TableColumn<Seller, Seller> tableColumnEDIT;
    
    @FXML TableColumn<Seller, Seller> tableColumnREMOVE;
    
    
    
    @FXML
    public void onBtnNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Seller dep = new Seller();
        createDialogForm(dep, "/gui/SellerForm.fxml", parentStage);
    }
    
    private ObservableList<Seller> obslist;
    
    public void setSellerService(SellerService service){
        this.service = service;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNode();
    }    

    private void initializeNode() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
        tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
        Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
        
        
        Stage stage = (Stage) Main.getMaiScene().getWindow(); // para a tabela acompanhar a altura da janela
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty()); // para a tabela acompanhar a altura da janelasa

    }
    
    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("Services was null");
        }
        List<Seller> list = service.findAll();
        obslist = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(obslist);
        initEditButtons();
        initRemoveButtons();
    }
    
    private void createDialogForm(Seller dep, String absoluteName, Stage parentStage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane =loader.load();
            
            SellerFormController controller = loader.getController();
            controller.setSeller(dep);
            controller.setSellerService(new SellerService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();
            
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Seller data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
            
        }
        catch(IOException e){
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }
    
    private void initEditButtons() { 
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue())); 
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() { 
            private final Button button = new Button("edit"); 
            @Override
            protected void updateItem(Seller obj, boolean empty) { 
                super.updateItem(obj, empty); 
                if (obj == null) { 
                    setGraphic(null); 
                    return; 
                } 
                setGraphic(button); 
                button.setOnAction( 
                    event -> createDialogForm( 
                    obj, "/gui/SellerForm.fxml",Utils.currentStage(event))); 
                } 
            }); 
        } 
    
    private void initRemoveButtons() { 
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue())); 
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() { 
            private final Button button = new Button("remove"); 
            @Override
            protected void updateItem(Seller obj, boolean empty) { 
                super.updateItem(obj, empty); 
                if (obj == null) { 
                setGraphic(null); 
                return; 
            } 
                setGraphic(button); 
                button.setOnAction(event -> removeEntity(obj)); 
            } 
         }); 
    } 
     private void removeEntity(Seller obj) {
                Optional<ButtonType> result  = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
                if(result.get() == ButtonType.OK){
                    if(service == null){
                        throw new IllegalStateException("Service was null");
                    } 
                    try{
                        service.remove(obj);
                        updateTableView();
                    }
                    catch(DbException e){
                        Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);               
                    }  
                }
            }
}
