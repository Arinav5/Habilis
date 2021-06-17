/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

import com.jfoenix.controls.JFXCheckBox;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author varda
 */
public class TaskController implements Initializable {

    @FXML
    private ImageView trashCan;
    public static ImageView trashCanDBL = new ImageView();
    @FXML
    private ImageView clockTime;
    public static ImageView clockTimeDBL = new ImageView();
    @FXML
    private AnchorPane taskPanel;
    public static Node whichClicked;
    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private ImageView addBack;
    public static ImageView addBackDBL = new ImageView();
    @FXML
    private Label uniqueID;
    public static ArrayList<Integer> ids = new ArrayList<Integer>();
    public int id;
    public static Label idDBL = new Label();
    public static JFXCheckBox checkBoxDBL = new JFXCheckBox();
    @FXML
    private Label taskText = new Label("");
    public static Label labelt = new Label("");


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkBoxDBL = checkBox;
        idDBL = uniqueID; 
        labelt = taskText;
        trashCanDBL = trashCan;
        clockTimeDBL = clockTime;
        addBackDBL = addBack;
        id = Integer.valueOf(randomId(""));
        while(idExists(id)){
            id = Integer.valueOf(randomId(""));
        }
        uniqueID.setText(String.valueOf(id)); 
        ids.add(id);
        trashCan.setVisible(false);
        clockTime.setVisible(false);
    }
    @FXML
    private void showImgs(MouseEvent event) {
        trashCan.setVisible(true);
        clockTime.setVisible(true);
    }

    @FXML
    private void hideImgs(MouseEvent event) {
        trashCan.setVisible(false);
        clockTime.setVisible(false);
    }

    @FXML
    private void deleteTask(MouseEvent event) throws IOException, SQLException {
        whichClicked = taskPanel;
        int counter = 0;
        //Task.allTasks.remove(Task.allTasks.indexOf(taskPanel));
        FXML1Controller.countTasks--;
        FXMain.deleteTaskData(uniqueID.getText());
        FXML1Controller.testVBox.getChildren().remove(FXML1Controller.testVBox.getChildren().indexOf(taskPanel));
        System.out.println(FXML1Controller.testVBox.getChildren());
        for(Node n: FXML1Controller.testVBox.getChildren()){
            counter++;
        }
        if(counter == 1){
            FXML1Controller.addTaskDBL.setVisible(true);
        }
    }
    
    @FXML
    private void moveToRemoved(MouseEvent event) throws SQLException {
        FXML1Controller.testRemVBox.setSpacing(5);
        FXMain.makeNotTask(uniqueID.getText());
        FXML1Controller.testVBox.getChildren().remove(FXML1Controller.testVBox.getChildren().indexOf(taskPanel));
        FXML1Controller.testRemVBox.getChildren().add(0,taskPanel);
        trashCan.setImage(null);
        clockTime.setImage(null);
        checkBox.setVisible(false);
        addBack.setVisible(true);
        FXML1Controller.checkMarkDBL.setVisible(false);
    }

    @FXML
    private void addBackTask(MouseEvent event) throws FileNotFoundException, SQLException {
        int counter = 0;
        FXML1Controller.testVBox.setSpacing(5);
        FXMain.makeTaskTrue(uniqueID.getText());
        FXML1Controller.testRemVBox.getChildren().remove(FXML1Controller.testRemVBox.getChildren().indexOf(taskPanel));
        FXML1Controller.testVBox.getChildren().add(0,taskPanel);
        checkBox.setVisible(true);
        addBack.setVisible(false);
        InputStream stream1 = new FileInputStream("C:\\Arina's Folder\\Programming\\Learning\\Coding\\Exercise2\\src\\JavaApplication8\\Images\\clock2.png");
        Image image1 = new Image(stream1);
        InputStream stream2 = new FileInputStream("C:\\Arina's Folder\\Programming\\Learning\\Coding\\Exercise2\\src\\JavaApplication8\\Images\\twotone_delete_outline_white_24dp.png");
        Image image2 = new Image(stream2);
        clockTime.setImage(image1);
        trashCan.setImage(image2);
        for(Node n: FXML1Controller.testRemVBox.getChildren()){
            counter++;
        }
        if(counter == 1){
            FXML1Controller.checkMarkDBL.setVisible(true);
        }
    }
    //recursion
    public static String randomId(String num){
        if(num.length() == 8){
            return num;
        }
        int digit = (int)(Math.random()*10);
        return randomId(num + String.valueOf(digit));
    }
    public static boolean idExists(int id){
        for(int i = 0; i<ids.size(); i++){
            if(ids.get(i) == id){
                return true;
            }
        }
        return false;
    }
    public int getId(){
        return id;
    }
    public JFXCheckBox getCheckBox(){
        return checkBoxDBL;
    }
}
