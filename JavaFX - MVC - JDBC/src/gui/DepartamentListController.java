/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import application.Main;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

/**
 * FXML Controller class
 *
 * @author Lucas Murilo
 */
public class DepartamentListController implements Initializable, DataChangeListener {
    
    private DepartmentService service;
    

    @FXML
    private Button btnNew;
    @FXML
    private TableView<Department> tableViewDepartament;
    @FXML
    private TableColumn<Department, Integer> tableColumnId;
    @FXML
    private TableColumn<Department, String> tableColumnName;
    @FXML
    private TableColumn<Department, Department> tableColumnEDIT;
    
    
    @FXML
    public void onBtnNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Department dep = new Department();
        createDialogForm(dep, "/gui/DepartmentForm.fxml", parentStage);
    }
    
    private ObservableList<Department> obslist;
    
    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNode();
    }    

    private void initializeNode() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        Stage stage = (Stage) Main.getMaiScene().getWindow(); // para a tabela acompanhar a altura da janela
        tableViewDepartament.prefHeightProperty().bind(stage.heightProperty()); // para a tabela acompanhar a altura da janelasa

    }
    
    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("Services was null");
        }
        List<Department> list = service.findAll();
        obslist = FXCollections.observableArrayList(list);
        tableViewDepartament.setItems(obslist);
        initEditButtons();
    }
    
    private void createDialogForm(Department dep, String absoluteName, Stage parentStage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane =loader.load();
            
            DepartmentFormController controller = loader.getController();
            controller.setDepartament(dep);
            controller.setDepartmentService(new DepartmentService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();
            
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data");
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
        tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() { 
            private final Button button = new Button("edit"); 
            @Override
            protected void updateItem(Department obj, boolean empty) { 
                super.updateItem(obj, empty); 
                if (obj == null) { 
                    setGraphic(null); 
                    return; 
                } 
                setGraphic(button); 
                button.setOnAction( 
                    event -> createDialogForm( 
                    obj, "/gui/DepartmentForm.fxml",Utils.currentStage(event))); 
                } 
            }); 
        } 
    
}
