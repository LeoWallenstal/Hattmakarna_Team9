/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;
import oru.inf.InfException;
import hattmakarna.data.CustomerRegister;
import java.util.ArrayList;
import java.util.HashMap;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.data.FillComboBox;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.data.Model;
import hattmakarna.data.ModelRegister;
import javax.swing.JOptionPane;
import hattmakarna.data.Customer;
import static hattmakarna.data.Hattmakarna.idb;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author linahanssons
 */
public class CustomerInformationWindow extends javax.swing.JFrame {
    private CustomerRegister customerRegister;

    /**
     * Creates new form CustomerInformationWindow
     */
    public CustomerInformationWindow() {
        this.customerRegister = new CustomerRegister();
        fillTable();
        initComponents();
        
        try{
            ArrayList<String> mailList = idb.fetchColumn("SELECT mail FROM mail;");
            cbMail.removeAllItems();
            for (String mail : mailList){
                cbMail.addItem(mail);
            }
        } 
        catch (InfException e) {
            JOptionPane.showMessageDialog(this,"kunde inte hämta mail" + e.getMessage());
        }
        
        try {
            ArrayList<HashMap<String, String>> customerRows = idb.fetchRows("SELECT first_name, last_name FROM customer");
            cbName.removeAllItems();

            for (HashMap<String, String> row : customerRows) {
                String fName = row.get("first_name");
                String lName = row.get("last_name");

                String comboText = fName + " " + lName;
                cbName.addItem(comboText);
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(this,"kunde inte hämta namn" + e.getMessage());
        }
    }
    
    private void fillTable() {
        //DEBUG
        System.out.println("fillTableKörs");
        ArrayList <Customer> customers = customerRegister.getAllCustomers();
        
        String[] columnNames = {"name", "customerID","email", "phone", "adress", "postalCode", "country" };
        Object[][] data = new Object[customers.size()][3];
        for (int i = 0; i < customers.size(); i++) {
            Customer m = customers.get(i);
            data[i][0] = m.getFullName();
            data[i][1] = m.getCustomerID();
            data[i][2] = m.getEmailAdresses();
            data[i][3] = m.getTelephoneNumbers();
            data[i][4] = m.getAdress();
            data[i][5] = m.getPostalCode();
            data[i][6] = m.getCountry();
     
            
        }
        
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel);
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
        jTable1 = new javax.swing.JTable();
        cbName = new javax.swing.JComboBox<>();
        cbMail = new javax.swing.JComboBox<>();
        lblCustomerInfo = new javax.swing.JLabel();
        lblSearchEmail = new javax.swing.JLabel();
        lblSearchName = new javax.swing.JLabel();
        btnEditCustomer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Förnamn", "Efternamn", "Email", "Telefonnummer", "Adress", "Postnummer", "Land"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        cbName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbMail.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblCustomerInfo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblCustomerInfo.setText("Kundinformation");

        lblSearchEmail.setText("Sök på email:");

        lblSearchName.setText("Sök på namn: ");

        btnEditCustomer.setText("Redigera kund");
        btnEditCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomerInfo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSearchName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblSearchEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditCustomer)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(lblCustomerInfo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSearchEmail)
                    .addComponent(lblSearchName)
                    .addComponent(btnEditCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditCustomerActionPerformed

    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
      //  try {
           // for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
              //  if ("Nimbus".equals(info.getName())) {
                 //   javax.swing.UIManager.setLookAndFeel(info.getClassName());
                 //   break;
                //}
           // }
        //} catch (ClassNotFoundException ex) {
       //     java.util.logging.Logger.getLogger(CustomerInformationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       // } catch (InstantiationException ex) {
          //  java.util.logging.Logger.getLogger(CustomerInformationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       // } catch (IllegalAccessException ex) {
            //java.util.logging.Logger.getLogger(CustomerInformationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       // } catch (javax.swing.UnsupportedLookAndFeelException ex) {
           // java.util.logging.Logger.getLogger(CustomerInformationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       // }
        //</editor-fold>

        /* Create and display the form */
       // java.awt.EventQueue.invokeLater(new Runnable() {
           // public void run() {
            //    new CustomerInformationWindow().setVisible(true);
           // }
       // });
   // }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditCustomer;
    private javax.swing.JComboBox<String> cbMail;
    private javax.swing.JComboBox<String> cbName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCustomerInfo;
    private javax.swing.JLabel lblSearchEmail;
    private javax.swing.JLabel lblSearchName;
    // End of variables declaration//GEN-END:variables
}
