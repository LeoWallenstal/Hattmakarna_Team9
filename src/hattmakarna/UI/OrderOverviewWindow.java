/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.data.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.SwingUtilities;

/**
 *
 * @author Gastinlogg
 */
public class OrderOverviewWindow extends javax.swing.JFrame {

    private DefaultTableModel table;
    private User userLoggedIn;
    private ArrayList<Order> orders;

    /**
     * Creates new form OrderOverviewWindow
     */
    public OrderOverviewWindow(User userLoggedIn) {
        initComponents();
        this.userLoggedIn = userLoggedIn;
        setLocationRelativeTo(null);
        this.setTitle("Order översikt");

        if (tblOrders != null) {
            this.table = (DefaultTableModel) tblOrders.getModel();
            initTable();
        } else {
            System.err.println("tblOrders är null – kontrollera initComponents()");
        }
    }

    public void initTable() {
        table.setRowCount(0);

        orders = new OrderRegister().getOrders();
        
        orders.sort(Comparator.comparingInt(Order::getOrderId));

        for (Order aOrder : orders) {
            int id = aOrder.getOrderId();

            String status = aOrder.getStatus().toString();
            Date date = aOrder.getReceivedDate();
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateF.format(date);
            table.addRow(new Object[]{id, formattedDate, status});
        }
    }

    public void filterTable(Date from, Date to) {

        table.setRowCount(0);

        if (from != null) {
            Calendar calFrom = Calendar.getInstance();
            calFrom.setTime(from);
            calFrom.set(Calendar.HOUR_OF_DAY, 0);
            calFrom.set(Calendar.MINUTE, 0);
            calFrom.set(Calendar.SECOND, 0);
            calFrom.set(Calendar.MILLISECOND, 0);
            from = calFrom.getTime();
        }

        if (to != null) {
            Calendar calTo = Calendar.getInstance();
            calTo.setTime(to);
            calTo.set(Calendar.HOUR_OF_DAY, 23);
            calTo.set(Calendar.MINUTE, 59);
            calTo.set(Calendar.SECOND, 59);
            calTo.set(Calendar.MILLISECOND, 999);
            to = calTo.getTime();
        }
        
        orders.sort(Comparator.comparingInt(Order::getOrderId));

        if (from == null && to == null) {
            for (Order aOrder : orders) {
                int id = aOrder.getOrderId();

                String status = aOrder.getStatus().toString();
                Date date = aOrder.getReceivedDate();
                SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateF.format(date);
                table.addRow(new Object[]{id, formattedDate, status});
            }
        } else if (from != null && to != null) {
            for (Order aOrder : orders) {
                Date recivedDate = aOrder.getReceivedDate();
                if ((recivedDate.equals(from) || recivedDate.after(from))
                        && (recivedDate.equals(to) || recivedDate.before(to))) {
                    int id = aOrder.getOrderId();
                    String status = aOrder.getStatus().toString();
                    SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateF.format(recivedDate);
                    table.addRow(new Object[]{id, formattedDate, status});
                }
            }
        } else if (from != null && to == null) {
            for (Order aOrder : orders) {
                Date recivedDate = aOrder.getReceivedDate();
                if ((recivedDate.equals(from) || recivedDate.after(from))) {
                    int id = aOrder.getOrderId();
                    String status = aOrder.getStatus().toString();
                    SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateF.format(recivedDate);
                    table.addRow(new Object[]{id, formattedDate, status});
                }
            }
        } else if (from == null && to != null) {
            for (Order aOrder : orders) {
                Date recivedDate = aOrder.getReceivedDate();
                if ((recivedDate.equals(to) || recivedDate.before(to))) {
                    int id = aOrder.getOrderId();
                    String status = aOrder.getStatus().toString();
                    SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateF.format(recivedDate);
                    table.addRow(new Object[]{id, formattedDate, status});
                }
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        lblOverview = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        dcDateFrom = new com.toedter.calendar.JDateChooser();
        dcDateTo = new com.toedter.calendar.JDateChooser();
        lblDateFrom = new javax.swing.JLabel();
        lblDateTo = new javax.swing.JLabel();
        btnClearDate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ordernummer", "Beställningsdatum", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrders.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrders);
        if (tblOrders.getColumnModel().getColumnCount() > 0) {
            tblOrders.getColumnModel().getColumn(0).setResizable(false);
            tblOrders.getColumnModel().getColumn(1).setResizable(false);
            tblOrders.getColumnModel().getColumn(2).setResizable(false);
        }

        lblOverview.setText("Orderöversikt");

        jButton1.setText("Registrera order");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnReturn.setText("Tillbaka");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        dcDateFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcDateFromPropertyChange(evt);
            }
        });

        dcDateTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcDateToPropertyChange(evt);
            }
        });

        lblDateFrom.setText("Från datum:");

        lblDateTo.setText("Till datum:");

        btnClearDate.setText("Rensa datum");
        btnClearDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(lblOverview))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnReturn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(lblDateFrom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblDateTo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClearDate)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblOverview)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dcDateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDateFrom)
                    .addComponent(dcDateTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDateTo)
                    .addComponent(btnClearDate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(btnReturn)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        new MainMenu(userLoggedIn).setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_btnReturnActionPerformed

    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        int row = tblOrders.rowAtPoint(evt.getPoint());

        Order order = orders.get(row);

        new OrderInfoWindow(this, order).setVisible(true);

    }//GEN-LAST:event_orderTableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new OrderWindow(userLoggedIn,this).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnClearDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearDateActionPerformed
        dcDateTo.setDate(null);
        dcDateFrom.setDate(null);
        filterTable(null, null);
    }//GEN-LAST:event_btnClearDateActionPerformed

    private void dcDateFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcDateFromPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            Date from = dcDateFrom.getDate();
            Date to = dcDateTo.getDate();
            filterTable(from, to);
        }
    }//GEN-LAST:event_dcDateFromPropertyChange

    private void dcDateToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcDateToPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            Date from = dcDateFrom.getDate();
            Date to = dcDateTo.getDate();
            filterTable(from, to);
        }
    }//GEN-LAST:event_dcDateToPropertyChange

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
            java.util.logging.Logger.getLogger(OrderOverviewWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderOverviewWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderOverviewWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderOverviewWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new OrderOverviewWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearDate;
    private javax.swing.JButton btnReturn;
    private com.toedter.calendar.JDateChooser dcDateFrom;
    private com.toedter.calendar.JDateChooser dcDateTo;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDateFrom;
    private javax.swing.JLabel lblDateTo;
    private javax.swing.JLabel lblOverview;
    private javax.swing.JTable tblOrders;
    // End of variables declaration//GEN-END:variables
}
