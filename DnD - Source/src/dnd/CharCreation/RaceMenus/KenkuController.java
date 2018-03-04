//============================================================================//
//PREVIOUS WINDOWS: raceSelectionMenu                                         //
//                  (loads when race = Kenku)                                 //
//                                                                            //
//NEXT WINDOW: ClassMenu                                                      //
//                                                                            //
//Changes to Character in this Window:                                        //
//---Adds two skill proficiencies                                             //
//============================================================================//
package dnd.CharCreation.RaceMenus;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;
import dnd.CharCreation.ClassMenuController;
import dnd.CharCreation.RaceSelectionMenuController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class KenkuController implements Initializable {

    private Character character;
    private ArrayList<String> prevWindows;
    private ArrayList<CheckBox> selectedCB = new ArrayList<>();
    private ArrayList<CheckBox> unselectedCB;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private CheckBox acrobaticsCB;
    @FXML private CheckBox deceptionCB;
    @FXML private CheckBox stealthCB;
    @FXML private CheckBox slOfHandCB;
    private int choices = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        unselectedCB = new ArrayList<>(Arrays.asList(acrobaticsCB, deceptionCB, stealthCB, slOfHandCB));
        unselectedCB.forEach((checkbox) -> {
            checkbox.selectedProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.equals(true)){
                selectedCB.add(checkbox);
                unselectedCB.remove(checkbox);
                choices++;
            }
            else{
                selectedCB.remove(checkbox);
                unselectedCB.add(checkbox);
                choices--;
            }
            if (choices == 2){
                unselectedCB.forEach((cb) -> {
                    cb.setDisable(true);
                });
                nextBut.setDisable(false);
            }
            else{
                unselectedCB.forEach((cb) -> {
                    cb.setDisable(false);
                });
                nextBut.setDisable(true);
            }
            });
        });
    }
    
    @FXML
    private void nextButton(){
        prevWindows.add("RaceMenus/Kenku.fxml");
        selectedCB.forEach((cb) -> {
            character.addSkill(cb.getText());
        });
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/ClassMenu.fxml"));
            root = loader.load();
            ClassMenuController classMenuCtrl = loader.getController();
            classMenuCtrl.setCharacter(character);
            classMenuCtrl.setPreviousWindows(prevWindows);
            switchScene(root);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    private void backButton(){
        Parent root;
        try{
            String prevWindow = prevWindows.get(prevWindows.size()-1);
            //Remove last element because when returning to previous window, the second to last
            //element in the array is now the current previous window
            prevWindows.remove(prevWindow);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/"+prevWindow));
            root = loader.load();
            RaceSelectionMenuController raceSelCtrl = loader.getController();
            raceSelCtrl.ReloadScene(character.getraceName(), character.getsubraceName());
            switchScene(root);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void switchScene(Parent newRoot){
        Stage stage;
        stage = (Stage) nextBut.getScene().getWindow();
        double oldX = stage.getX(); 
        double oldY = stage.getY();
        double oldHeight = stage.getHeight();
        double oldWidth = stage.getWidth();
        stage.close();
        Scene newScene = new Scene(newRoot);
        stage.setScene(newScene);
        
        //Adjusts new stage position so that it is centered relative to
        //the previous stage
        stage.setOnShown((WindowEvent w) -> {
            if (stage.getWidth() > oldWidth)
                stage.setX(oldX - ((stage.getWidth() / 2.0) - (oldWidth / 2.0)));
            else
                stage.setX(oldX + (oldWidth / 2.0) - (stage.getWidth() / 2.0));
            if (stage.getHeight() > oldHeight)
                stage.setY(oldY - ((stage.getHeight() / 2.0) - (oldHeight / 2.0)));
            else
                stage.setY(oldY + (oldHeight / 2.0) - (stage.getHeight() / 2.0));
        });
        stage.show();
    }

    
    public void setCharacter(Character c){
        character = new Character(c);
    }
    
    //Needed so back button knows which window to transition to since the ExtraLanguageMenu
    //is used in multiple combinations
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    //when a back button is pressed that returns to this window, load previous choice(s)
    public void setSceneOnReload(ArrayList<String> skillsList){
        for (String skill : skillsList){
            if (skill.equals(acrobaticsCB.getText()))
                acrobaticsCB.setSelected(true);
            if (skill.equals(deceptionCB.getText()))
                deceptionCB.setSelected(true);
            if (skill.equals(stealthCB.getText()))
                stealthCB.setSelected(true);
            if (skill.equals(slOfHandCB.getText()))
                slOfHandCB.setSelected(true);
        }
    }
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
}
