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
import hattmakarna.data.User;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author linahanssons
 */
public class CustomerInformationWindow extends javax.swing.JFrame {
    private CustomerRegister customerRegister;

    private  Customer customer;
    private User userLoggedIn;

    /**
     * Creates new form CustomerInformationWindow
     */
public CustomerInformationWindow(User userLoggedIn) {
    this.userLoggedIn = userLoggedIn;
    this.customerRegister = new CustomerRegister();

    initComponents();
    fillTable();

    try {
        ArrayList<String> mailList = idb.fetchColumn("SELECT mail FROM mail;");
        cbMail.removeAllItems();
        for (String mail : mailList) {
            cbMail.addItem(mail);
        }
    } catch (InfException e) {
        JOptionPane.showMessageDialog(this, "Kunde inte hämta mail: " + e.getMessage());
    }

    try {
        ArrayList<HashMap<String, String>> customerRows = idb.fetchRows("SELECT first_name, last_name FROM customer");
        cbName.removeAllItems();
        for (HashMap<String, String> row : customerRows) {
            String fName = row.get("first_name");
            String lName = row.get("last_name");
            cbName.addItem(fName + " " + lName);
        }
    } catch (InfException e) {
        JOptionPane.showMessageDialog(this, "Kunde inte hämta namn: " + e.getMessage());
    }

    cbMail.addActionListener(e -> mailSelected());
    cbName.addActionListener(e -> nameSelected());
}
    
    private void fillTable() {
        //DEBUG
        System.out.println("fillTableKörs");
        ArrayList <Customer> customers = customerRegister.getAllCustomers();
        

        

        String[] columnNames = {"name", "customerID","email", "phone", "adress", "postalCode", "country" };
    
        Object[][] data = new Object[customers.size()][7];
        for (int i = 0; i < customers.size(); i++) {
            Customer m = customers.get(i);
            data[i][0] = m.getCustomerID();
            data[i][1] = m.getFullName();
            data[i][2] = m.getEmailAdresses();
            data[i][3] = m.getTelephoneNumbers();
            data[i][4] = m.getAdress();
            data[i][5] = m.getPostalCode();
            data[i][6] = m.getCountry();
     
            
        }
        
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel);
        
         jTable1.getColumnModel().getColumn(0).setMinWidth(0);
         jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
         jTable1.getColumnModel().getColumn(0).setWidth(0);
    
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) { //dubbelklick
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
        String email = (String) jTable1.getValueAt(selectedRow, 1);
        openEditCustomerWindow(email);
    }
      }
         }
            });
    
    }
    
private void openEditCustomerWindow(String email) {
    Customer selectedCustomer = customerRegister.getCustomerByEmail(email);
    if (selectedCustomer != null) {
        EditCustomer editWindow = new EditCustomer(selectedCustomer);
        editWindow.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Kunden kunde inte hittas.");
    }
}
    private void updateTable(ArrayList<Customer> customers) {
    String[] columnNames = {"Namn", "E-mail", "Nummer", "Adress", "Postnummer", "Land"};
    Object[][] data = new Object[customers.size()][6];
    
    for (int i = 0; i < customers.size(); i++) {
        Customer m = customers.get(i);
        data[i][0] = m.getFullName();
        data[i][1] = m.getEmailAdresses();
        data[i][2] = m.getTelephoneNumbers();
        data[i][3] = m.getAdress();
        data[i][4] = m.getPostalCode();
        data[i][5] = m.getCountry();
    }

    javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(data, columnNames) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    jTable1.setModel(tableModel);
}
    
    private void mailSelected() {
    String selectedMail = (String) cbMail.getSelectedItem();
    if (selectedMail != null && !selectedMail.isEmpty()) {
        Customer c = findCustomerByEmail(selectedMail);
        if (c != null) {
            ArrayList<Customer> singleCustomer = new ArrayList<>();
            singleCustomer.add(c);
            updateTable(singleCustomer);
        }
    }
}

private void nameSelected() {
    String selectedName = (String) cbName.getSelectedItem();
    if (selectedName != null && !selectedName.isEmpty()) {
        String[] nameParts = selectedName.split(" ");
        if (nameParts.length == 2) {
            String firstName = nameParts[0];
            String lastName = nameParts[1];
             Customer c = findCustomerByFullName(firstName + " " + lastName);
            ArrayList<Customer> customers = new ArrayList<>();
            if (c != null){
    customers.add(c);
}
            updateTable(customers);
        }
    }
}
private Customer findCustomerByEmail(String email) {
    for (Customer c : customerRegister.getAllCustomers()) {
        for (String mail : c.getEmailAdresses()) {
            if (mail.equalsIgnoreCase(email)) {
                return c;
            }
        }
    }
    return null;
}

private Customer findCustomerByFullName(String fullName) {
    for (Customer c : customerRegister.getAllCustomers()) {
        String combinedName = c.getFirstName() + " " + c.getLastName();
        if (combinedName.equalsIgnoreCase(fullName)) {
            return c;
        }
    }
    return null;
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
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        btnBack.setText("Tillbaka");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
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
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(btnEditCustomer)
                    .addComponent(btnBack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCustomerActionPerformed
                                               
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Välj en kund i tabellen först.");
        return;
    }

    String customerID = jTable1.getValueAt(selectedRow, 0).toString(); // Hämta från kolumn 0 (ID)

    Customer selectedCustomer = customerRegister.getCustomer(customerID);
    if (selectedCustomer != null) {
        EditCustomer editWindow = new EditCustomer(selectedCustomer);
        editWindow.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Kunden kunde inte hittas.");
    }


    }//GEN-LAST:event_btnEditCustomerActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
       new MainMenu(userLoggedIn).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

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
    private javax.swing.JButton btnBack;
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
