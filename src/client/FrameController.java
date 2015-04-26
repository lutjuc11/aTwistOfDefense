/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Juergen
 */
public class FrameController implements Initializable {

    @FXML
    private Button btnClose;

    @FXML
    private Button btnStart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void onStart(ActionEvent evt) throws IOException, InterruptedException {
        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("loading.fxml"));
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add("css/format.css");
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onOptions(ActionEvent evt) {
        JOptionPane.showMessageDialog(null, "Options");
    }

    @FXML
    public void onQuit(ActionEvent evt) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

}
