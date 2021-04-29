
package gui;

import DB.DbException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Contraints;
import gui.util.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.SpringLayout;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;


public class DepartmentFormController implements Initializable {
    
    private Department entity;
    
    private DepartmentService service;
    
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    

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
    public void onBtSaveAction(ActionEvent event){
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        if(service ==null){
            throw new IllegalStateException("Service was null");
        }
        try{
            entity = getFormData();
            service.saveOrUpdate(entity);
            NotifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch(ValidationException e){
            setErrorMessages(e.getErros());
        }
        catch(DbException e){
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void onBtnCancelAction(ActionEvent event){
        
        Utils.currentStage(event).close();      
    }
    
    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }
    
    public void setDepartmentService(DepartmentService service){
        this.service = service;  
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

    private Department getFormData() {
        Department obj = new Department();
        
        ValidationException exception = new ValidationException("Validation error!");
         obj.setId(Utils.tryParseToInt(txtId.getText()));
        if(txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Field can´t be empty");
        }

        obj.setName(txtName.getText());
        
        if(exception.getErros().size() > 0){
            throw exception;
        }
        return obj;
    }

    private void NotifyDataChangeListeners() {
        for(DataChangeListener listener: dataChangeListeners){
            listener.onDataChanged();
        }
    }
    
    private void setErrorMessages(Map<String, String> error){
        Set<String> fields = error.keySet();
        
        if(fields.contains("name")){
            labelErrorName.setText(error.get("name"));
        }
    }
}
