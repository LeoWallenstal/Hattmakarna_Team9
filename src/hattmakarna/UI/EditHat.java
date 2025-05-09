/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;
import hattmakarna.data.Customer;
import hattmakarna.data.Model;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import hattmakarna.data.ModelRegister;
import hattmakarna.data.User;
import hattmakarna.util.Validerare;
import javax.swing.JOptionPane;
/**
 *
 * @author sebas
 */
public class EditHat extends javax.swing.JFrame {
 private final Model currentModel;
 private User userLoggedIn;
 
    public EditHat(User userLoggedIn,Model model) {
        initComponents();
         this.currentModel = model;
          this.userLoggedIn = userLoggedIn;
          
       if (currentModel != null) {
    tfPrice.setText(String.valueOf(currentModel.getPrice()));
    tfName.setText(String.valueOf(currentModel.getName()));
}        this.setTitle("Hantera hatt");

         
        
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeader = new javax.swing.JLabel();
        lblInStockHats = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        tfPrice = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        btnSettPrice = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfName1 = new javax.swing.JTextField();
        lblErrorName = new javax.swing.JLabel();
        lblErrorPrice = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblHeader.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblHeader.setText("Redigera hatt");

        lblInStockHats.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInStockHats.setText("Lagerförda hattar");

        lblPrice.setText("Pris:");

        tfPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPriceActionPerformed(evt);
            }
        });

        btnBack.setText("Tillbaka");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnSettPrice.setText("Spara");
        btnSettPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettPriceActionPerformed(evt);
            }
        });

        jLabel2.setText("Namn:");

        jLabel3.setText("Namn:");

        tfName1.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInStockHats)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblPrice)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnSettPrice)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblErrorName)
                                    .addComponent(lblErrorPrice)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnBack))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lblHeader)))
                .addContainerGap(276, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblHeader)
                .addGap(109, 109, 109)
                .addComponent(lblInStockHats)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblErrorName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblErrorPrice)
                    .addComponent(lblPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSettPrice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSettPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettPriceActionPerformed

        boolean editOK = true;

    String name = tfName.getText().trim();
    String priceText = tfPrice.getText().trim();

    // Töm tidigare felmeddelanden
    lblErrorName.setText("");
    lblErrorPrice.setText("");

    // --- Namnvalidering ---
    if (name.isEmpty()) {
        lblErrorName.setText("Får inte vara tomt!");
        editOK = false;
    } else if (!Validerare.validateName(name)) {
        lblErrorName.setText("Får bara innehålla bokstäver och bindestreck!");
        editOK = false;
    }

    // --- Prisvalidering ---
    if (priceText.isEmpty()) {
        lblErrorPrice.setText("Får inte vara tomt!");
        editOK = false;
    } else if (!Validerare.validatePrice(priceText)) {
        lblErrorPrice.setText("Endast siffror + punkt eller komma är tillåtna!");
        editOK = false;
    }

    // --- Om allt är OK: spara ändringar ---
    if (editOK) {
        try {
            double price = Double.parseDouble(priceText.replace(',', '.'));
            currentModel.updatePrice(currentModel.getModelID(), price);
            currentModel.updateName(name);

            

            new HattWindow(userLoggedIn).setVisible(true);
            this.dispose();
        } catch (NumberFormatException e) {
            lblErrorPrice.setText("Ogiltigt format. Kontrollera decimaltecken.");
        }
    }

    }//GEN-LAST:event_btnSettPriceActionPerformed

    private void tfPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPriceActionPerformed
                
    }//GEN-LAST:event_tfPriceActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed

        new HattWindow(userLoggedIn).setVisible(true);
        this.dispose();    



    }//GEN-LAST:event_btnBackActionPerformed

    /**
     * @param args the command line arguments
     */
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSettPrice;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblErrorName;
    private javax.swing.JLabel lblErrorPrice;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblInStockHats;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfName1;
    private javax.swing.JTextField tfPrice;
    // End of variables declaration//GEN-END:variables
}
