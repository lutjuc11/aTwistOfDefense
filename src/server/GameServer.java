/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**
 *
 * @author philipp
 */
public class GameServer {

    private final int PORTNR;
    private JTextComponent logArea = null;
    private ServerSocket server;
    private ServerThread st;
    private HashMap<String, ObjectOutputStream> clientMap = new HashMap<>();

    public GameServer(int portnr) throws IOException {
        this.PORTNR = portnr;
//        server = new ServerSocket(portnr);
//        server.setSoTimeout(500);
    }

    public GameServer(int portnr, JTextComponent logArea) throws IOException {
        this(portnr);
        this.logArea = logArea;
    }

    public void startServer() {
        if (st == null || !st.isAlive()) {
            try {
                server = new ServerSocket(PORTNR);
                server.setSoTimeout(500);
            } catch (IOException ex) {
                log("Server Exception " + ex.toString());
            }
            st = new ServerThread();
            st.start();
        }
    }

    public void stopServer() {
        if (st != null && st.isAlive()) {
            st.interrupt();
        }
    }

    private synchronized void log(String logText) {
        if (logArea == null) {
            System.out.println(logText);
        } else {
            synchronized (logArea) {
                logArea.setText(logArea.getText() + System.lineSeparator() + logText);
            }
        }
    }

    class ServerThread extends Thread {

        public ServerThread() {
            this.setPriority(Thread.MIN_PRIORITY + 2);
        }

        @Override
        public void run() {
            log("Server started on port: " + PORTNR);
            while (!interrupted()) {
                try {
                    Socket socket = server.accept();
                    log("Connection established with: " + socket.getRemoteSocketAddress().toString());
                    new ClientCommunicationThread(socket).start();
                } catch (SocketTimeoutException ex) {
                    //log("Accept Timeout");
                } catch (Exception ex) {
                    log("Server-Exception: " + ex.toString());

                }
            }
            try {
                server.close();
                log("Server closed");
            } catch (IOException ex) {
                log("Server-Exception: " + ex.toString());
            }
        }
    }

    class ClientCommunicationThread extends Thread {

        Socket socket;
        InputStream is = null;
        OutputStream os = null;

        public ClientCommunicationThread(Socket socket) {
            this.socket = socket;
        }

        //Ablauf der vollständigen Kommunikation zwischen Client und Server
        @Override
        public void run() {
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                ObjectOutputStream oos = new ObjectOutputStream(os);
                Object inputFromClient;

                System.out.println("HALLLO MACI");
                
                while (true) {
                    inputFromClient = (String) ois.readObject();
                    System.out.println("Input from a Client: " + inputFromClient.toString());
                    if (inputFromClient.equals("###ExitingChat###")) {
                        log("Connection closed from: " + socket.getRemoteSocketAddress().toString());
                        oos.writeObject("###GoodBye###");
                        
                        break;
                    }
                }

                os.close();
                is.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                log("Communication failure: " + ex.toString());
            } catch (ClassNotFoundException ex) {
                log("Class not found: " + ex.toString());
            } finally {
                try {
                    os.close();
                    is.close();
                    socket.close();
                } catch (IOException ex) {
                    log("ERROR while closing");
                }
            }

        }
    }

    protected Object performRequest(Object request) {
        return "request contained: " + request.toString();
    }
}
