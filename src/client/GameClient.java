/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.awt.TextArea;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import server.GameServer;

/**
 *
 * @author philipp
 */
public class GameClient {

    private int portnr;
    private static InetAddress addr;
    private OutputStream os;
    private ObjectOutputStream oos;
    private InputStream is;
    private ObjectInputStream ois;
    private Socket socket;
    private String inputFromClient;
    private String playerName;

    static {
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GameClient(int portnr, String playerName) throws IOException, ClassNotFoundException {        
        this.portnr = portnr;
        this.playerName = playerName;
        socket = new Socket(addr, portnr);
        os = socket.getOutputStream();
        oos = new ObjectOutputStream(os);
        is = socket.getInputStream();
        ois = new ObjectInputStream(is);

        new InputThread().start();
    }

    public void sendData(Object object) {
        try {
            oos.writeObject(object);
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class InputThread extends Thread {

        @Override
        public void run() {

            try {
                while (true) {
                    Object response = ois.readObject();
                    if (response instanceof String) {
                        if (response.equals("###GoodBye###")) {
                            break;
                        }
                    }
                }
                os.close();
                is.close();
                socket.close();
                this.interrupt();
            } catch (IOException ex) {
                Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
