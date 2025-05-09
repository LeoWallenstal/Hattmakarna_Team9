/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.data.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gastinlogg
 */
public class OrderInfoWindow extends javax.swing.JFrame {

    private Order currentOrder;
    private DefaultTableModel table;
    private boolean isInitialized;
    private boolean initializedMaterialOrder;
    private OrderOverviewWindow window;
    private ScheduleManager scheduleManager;
    private HashMap<String, String> modelToFirstHatId = new HashMap<>();

    /**
     * Creates new form OrderInfoWindow
     */
    public OrderInfoWindow(OrderOverviewWindow window, Order order) {
        initComponents();
        this.currentOrder = order;
        this.window = window;
        scheduleManager = null;
        this.table = (DefaultTableModel) tblHats.getModel();
        this.setTitle("Order information");

        fillTable();
        initStatusCb();
        setStatus();
        initMaterialOrderCb();
        setStatusMaterialOrder();
        lblSuccessFailed.setVisible(false);
        btnDeleteOrder.setVisible(false);
        setInfo();
        setLocationRelativeTo(null);
        this.isInitialized = true;
        initializedMaterialOrder = true;
    }

    public OrderInfoWindow(ScheduleManager scheduleManager, Order order) {
        initComponents();
        this.currentOrder = order;
        this.window = null;
        this.scheduleManager = scheduleManager;
        this.table = (DefaultTableModel) tblHats.getModel();

        fillTable();
        initStatusCb();
        setStatus();
        initMaterialOrderCb();
        setStatusMaterialOrder();
        lblSuccessFailed.setVisible(false);
        btnDeleteOrder.setVisible(false);
        setInfo();
        setLocationRelativeTo(null);
        this.isInitialized = true;
        initializedMaterialOrder = true;
    }

    private void setInfo() {
        lblOrderNr.setText("Ordernummer: " + currentOrder.getOrderId());

        Date date = currentOrder.getReceivedDate();
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateF.format(date);
        lblDate.setText("Datum: " + formattedDate);
        lblPrice.setText("Pris: " + currentOrder.getTotalPrice());
        String id = "" + currentOrder.getCustomerId();
        Customer customer = new Customer(id);
        lblCustomer.setText("Kund: " + id + ", " + customer.getFullName());

        if (currentOrder.getStatus().toString().equals("MOTTAGEN")) {
            btnDeleteOrder.setVisible(true);
        }

    }

    private void fillTable() {
        List<Hat> hats = currentOrder.fetchHatObjects();

        ModelRegister modelRegister = new ModelRegister();

        for (Hat hat : hats) {
            String modelName;

            if (hat.isSpecial()) {
                modelName = "Special";

            } else {
                modelName = modelRegister.getModel(hat.getModelId()).getName();
            }

            table.addRow(new Object[]{modelName, 1}); // alltid 1 hatt per rad
        }
    }

//    private void fillTable() {
//        List<Hat> hatIds = currentOrder.fetchHatObjects();
//       
//        HashMap<String, Integer> hatCount = new HashMap<>();
//        for (Hat hat : hatIds) {
//            String model = new ModelRegister().getModel(hat.getModelId()).getName();
//            hatCount.put(model, hatCount.getOrDefault(model, 0) + 1);
//        }
//
//        for (String model : hatCount.keySet()) {
//            table.addRow(new Object[]{model, hatCount.get(model)});
//        }
//    }
    private void initStatusCb() {
        for (Status status : Status.values()) {
            cbStatus.addItem(status.name());
        }
    }

    public void setStatus() {
        cbStatus.setSelectedItem(currentOrder.getStatus().toString());
    }

    public void initMaterialOrderCb() {
        cbMaterialOrder.addItem("BESTÄLLT");
        cbMaterialOrder.addItem("EJ BESTÄLLT");
    }

    public void setStatusMaterialOrder() {
        boolean special = false;
        for (Hat aHat : currentOrder.fetchHatObjects()) {
            if (aHat.isIsSpecial()) {
                special = true;
                break;
            }

        }

        if (special) {
            if (currentOrder.isMaterialOrdered()) {
                cbMaterialOrder.setSelectedItem("BESTÄLLT");
            } else {
                cbMaterialOrder.setSelectedItem("EJ BESTÄLLT");
            }
        } else {
            lblMaterial.setVisible(false);
            cbMaterialOrder.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblOrderNr = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        lblDate = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblCustomer = new javax.swing.JLabel();
        cbMaterialOrder = new javax.swing.JComboBox<>();
        lblMaterial = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHats = new javax.swing.JTable();
        btnDeleteOrder = new javax.swing.JButton();
        lblSuccessFailed = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(400, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblOrderNr.setText("Ordernummer:");

        cbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbStatusItemStateChanged(evt);
            }
        });
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        lblDate.setText("Datum:");

        lblPrice.setText("Pris:");

        lblCustomer.setText("Kund:");

        cbMaterialOrder.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMaterialOrderItemStateChanged(evt);
            }
        });

        lblMaterial.setText("Material:");

        jLabel4.setText("Orderstatus:");

        tblHats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hatt", "Antal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHats.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblHats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHatsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHats);
        if (tblHats.getColumnModel().getColumnCount() > 0) {
            tblHats.getColumnModel().getColumn(0).setResizable(false);
            tblHats.getColumnModel().getColumn(1).setResizable(false);
        }

        btnDeleteOrder.setText("Ta bort order");
        btnDeleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOrderActionPerformed(evt);
            }
        });

        lblSuccessFailed.setText("borttagen/misslyckades");

        jButton1.setText("Skapa fraktsedel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDeleteOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSuccessFailed)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblPrice)
                                .addComponent(lblDate)
                                .addComponent(lblOrderNr)
                                .addComponent(lblCustomer))
                            .addGap(145, 145, 145)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(lblMaterial))
                                .addComponent(cbMaterialOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblOrderNr)
                        .addGap(18, 18, 18)
                        .addComponent(lblDate)
                        .addGap(12, 12, 12)
                        .addComponent(lblPrice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCustomer))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaterial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMaterialOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteOrder)
                    .addComponent(lblSuccessFailed)
                    .addComponent(jButton1))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbStatusItemStateChanged
        if (!isInitialized || evt.getStateChange() != ItemEvent.SELECTED) {
            return;
        }
        currentOrder.setStatus(Status.valueOf(cbStatus.getSelectedItem().toString()));
        currentOrder.save();
        if (window != null) {
            window.initTable();
        } else if (scheduleManager != null) {
            scheduleManager.buildOrdersPanel(true);
        }

        if (cbStatus.getSelectedItem().toString().equals("MOTTAGEN")) {
            btnDeleteOrder.setVisible(true);
        } else {
            btnDeleteOrder.setVisible(false);
        }
    }//GEN-LAST:event_cbStatusItemStateChanged

    private void cbMaterialOrderItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMaterialOrderItemStateChanged
        if (!isInitialized || evt.getStateChange() != ItemEvent.SELECTED) {
            return;
        }
        if (cbMaterialOrder.getSelectedItem().equals("BESTÄLLD")) {
            currentOrder.setMaterialOrdered(true);
        } else {
            currentOrder.setMaterialOrdered(false);
        }
        currentOrder.save();
    }//GEN-LAST:event_cbMaterialOrderItemStateChanged

    private void btnDeleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOrderActionPerformed
        boolean success = currentOrder.delete();
        lblSuccessFailed.setVisible(true);
        if (success) {
            lblSuccessFailed.setText("Borttagen!");
        } else {
            lblSuccessFailed.setText("Misslyckades!");
        }
    }//GEN-LAST:event_btnDeleteOrderActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed

    }//GEN-LAST:event_cbStatusActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (window != null)
            window.initTable();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String id = Integer.toString(currentOrder.getOrderId());

        new FraktSedelUI(this, id).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblHatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHatsMouseClicked
        System.out.println("Klickad på tabellrad");

        int row = tblHats.getSelectedRow();
        if (row == -1) {
            System.out.println("Ingen rad vald.");
            return;
        }

        String clickedModelName = "Special";
        System.out.println("Klickat modellnamn: " + clickedModelName);

        ArrayList<String> hatIds = (ArrayList<String>) currentOrder.fetchHatIds();
        boolean matchFound = false;

        if (hatIds.isEmpty()) {
            System.out.println("Inga hatt-ID:n hittades.");
            return;
        }

        for (String hatId : hatIds) {
            Hat hat = new Hat(hatId);
            String modelName = new ModelRegister().getModel(hat.getModelId()).getName();

            System.out.println("Kontrollerar hatt: " + hatId + " med modellnamn: " + modelName);

            if (modelName.equals(clickedModelName)) {
                System.out.println("Match hittad! Öppnar HattViewerWindow med ID: " + hatId);

                // Check if images exist for this hat
                if (imagesForHatExist(hatId)) {
                    new HattViewerWindow(Integer.parseInt(hatId)).setVisible(true);
                } else {
                    System.out.println("Inga bilder hittades för hatt ID: " + hatId);
                    // Open the window with a default or message indicating no images
                    new HattViewerWindow(Integer.parseInt(hatId)).setVisible(true); // Open with default view
                }

                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            System.out.println("Ingen matchande hatt hittades.");
        }

    }//GEN-LAST:event_tblHatsMouseClicked

    public boolean imagesForHatExist(String hatId) {
        String imageDir = "images/hattextra" + hatId;
        File folder = new File(imageDir);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

        // Check if any files are found in the directory
        return files != null && files.length > 0;
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
            java.util.logging.Logger.getLogger(OrderInfoWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderInfoWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderInfoWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderInfoWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new OrderInfoWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteOrder;
    private javax.swing.JComboBox<String> cbMaterialOrder;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblMaterial;
    private javax.swing.JLabel lblOrderNr;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblSuccessFailed;
    private javax.swing.JTable tblHats;
    // End of variables declaration//GEN-END:variables
}
