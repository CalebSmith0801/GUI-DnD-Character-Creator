package dnd.CharCreation;

import dnd.CharCreation.ClassMenus.HitPointsController;
import dnd.Character;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Caleb
 */
public class AbilityScoresController implements Initializable {

    private Character character;
    private ArrayList<String> prevWindows;
    private ArrayList<Integer> raceBonuses;
    private ArrayList<Integer> abilities;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private Button customBut;
    @FXML private Button simpleBut;
    @FXML private Button pointBut;
    @FXML private Button rollBut;
    @FXML private Label raceBonusLabel;
    @FXML private Label strModLabel;
    @FXML private Label dexModLabel;
    @FXML private Label conModLabel;
    @FXML private Label intModLabel;
    @FXML private Label wisModLabel;
    @FXML private Label charModLabel;
    @FXML private TextField customStrTF;
    @FXML private TextField customDexTF;
    @FXML private TextField customConTF;
    @FXML private TextField customIntTF;
    @FXML private TextField customWisTF;
    @FXML private TextField customCharTF;
    @FXML private Label simpleStrText;
    @FXML private Label simpleDexText;
    @FXML private Label simpleConText;
    @FXML private Label simpleIntText;
    @FXML private Label simpleWisText;
    @FXML private Label simpleCharText;
    @FXML private Label simple15;
    @FXML private Label simple14;
    @FXML private Label simple13;
    @FXML private Label simple12;
    @FXML private Label simple10;
    @FXML private Label simple8;
    @FXML private Label pointStr;
    @FXML private Label pointDex;
    @FXML private Label pointCon;
    @FXML private Label pointInt;
    @FXML private Label pointWis;
    @FXML private Label pointChar;
    @FXML private Label pointsRemaining;
    @FXML private Label rollLabel1;
    @FXML private Label rollLabel2;
    @FXML private Label rollLabel3;
    @FXML private Label rollLabel4;
    @FXML private Label rollLabel5;
    @FXML private Label rollLabel6;
    @FXML private Label rollStr;
    @FXML private Label rollDex;
    @FXML private Label rollCon;
    @FXML private Label rollInt;
    @FXML private Label rollWis;
    @FXML private Label rollChar;
    @FXML private Line customLine;
    @FXML private Line simpleLine;
    @FXML private Line pointLine;
    @FXML private Line rollLine;
    @FXML private GridPane customPane;
    @FXML private GridPane simplePane;
    @FXML private AnchorPane pointPane;
    @FXML private AnchorPane rollPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MakeIntTextField(customStrTF);
        MakeIntTextField(customDexTF);
        MakeIntTextField(customConTF);
        MakeIntTextField(customIntTF);
        MakeIntTextField(customWisTF);
        MakeIntTextField(customCharTF);
    }
    
    
    private void MakeIntTextField(final TextField tf){
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d+"))
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            
            //calculate ability modifiers when new digit is entered in textfield
            //0 + textfield because sometimes the textfield can be left empty
            else
                setModifiers(Integer.parseInt(0 + customStrTF.getText()), Integer.parseInt(0 + customDexTF.getText()), Integer.parseInt(0 + customConTF.getText()), 
                             Integer.parseInt(0 + customIntTF.getText()), Integer.parseInt(0+ customWisTF.getText()), Integer.parseInt(0 + customCharTF.getText()));
        
            
            //limits text field to 2 characters
            if (tf.getText().length() > 2){
                String s = tf.getText().substring(0, 2);
                tf.setText(s);
            }
        });
        RemoveUndoRedoShortcut(tf);
    }
    
    //limiting the length of tf causes problems with undo/redo, so I removed the shortcuts
    private void RemoveUndoRedoShortcut(final TextField tf){
        tf.setOnKeyPressed((KeyEvent event) -> {
            if ((event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Y) && event.isShortcutDown())
                event.consume();
        });
    }
    
    //Definately feels hacky, probably a better way but it created a string of the form +2 Dex, +1 Wis from race bonuses.
    private void setupRaceBonusLabels(){
        ArrayList<String> abilityNames = new ArrayList<>(Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Char"));
        ArrayList<String> bonusConstruct = new ArrayList<>();
        String bonus = "";
        for (int i = 0; i < 6; i++){
            if (raceBonuses.get(i) < 0)
                bonusConstruct.add(raceBonuses.get(i) + " " + abilityNames.get(i));
            if (raceBonuses.get(i) > 0)
                bonusConstruct.add("+" + raceBonuses.get(i) + " " + abilityNames.get(i));
        }
        bonus = bonusConstruct.get(0);
        for (int i = 1; i < bonusConstruct.size(); i++)
            bonus += ", " + bonusConstruct.get(i);
        raceBonusLabel.setText(bonus);
    }
    
    @FXML
    private void customButton(){
        simplePane.setVisible(false);
        pointPane.setVisible(false);
        rollPane.setVisible(false);
        customPane.setVisible(true);
        customBut.setDisable(true);
        simpleBut.setDisable(false);
        pointBut.setDisable(false);
        rollBut.setDisable(false);
        customBut.setStyle("-fx-font-weight: bold");
        simpleBut.setStyle("-fx-font-weight: normal");
        pointBut.setStyle("-fx-font-weight: normal");
        rollBut.setStyle("-fx-font-weight: normal");
        customLine.setStrokeWidth(3);
        simpleLine.setStrokeWidth(1);
        pointLine.setStrokeWidth(1);
        rollLine.setStrokeWidth(1);
        setModifiers(Integer.parseInt(0 + customStrTF.getText()), Integer.parseInt(0 + customDexTF.getText()), Integer.parseInt(0 + customConTF.getText()), 
                     Integer.parseInt(0 + customIntTF.getText()), Integer.parseInt(0+ customWisTF.getText()), Integer.parseInt(0 + customCharTF.getText()));        
    }
    
    @FXML
    private void simpleButton(){
        pointPane.setVisible(false);
        rollPane.setVisible(false);
        customPane.setVisible(false);
        simplePane.setVisible(true);
        customBut.setDisable(false);
        simpleBut.setDisable(true);
        pointBut.setDisable(false);
        rollBut.setDisable(false);
        customBut.setStyle("-fx-font-weight: normal;");
        simpleBut.setStyle("-fx-font-weight: bold;");
        pointBut.setStyle("-fx-font-weight: normal;");
        rollBut.setStyle("-fx-font-weight: normal;");
        customLine.setStrokeWidth(1);
        simpleLine.setStrokeWidth(3);
        pointLine.setStrokeWidth(1);
        rollLine.setStrokeWidth(1);
        abilities = getAbilitiesFromGrid(simplePane);
        setModifiers(abilities.get(0), abilities.get(1), abilities.get(2), abilities.get(3), abilities.get(4), abilities.get(5));
    }
    
    @FXML
    private void pointButton(){
        simplePane.setVisible(false);
        rollPane.setVisible(false);
        customPane.setVisible(false);
        pointPane.setVisible(true);
        customBut.setDisable(false);
        simpleBut.setDisable(false);
        pointBut.setDisable(true);
        rollBut.setDisable(false);
        customBut.setStyle("-fx-font-weight: normal");
        simpleBut.setStyle("-fx-font-weight: normal");
        pointBut.setStyle("-fx-font-weight: bold");
        rollBut.setStyle("-fx-font-weight: normal");
        customLine.setStrokeWidth(1);
        simpleLine.setStrokeWidth(1);
        pointLine.setStrokeWidth(3);
        rollLine.setStrokeWidth(1);
    }
    
    @FXML
    private void rollButton(){
        simplePane.setVisible(false);
        pointPane.setVisible(false);
        customPane.setVisible(false);
        rollPane.setVisible(true);
        customBut.setDisable(false);
        simpleBut.setDisable(false);
        pointBut.setDisable(false);
        rollBut.setDisable(true);
        customBut.setStyle("-fx-font-weight: normal");
        simpleBut.setStyle("-fx-font-weight: normal");
        pointBut.setStyle("-fx-font-weight: normal");
        rollBut.setStyle("-fx-font-weight: bold");
        customLine.setStrokeWidth(1);
        simpleLine.setStrokeWidth(1);
        pointLine.setStrokeWidth(1);
        rollLine.setStrokeWidth(3);
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && row == GridPane.getRowIndex(node)) {
                return node;
            }
        }
        return null;
    }
    
    //used to get the ability score from the panes that use gridpanes
    private ArrayList<Integer> getAbilitiesFromGrid(GridPane gp){
        ArrayList<Integer> abilityArray = new ArrayList<>(Arrays.asList(0,0,0,0,0,0));
        if (gp.equals(simplePane)){
            ArrayList<Integer> simpleArray = new ArrayList<>(Arrays.asList(15, 14, 13, 12, 10, 8));
            for (int i = 0; i < 6; i++){
                String ability = ((Label) getNodeFromGridPane(gp, 2, i)).getText();
                switch (ability){
                    case "Strength":
                        abilityArray.set(0, simpleArray.get(i));
                        break;
                    case "Dexterity":
                        abilityArray.set(1, simpleArray.get(i));
                        break;
                    case "Constitution":
                        abilityArray.set(2, simpleArray.get(i));
                        break;
                    case "Intelligence":
                        abilityArray.set(3, simpleArray.get(i));
                        break;
                    case "Wisdom":
                        abilityArray.set(4, simpleArray.get(i));
                        break;
                    case "Charisma":
                        abilityArray.set(5, simpleArray.get(i));
                        break;
                    default:
                        System.out.println("Misspelling");
                        break;
                }
            }
        }
        
        else{
            
        }
        
        return abilityArray;
    }
    
    //Function from DnD rulebook on how to create modifiers
    private void setModifiers(int str, int dex, int con, int iq, int wis, int cha){       
        strModLabel.setText(Integer.toString((int) Math.floor((str + raceBonuses.get(0) - 10) / 2.0)));
        dexModLabel.setText(Integer.toString((int) Math.floor((dex + raceBonuses.get(1) - 10) / 2.0)));
        conModLabel.setText(Integer.toString((int) Math.floor((con + raceBonuses.get(2) - 10) / 2.0)));
        intModLabel.setText(Integer.toString((int) Math.floor((iq + raceBonuses.get(3)- 10) / 2.0)));
        wisModLabel.setText(Integer.toString((int) Math.floor((wis + raceBonuses.get(4) - 10) / 2.0)));
        charModLabel.setText(Integer.toString((int) Math.floor((cha + raceBonuses.get(5) - 10) / 2.0)));
    }
    
    //first down and sceond up
    @FXML
    private void moveFirstDown(){
        switchAbilityLabels(simplePane, "firstDown");
    }
    
    //second down and third up
    @FXML
    private void moveSecondDown(){
        switchAbilityLabels(simplePane, "secondDown");
    }
    
    //third down and fourth up
    @FXML
    private void moveThirdDown(){
        switchAbilityLabels(simplePane, "thirdDown");
    }
    
    //fourth down and fifth up
    @FXML
    private void moveFourthDown(){
        switchAbilityLabels(simplePane, "fourthDown");
    }
    
    //fifth down and sixth up
    @FXML
    private void moveFifthDown(){
        switchAbilityLabels(simplePane, "fifthDown");
    }
    
    public void switchAbilityLabels(GridPane gp, String buttonPressed){
        Node node1;
        Node node2;
        switch(buttonPressed){
            case "firstDown":
                node1 = getNodeFromGridPane(gp, 2, 0);
                node2 = getNodeFromGridPane(gp, 2, 1);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 0);
                gp.add(node1, 2, 1);
                break;
            case "secondDown":
                node1 = getNodeFromGridPane(gp, 2, 1);
                node2 = getNodeFromGridPane(gp, 2, 2);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 1);
                gp.add(node1, 2, 2);
                break;
            case "thirdDown":
                node1 = getNodeFromGridPane(gp, 2, 2);
                node2 = getNodeFromGridPane(gp, 2, 3);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 2);
                gp.add(node1, 2, 3);
                break;
            case "fourthDown":
                node1 = getNodeFromGridPane(gp, 2, 3);
                node2 = getNodeFromGridPane(gp, 2, 4);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 3);
                gp.add(node1, 2, 4);
                break;
            case "fifthDown":
                node1 = getNodeFromGridPane(gp, 2, 4);
                node2 = getNodeFromGridPane(gp, 2, 5);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 4);
                gp.add(node1, 2, 5);
                break;
            default:
                System.out.println("Misspelling");
        }
        abilities = getAbilitiesFromGrid(simplePane);
        System.out.println(abilities);
        setModifiers(abilities.get(0), abilities.get(1), abilities.get(2), abilities.get(3), abilities.get(4), abilities.get(5));
    }
    
    @FXML
    private void nextButton(){
        prevWindows.add("ClassMenus/BarbarianSkillsMenu.fxml");
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/ClassMenus/HitPoints.fxml"));
            root = loader.load();
            HitPointsController hitPointsCtrl = loader.getController();
            hitPointsCtrl.setCharacter(character);
            hitPointsCtrl.setPreviousWindows(prevWindows);
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
            ClassMenuController classMenuCtrl = loader.getController();
            character.setHitDice("");
            character.RemoveLastArmorProficiency(); //shields
            character.RemoveLastArmorProficiency(); //medium armor
            character.RemoveLastArmorProficiency(); //light armor
            character.RemoveLastWeaponProficiency(); //martial weapons
            character.RemoveLastWeaponProficiency(); //simple weapons
            character.RemoveSave("Strength");
            character.RemoveSave("Constitution");
            character.RemoveTrait("Rage");
            character.RemoveTrait("Unarmored Defense");
            classMenuCtrl.setCharacter(character);
            classMenuCtrl.setPreviousWindows(prevWindows);
            classMenuCtrl.ReloadScene(character.getclassName());
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


    public void setCharacter(dnd.Character r){
        character = new dnd.Character(r);
        raceBonuses = new ArrayList<>(Arrays.asList(character.getStrRaceBonus(), character.getDexRaceBonus(), character.getConRaceBonus(), 
                                                    character.getIntRaceBonus(), character.getWisRaceBonus(), character.getCharRaceBonus()));
        setupRaceBonusLabels();
        setModifiers(Integer.parseInt(0 + customStrTF.getText()), Integer.parseInt(0 + customDexTF.getText()), 
                     Integer.parseInt(0 + customConTF.getText()), Integer.parseInt(0 + customIntTF.getText()), 
                     Integer.parseInt(0 + customWisTF.getText()), Integer.parseInt(0 + customCharTF.getText()));             
    }
   
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
}