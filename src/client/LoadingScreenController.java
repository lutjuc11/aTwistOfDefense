/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Juergen
 */
public class LoadingScreenController implements Initializable {

    @FXML
    private TextField txtNickname;

    @FXML
    private TextField txtServerAddress;

    @FXML
    private TextField txtServerPort;

    private GamingClient gc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void onConnect(ActionEvent evt) throws IOException, InterruptedException, ClassNotFoundException {
        if (!txtNickname.getText().isEmpty() && !txtServerAddress.getText().isEmpty() && !txtServerPort.getText().isEmpty()) {
            try {
                InetAddress inet = InetAddress.getByName(txtServerAddress.getText());
                int portnr = Integer.parseInt(txtServerPort.getText());
                gc = new GamingClient(inet, portnr, txtNickname.getText());
                onOpenChampionSelect();
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(null, "Host Addresse nicht gültig!");
                txtServerAddress.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Portnummer nicht gültig!");
                txtServerPort.setText("");
            }
        }
    }

    @FXML
    public void onOpenChampionSelect() throws IOException {
        Stage stage = (Stage) txtNickname.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "ChampionSelectScreen.fxml"
                )
        );
        Parent root = loader.load();
        ChampionSelectScreenController controller = loader.<ChampionSelectScreenController>getController();
        controller.setGamingClient(gc);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
