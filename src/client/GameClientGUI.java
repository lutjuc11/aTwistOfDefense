/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import bl.Unit;
import client.GamingClient;
import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import server.GamingServer;

/**
 *
 * @author philipp
 */
public class GameClientGUI extends javax.swing.JFrame {

    private DefaultListModel<String> model = new DefaultListModel<>();
    private GamingClient gameclient;
    private LinkedList<Unit> unitList = new LinkedList<>();
    private DefaultListModel dlm = new DefaultListModel();

    public GameClientGUI() {
        initComponents();
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNickname = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtaddress = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        btnConnect = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        liDisplayStats = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                onClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                onClosing(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(1, 7));

        jLabel3.setText("Nickname:");
        jPanel1.add(jLabel3);
        jPanel1.add(txtNickname);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("server address: ");
        jPanel1.add(jLabel1);

        txtaddress.setText("localhost");
        jPanel1.add(txtaddress);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("port: ");
        jPanel1.add(jLabel2);

        txtPort.setText("9998");
        jPanel1.add(txtPort);

        btnConnect.setText("connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        jPanel1.add(btnConnect);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jButton1.setText("show");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, java.awt.BorderLayout.PAGE_END);

        jScrollPane1.setViewportView(liDisplayStats);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void onClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onClosed

    }//GEN-LAST:event_onClosed

    private void onClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onClosing
        if (gameclient != null) {
            gameclient.sendData("###ExitingChat###");
        }
    }//GEN-LAST:event_onClosing

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        
    }//GEN-LAST:event_btnConnectActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dlm.clear();
        readCsv();
        for (Unit unit : unitList) {
            dlm.addElement(unit.toString());
        }
        liDisplayStats.setModel(dlm);
    }//GEN-LAST:event_jButton1ActionPerformed

    public void readCsv() {
        FileReader fr = null;
        try {
            String sep = File.separator;
            fr = new FileReader(System.getProperty("user.dir") + sep + "src" + sep + "res" + sep + "UnitData.csv");
            BufferedReader br = new BufferedReader(fr);
            String line;
            String splitedLine[];
            int unitID, Health, ad, ap, armor, magicres, range, movespeed, costs;
            double attackspeed;
            String displayname, typ;

            while ((line = br.readLine()) != null) {
                splitedLine = line.split(";");
                unitID = Integer.parseInt(splitedLine[0]);
                displayname = splitedLine[1];
                Health = Integer.parseInt(splitedLine[2]);
                ad = Integer.parseInt(splitedLine[3]);
                ap = Integer.parseInt(splitedLine[4]);
                armor = Integer.parseInt(splitedLine[5]);
                magicres = Integer.parseInt(splitedLine[6]);
                attackspeed = Double.parseDouble(splitedLine[7]);
                range = Integer.parseInt(splitedLine[8]);
                movespeed = Integer.parseInt(splitedLine[9]);
                typ = splitedLine[10];
                costs = Integer.parseInt(splitedLine[11]);
                unitList.add(new Unit(unitID, displayname, Health, ad, ap, armor, magicres, attackspeed, range, movespeed, typ, costs));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList liDisplayStats;
    private javax.swing.JTextField txtNickname;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtaddress;
    // End of variables declaration//GEN-END:variables
}
