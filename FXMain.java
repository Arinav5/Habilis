/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static javaapplication8.TaskController.labelt;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 *
 * @author varda
 */
public class FXMain extends Application {
    Stage window;
    static Connection con;
    static Statement stmt;
    static Statement stmt2;
    static Statement stmt3;
    static Statement stmt4;
    static Statement stmt5;
    static Statement stmt6;
    static ResultSet rs;
    static ResultSet rs2;
    static ResultSet rs3;
    static ResultSet rs4;
    static ResultSet rs5;
    static ResultSet rs6;
//    public FXMain(){
//        DoConnect();
//    }
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("FXML1.fxml"));
        //String css = FXMain.class.getResource("fxml.css").toExternalForm();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setScene(scene);
        stage.show();
        DoConnect();
        setAllTasks();
    }
    public void addMissing(String id) throws SQLException{
        String SQL2 = "select*from HABILISDATA where ID= '" + id + "'";
        rs2 = stmt2.executeQuery(SQL2);
        
        rs2.next();
        rs2.updateInt("NUMMISSED", rs2.getInt("NUMMISSED") + 1);
        rs2.updateRow();
    }
    public static void makeNotTask(String id) throws SQLException{
        String SQL4 = "select*from HABILISDATA where ID= '" + id + "'";
        rs4 = stmt4.executeQuery(SQL4);
        
        rs4.next();
        rs4.updateBoolean("ISTASK", false);
        rs4.updateRow();
    }
    public static void makeTaskTrue(String id) throws SQLException{
        String SQL6 = "select*from HABILISDATA where ID= '" + id + "'";
        rs6 = stmt6.executeQuery(SQL6);
        
        rs6.next();
        rs6.updateBoolean("ISTASK", true);
        rs6.updateInt("NUMMISSED", 0);
        rs6.updateRow();
    }
    public static void deleteTaskData(String id) throws SQLException{
        String SQL3 = "select*from HABILISDATA where ID= '" + id + "'";
        rs3 = stmt3.executeQuery(SQL3);
        
        rs3.next();
        rs3.deleteRow();
            
        stmt3.close();
        rs3.close();

        stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
    public void setAllTasks() throws SQLException, IOException{
        while(rs.next()){
            Node someTask = FXMLLoader.load(getClass().getResource("Task.fxml"));
            FXML1Controller.testVBox.setSpacing(5);
            labelt.setText(rs.getString("TASK"));
            FXML1Controller.testVBox.getChildren().add(0, someTask);
            FXML1Controller.addTaskDBL.setVisible(false);
            TaskController.idDBL.setText(rs.getString("ID"));
        }
        while(rs5.next()){
            Node someTask2 = FXMLLoader.load(getClass().getResource("Task.fxml"));
            FXML1Controller.testRemVBox.setSpacing(5);
            labelt.setText(rs5.getString("TASK"));
            FXML1Controller.testRemVBox.getChildren().add(0,someTask2);
            FXML1Controller.checkMarkDBL.setVisible(false);
            TaskController.idDBL.setText(rs5.getString("ID"));
            TaskController.trashCanDBL.setImage(null);
            TaskController.clockTimeDBL.setImage(null);
            TaskController.checkBoxDBL.setVisible(false);
            TaskController.addBackDBL.setVisible(true);
        }
    }
    public boolean DoConnect( ) {
        try{
            //Connect to the database
            String host = "jdbc:derby://localhost:1527/Employees";
            String uName = "Arinav5";
            String uPass = "Arivari123@1!";
            con = DriverManager.getConnection(host, uName, uPass);
            
            //Execute SQL and load records into the resultset
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String SQL = "select*from HABILISDATA where ISTASK = true AND NUMMISSED <= 5";
            rs = stmt.executeQuery(SQL);
            
            stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            stmt4 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            stmt5 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String SQL5 = "select*from HABILISDATA where ISTASK = false OR NUMMISSED > 5";
            rs5 = stmt5.executeQuery(SQL5);
            
            stmt6 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
        catch(SQLException err){
            System.out.println("Couldn't connect");
            return false;
        }
        System.out.println("Connected");
        return true;
    }
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args){
        launch(args);
    }
    
    @FXML
    public void exitApplication(ActionEvent event) {
       Platform.exit();
    }
    @Override
    public void stop() throws SQLException{
        if(FXML1Controller.isNewDayDBL.isSelected()){
            String locId = "";
            int counter = 0;
            ArrayList <AnchorPane> anks = new ArrayList<AnchorPane>();
            for(Node n: FXML1Controller.testVBox.getChildren()){
                if(n instanceof AnchorPane){
                    anks.add((AnchorPane)n);
                }
            }

            for(AnchorPane n: anks){
                for(Node m: n.getChildren()){
                    if(m instanceof JFXCheckBox){
                        if(!((JFXCheckBox) m).isSelected()){
                            for(Node j: anks.get(counter).getChildren()){
                                if(j instanceof Label){
                                    Label l = (Label) j;
                                    locId = l.getText();
                                }
                            }
                            addMissing(locId);
                        }
                        counter++;
                    }

                }
            }

        }
        System.out.println("Stage is closing");
        // Save file
    }
    //components:
    // linear-gradient(to top left, #ff4dad 20%, #9100ff 68%)
}
