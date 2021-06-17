/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author varda
 */
public class FXML1Controller extends TaskController implements Initializable {

    @FXML
    private AnchorPane sunPanel;
    @FXML
    private AnchorPane menuPanel;
    @FXML
    private ImageView sun;
    @FXML
    private ImageView clock;
    @FXML
    private TextField taskAdder;
    @FXML
    private VBox taskItems;
    @FXML
    private ScrollPane scrollPane1;
    @FXML
    private AnchorPane clockPanel;
    @FXML
    private VBox removedTasksVBox;
    public static VBox testVBox = new VBox();
    public static VBox testRemVBox = new VBox();
    //Stage stage = (Stage) pyramidPanel.getScene().getWindow();
    //Scene scene1, scene2;
    /**
     * Initializes the controller class.
     */
    public static int countTasks = 0;
    @FXML
    private ImageView addTaskImg;
    private ImageView addTaskText;
    @FXML
    private ImageView checkMark;
    public static ImageView checkMarkDBL = new ImageView();
    public static ImageView addTaskDBL = new ImageView();
    Connection con;
    Statement stmt;
    ResultSet rs;
    @FXML
    private JFXCheckBox isNewDay;
    public static JFXCheckBox isNewDayDBL = new JFXCheckBox();
    public boolean DoConnect() {
        try{
            //Connect to the database
            String host = "jdbc:derby://localhost:1527/Employees";
            String uName = "Arixxxx";
            String uPass = "Arixxxxxxxxx";
            con = DriverManager.getConnection(host, uName, uPass);
            
            //Execute SQL and load records into the resultset
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String SQL = "select*from HABILISDATA";
            rs = stmt.executeQuery(SQL);
            
//            stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            String SQL2 = "select*from TERRAPLANTS";
//            rs2 = stmt2.executeQuery(SQL2);
            
            
            
            
        }
        catch(SQLException err){
            System.out.println("Couldn't connect ttt");
            return false;
        }
        System.out.println("connected");
        return true;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       DoConnect();
       testVBox = taskItems;
       testRemVBox = removedTasksVBox;
       checkMarkDBL = checkMark;
       addTaskDBL = addTaskImg;
       isNewDayDBL = isNewDay;
    }    

    @FXML
    private void goToDay(MouseEvent event) throws IOException {
        sunPanel.toFront();
    }

    private void goToOrganizing(MouseEvent event) {
    }

    @FXML
    private void goToRemoved(MouseEvent event) {
        clockPanel.toFront();
    }
    public void addRecord(int missedDays, String id){
        try{
            rs.moveToInsertRow();
            rs.updateString("ID", id);
            rs.updateInt("NUMMISSED", missedDays);
            rs.updateString("TASK", labelt.getText());
            rs.updateBoolean("ISTASK", true);
            rs.insertRow();
            stmt.close();
            rs.close();
            System.out.println("record added");
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT * FROM HABILISDATA";
            rs = stmt.executeQuery(sql);
        }
        catch(SQLException err){
            System.out.println("Couldn't add record");
        }
    }
    @FXML
    public void makeTask(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            try{
                Node someTask = FXMLLoader.load(getClass().getResource("Task.fxml"));
                taskItems.setSpacing(5);
                labelt.setText(taskAdder.getText());
                //Task.allTasks.add(new Task(Task.allTasks.indexOf(countTasks), someTask));
                countTasks++;
                taskItems.getChildren().add(0, someTask);
                taskAdder.setText("");
                addTaskImg.setVisible(false);
                addTaskText.setVisible(false);
            }
            catch(Exception e){

            }
            System.out.println(TaskController.idDBL.getText());
            addRecord(0, TaskController.idDBL.getText());
        }
    }

    @FXML
    private void askDayInvisible(ActionEvent event) {
        isNewDay.setVisible(false);
    }
    
}
