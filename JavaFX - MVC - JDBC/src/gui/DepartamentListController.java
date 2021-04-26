/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import application.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

/**
 * FXML Controller class
 *
 * @author Lucas Murilo
 */
public class DepartamentListController implements Initializable {

    @FXML
    private Button btnNew;
    @FXML
    private TableView<Department> tableViewDepartament;
    @FXML
    private TableColumn<Department, Integer> tableColumnId;
    @FXML
    private TableColumn<Department, String> tableColumnName;
    
    @FXML
    public void onBtnNewAction(){
        
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
    
}
