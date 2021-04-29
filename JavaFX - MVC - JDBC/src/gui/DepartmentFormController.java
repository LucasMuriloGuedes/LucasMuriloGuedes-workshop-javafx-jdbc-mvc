/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.util.Contraints;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.SpringLayout;
import model.entities.Department;

/**
 * FXML Controller class
 *
 * @author Lucas Murilo
 */
public class DepartmentFormController implements Initializable {
    
    private Department entity;
    

    @FXML
    private TextField txtId;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private Label labelErrorName;
    
    @FXML 
    private Button btnSave;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    public void onBtSaveAction(){
        
    }
    
    @FXML
    public void onBtnCancelAction(){
        
    }
    
    public void setDepartament(Department entity){
        this.entity = entity;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void initializeNode(){
        Contraints.setTextFieldInteger(txtId);
        Contraints.setTextFieldMaxLength(txtName, 30);
       
    }
    
    public void updateFormData(){
        if(entity == null){
            throw new IllegalStateException("Entity was null");
   
        }
        
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(String.valueOf(entity.getName()));
    }
}
