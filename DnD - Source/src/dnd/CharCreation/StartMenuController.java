package dnd.CharCreation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import dnd.Character;

/**
 *
 * @author calebs
 */
public class StartMenuController implements Initializable {
    
    @FXML private Button newBut;
    
    @FXML
    private void loadChar(ActionEvent event) {
       
    }
    
    @FXML
    private void newChar(ActionEvent event) {
        Stage stage;
        Parent newRoot;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        try{
            stage = (Stage) newBut.getScene().getWindow();
            //Made stage close and open again to make transition between scenes nicer and not have a weird black flicker
            stage.close();
            //stage.setX((screenBounds.getWidth() - 800) / 2); 
            //stage.setY((screenBounds.getHeight() - 650) / 2);
            
            
            
            newRoot = FXMLLoader.load(getClass().getResource("raceSelectionMenu.fxml"));
            Scene newScene = new Scene(newRoot);

            stage.setScene(newScene);
           
            stage.show();

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        
    }
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
