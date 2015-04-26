/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juergen
 */
public class LoadingController implements Initializable {

    @FXML
    private ProgressBar proLoading;

    @FXML
    private TextField txtNickname;

    @FXML
    private TextField txtServerAddress;

    @FXML
    private TextField txtServerPort;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void onConnect(ActionEvent evt) {
        if (!txtNickname.getText().isEmpty() && !txtServerAddress.getText().isEmpty() && !txtServerPort.getText().isEmpty()) {
            try {
                onOpenChampionSelect();
                //new LoadingThread().start();
            } catch (IOException ex) {
                Logger.getLogger(LoadingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoadingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class LoadingThread extends Thread {

        @Override
        public void run() {
            Random rand = new Random();
            for (int i = 1; i <= 100; i++) {
                try {
                    double value = i;
                    proLoading.setProgress(value / 100.0);
                    if (i < 50) {
                        Thread.sleep(rand.nextInt(200));
                    } else {
                        Thread.sleep(20);
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Fatal Error @ Loading");
                }
            }
            try {
                onOpenChampionSelect();
            } catch (Exception ex) {
                System.out.println("FATAL ERROR: " + ex.toString());
            }
        }

    }

    @FXML
    public void onOpenChampionSelect() throws IOException, InterruptedException {
        Stage stage = (Stage) proLoading.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("ChampionSelect.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}
