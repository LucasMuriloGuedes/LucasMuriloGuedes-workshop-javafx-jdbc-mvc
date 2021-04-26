package application;


import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
    
    private static Scene mainScene;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();
        
            scrollPane.setFitToHeight(true); // para que barra de menu fique do tamanho da tela
            scrollPane.setFitToWidth(true); // para que barra de menu fique do tamanho da tela
            
            mainScene = new Scene(scrollPane);
            
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Java FX - Lucas Murilo");
            primaryStage.show();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    public static Scene getMaiScene(){
        return mainScene;
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
