/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import hattmakarna.data.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import static hattmakarna.data.Hattmakarna.idb;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author Gastinlogg
 */
public class MaterialOrderWindow extends javax.swing.JFrame {

    private ArrayList<HashMap<String, String>> orderAndHats;
    private HatRegister hatRegister;
    private final User userLoggedIn;

    private HashMap<String, HashMap<String, Double>> orderData;
    HashMap<String, Double> totalMaterial;

    /**
     * Creates new form MaterialOrderWindow
     */
    public MaterialOrderWindow(User userLoggedIn) {
        initComponents();
        this.hatRegister = new HatRegister(idb);
        this.orderAndHats = null;
        this.userLoggedIn = userLoggedIn;
        initHats(null, null);
        totalMaterial = new HashMap<>();
        orderData = getOrderMaterialData();

        initTable();
        setLocationRelativeTo(null);
    }

    private void initTable() {
        paneOrderInfo.setLayout(new BoxLayout(paneOrderInfo, BoxLayout.Y_AXIS));
        fillTable();
        // Let scroll pane control the visible size
        scrollOrderInfo.setPreferredSize(new Dimension(220, 170));
        paneOrderInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));

        // Attach panel to scroll
        scrollOrderInfo.setViewportView(paneOrderInfo);

        // Refresh UI
        paneOrderInfo.revalidate();
    }

    private HashMap<String, HashMap<String, Double>> getOrderMaterialData() {
        HashMap<String, HashMap<String, Double>> orderData = new HashMap<>();
        
        if(orderAndHats == null){
                return orderData;
            }
        
        for (HashMap<String, String> order : orderAndHats) {
            String orderId = order.get("order_id");
            Order aOrder = new Order(orderId);

            if (aOrder.getMaterialOrdered()) {
                continue;
            }

            HashMap<String, Double> orderMaterial = new HashMap<>();

            for (Hat hat : hatRegister.getAllHats()) {

                if (!hat.getOrderId().equals(orderId) || !hat.isSpecial()) {
                    continue;
                }

                MaterialOrder mo = new MaterialOrder(hat.gethatId());

                for (HashMap<String, String> row : mo.getMaterialList()) {
                    String materialId = row.get("material_id");
                    String color = row.get("color");
                    double amount = safeParseDouble(row.get("amount"));
                    String key = materialId + " " + color;

                    orderMaterial.merge(key, amount, Double::sum);
                    totalMaterial.merge(key, amount, Double::sum);
                }
                orderData.put(orderId, orderMaterial);
            }

        }
        return orderData;
    }

    public void initHats(Date fromDate, Date toDate) {
        orderAndHats = MaterialOrder.getOrdersWithMaterials(fromDate, toDate);
    }

    public void fillTable() {
        for (String orderId : orderData.keySet()) {
            paneOrderInfo.add(new JLabel("Order: " + orderId));

            HashMap<String, Double> materials = orderData.get(orderId);

            for (String key : materials.keySet()) {
                double amount = materials.get(key);

                // Split materialId and color (optional)
                String[] parts = key.split(" ");
                String materialId = parts[0];
                String color = parts.length > 1 ? parts[1] : "";

                Material material = new Material(materialId);
                String name = material.getName();
                String unit = material.getUnit();

                paneOrderInfo.add(new JLabel("  Material: " + name + ", " + color + ", " + amount + " " + unit));
            }

            paneOrderInfo.add(Box.createVerticalStrut(10)); // Blank line between orders
        }
        boolean hasSomethingToPrint = !orderData.isEmpty();

        jButton1.setEnabled(hasSomethingToPrint);
    }

    private void updateFilteredOrders() {
        Date from = dcFromDate.getDate();
        Date to = dcToDate.getDate();

        initHats(from, to);

        totalMaterial.clear();
        orderData = getOrderMaterialData();

        paneOrderInfo.removeAll();
        fillTable();
        paneOrderInfo.revalidate();
        paneOrderInfo.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        scrollOrderInfo = new javax.swing.JScrollPane();
        paneOrderInfo = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        dcFromDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dcToDate = new com.toedter.calendar.JDateChooser();
        btnClearDate = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Skriv ut lista");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Materialbeställning");

        jLabel2.setText("Redo för materialbeställning:");

        javax.swing.GroupLayout paneOrderInfoLayout = new javax.swing.GroupLayout(paneOrderInfo);
        paneOrderInfo.setLayout(paneOrderInfoLayout);
        paneOrderInfoLayout.setHorizontalGroup(
            paneOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 191, Short.MAX_VALUE)
        );
        paneOrderInfoLayout.setVerticalGroup(
            paneOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 297, Short.MAX_VALUE)
        );

        scrollOrderInfo.setViewportView(paneOrderInfo);

        btnBack.setText("Tillbaka");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        dcFromDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcFromDatePropertyChange(evt);
            }
        });

        jLabel3.setText("Från datum:");

        jLabel4.setText("Till datum:");

        dcToDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcToDatePropertyChange(evt);
            }
        });

        btnClearDate.setText("Rensa datum");
        btnClearDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearDateActionPerformed(evt);
            }
        });

        jLabel5.setText("Material");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollOrderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(dcToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dcFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jButton1)
                            .addComponent(btnClearDate)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 59, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnClearDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(scrollOrderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(101, 101, 101))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        exportToPDF();
        this.setVisible(false);
        new MaterialOrderWindow(userLoggedIn).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.setVisible(false);
        new MainMenu(userLoggedIn).setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void dcFromDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcFromDatePropertyChange
        if ("date".equals(evt.getPropertyName())) {
            updateFilteredOrders();
        }
    }//GEN-LAST:event_dcFromDatePropertyChange

    private void dcToDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcToDatePropertyChange
        if ("date".equals(evt.getPropertyName())) {
            updateFilteredOrders();
        }
    }//GEN-LAST:event_dcToDatePropertyChange

    private void btnClearDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearDateActionPerformed
        dcFromDate.setDate(null);
        dcToDate.setDate(null);
        updateFilteredOrders();
    }//GEN-LAST:event_btnClearDateActionPerformed


    private void exportToPDF() {

        try {
            //skapar ett PDF dokument och lägger till en sida
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            //möjliggör att text och grafik genereras på sidan
            //teckensnitt, srtl och radavstånd sätts samt startposition anges
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 700);

            // Huvudrubrik
            contentStream.showText("MATERIALÖVERSIKT FÖR ORDRAR");
            contentStream.newLine();
            contentStream.newLine();

            //lista som sammanställer ordrarnas material
            writeOrdersInPDF(contentStream);
            writeTotalSummaryPDF(contentStream);

            //textskrivning avslutas och stängs
            contentStream.endText();
            contentStream.close();

            //PDF sparas och stängs för att sedan öppnas
            document.save("materialorder.pdf");
            document.close();
            openPDF("materialorder.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void openPDF(String fileName) {
        try {
            File pdfFile = new File(fileName);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("PDF filen hittades inte");
            }
        } catch (IOException e) {
            System.out.println("Kunde inte öppna PDF: " + e.getMessage());
        }
    }

    private void writeOrdersInPDF(PDPageContentStream contentStream) {
        try {
            for (String orderId : orderData.keySet()) {
                contentStream.showText("Order: " + orderId);
                contentStream.newLine();

                HashMap<String, Double> orderMaterial = orderData.get(orderId);

                for (String key : orderMaterial.keySet()) {
                    String id = key.replaceAll("[^0-9]", "");
                    String color = key.replaceAll("[0-9]", "");
                    Material material = new Material(id);
                    String unit = material.getUnit();
                    String name = material.getName();

                    double amount = orderMaterial.get(key);
                    contentStream.showText("Material: " + name + "," + color + ", " + amount + " " + unit);
                    contentStream.newLine();
                }

                contentStream.newLine(); // space between orders

                // Optional: update material ordered flag
                Order aOrder = new Order(orderId);
                aOrder.setMaterialOrdered(true);
                aOrder.save();
            }
        } catch (IOException e) {
            System.out.println("Fel vid utskrift av ordrar");
        }
    }

    private void writeTotalSummaryPDF(PDPageContentStream contentStream) {

        try {
            contentStream.showText("TOTAL MATERIALSUMMERING:");
            contentStream.newLine();

            for (String key : totalMaterial.keySet()) {
                String id = key.replaceAll("[^0-9]", "");
                String color = key.replaceAll("[0-9]", "");
                Material material = new Material(id);
                String unit = material.getUnit();
                String name = material.getName();

                double amount = totalMaterial.get(key);
                contentStream.showText("Material: " + name + "," + color + ", " + amount + " " + unit);
                contentStream.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fel vid totalsummering");
        }
    }

    private double safeParseDouble(String s) {
        try {
            return (s != null && !s.isEmpty()) ? Double.parseDouble(s) : 0.0;
        } catch (NumberFormatException e) {
            System.out.println("Ogiltig mängd: " + s + " – behandlas som 0.0");
            return 0.0;
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
            java.util.logging.Logger.getLogger(MaterialOrderWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MaterialOrderWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MaterialOrderWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MaterialOrderWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MaterialOrderWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClearDate;
    private com.toedter.calendar.JDateChooser dcFromDate;
    private com.toedter.calendar.JDateChooser dcToDate;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel paneOrderInfo;
    private javax.swing.JScrollPane scrollOrderInfo;
    // End of variables declaration//GEN-END:variables
}
