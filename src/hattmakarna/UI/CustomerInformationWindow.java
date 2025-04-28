/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;
import hattmakarna.UI.EditCustomer;
import hattmakarna.UI.MainMenu;
import hattmakarna.UI.RegisterCustomerWindow;
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
        this.setTitle("Hantera kunder");

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
    ArrayList<Customer> customers = customerRegister.getAllCustomers();

    String[] columnNames = {"ID", "Namn", "E-mail", "Telefon", "Adress", "Postnummer", "Land"};
    Object[][] data = new Object[customers.size()][7];

    for (int i = 0; i < customers.size(); i++) {
        Customer m = customers.get(i);
        data[i][0] = m.getCustomerID();
        data[i][1] = m.getFullName();
        data[i][2] = String.join(", ", m.getEmailAdresses());
        data[i][3] = String.join(", ", m.getTelephoneNumbers());
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

    // Göm ID-kolumnen
    jTable1.getColumnModel().getColumn(0).setMinWidth(0);
    jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
    jTable1.getColumnModel().getColumn(0).setWidth(0);
    
    jTable1.setToolTipText(""); // Aktivera tooltips

jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        java.awt.Point p = e.getPoint();
        int rowIndex = jTable1.rowAtPoint(p);
        int colIndex = jTable1.columnAtPoint(p);

        if (rowIndex != -1 && (colIndex == 2 || colIndex == 3)) { // Email eller Telefon
            Object value = jTable1.getValueAt(rowIndex, colIndex);
            if (value != null && !value.toString().isEmpty()) {
                jTable1.setToolTipText("<html>" + value.toString().replace(", ", "<br>") + "</html>");
            } else {
                jTable1.setToolTipText(null);
            }
        } else {
            jTable1.setToolTipText(null);
        }
    }
});

    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    String customerID = jTable1.getValueAt(selectedRow, 0).toString();
                    Customer selectedCustomer = customerRegister.getCustomer(customerID);
                    if (selectedCustomer != null) {
                        EditCustomer editWindow = new EditCustomer(selectedCustomer);
                        editWindow.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Kunden kunde inte hittas.");
                    }
                }
            }
        }
    });
}

private void updateComboBoxes() {
    try {
        cbMail.removeActionListener(cbMail.getActionListeners()[0]);
        cbName.removeActionListener(cbName.getActionListeners()[0]);

        // Uppdatera email-listan
        ArrayList<String> mailList = idb.fetchColumn("SELECT mail FROM mail;");
        cbMail.removeAllItems();
        for (String mail : mailList) {
            cbMail.addItem(mail);
        }

        // Uppdatera namn-listan
        ArrayList<HashMap<String, String>> customerRows = idb.fetchRows("SELECT first_name, last_name FROM customer");
        cbName.removeAllItems();
        for (HashMap<String, String> row : customerRows) {
            String fName = row.get("first_name");
            String lName = row.get("last_name");
            cbName.addItem(fName + " " + lName);
        }

        cbMail.addActionListener(e -> mailSelected());
        cbName.addActionListener(e -> nameSelected());

    } catch (InfException e) {
        JOptionPane.showMessageDialog(this, "Kunde inte hämta uppdaterad information: " + e.getMessage());
    }
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
    String[] columnNames = {"ID", "Namn", "E-mail", "Telefon", "Adress", "Postnummer", "Land"};
    Object[][] data = new Object[customers.size()][7];

    for (int i = 0; i < customers.size(); i++) {
        Customer m = customers.get(i);
        data[i][0] = m.getCustomerID();
        data[i][1] = m.getFullName();
        data[i][2] = String.join(", ", m.getEmailAdresses());
        data[i][3] = String.join(", ", m.getTelephoneNumbers());
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

    // Dölj ID-kolumnen
    jTable1.getColumnModel().getColumn(0).setMinWidth(0);
    jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
    jTable1.getColumnModel().getColumn(0).setWidth(0);
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
        btnRefresh = new javax.swing.JButton();
        btnRegNewCustomer = new javax.swing.JButton();
        btnDeleteCustomer = new javax.swing.JButton();

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

        btnEditCustomer.setText("Redigera");
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

        btnRefresh.setText("Lista alla kunder");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnRegNewCustomer.setText("Ny kund");
        btnRegNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegNewCustomerActionPerformed(evt);
            }
        });

        btnDeleteCustomer.setText("Ta bort");
        btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomerInfo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSearchName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSearchEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRefresh))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRegNewCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCustomer))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblCustomerInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSearchEmail)
                    .addComponent(lblSearchName)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnEditCustomer)
                    .addComponent(btnRegNewCustomer)
                    .addComponent(btnDeleteCustomer))
                .addContainerGap())
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

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        fillTable();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnRegNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegNewCustomerActionPerformed
    new RegisterCustomerWindow(userLoggedIn).setVisible(true);
      // TODO add your handling code here:
    }//GEN-LAST:event_btnRegNewCustomerActionPerformed

    private void btnDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustomerActionPerformed
        int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Välj en kund i tabellen först.");
        return;
    }

    String customerID = jTable1.getValueAt(selectedRow, 0).toString();
    Customer toRemove = customerRegister.getCustomer(customerID);

    if (toRemove == null) {
        JOptionPane.showMessageDialog(this, "Kunden kunde inte hittas.");
        return;
    }

    Object[] options = {"Ja", "Nej"};

    int result = JOptionPane.showOptionDialog(this,
            "Är du säker på att du vill ta bort " + toRemove.getFullName() + "?",
            "Varning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            null, options, options[0]);

    if (result == 0) { // Om "Ja" klickas
        try {
            toRemove.delete(); // Ta bort från databasen

            customerRegister = new CustomerRegister(); // Ladda om kundlistan
            fillTable(); // Ladda om tabellen

            // Uppdatera comboboxarna
            updateComboBoxes();

            JOptionPane.showMessageDialog(this, "Kunden togs bort.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Något gick fel vid borttagning: " + e.getMessage());
        }
    } else {
        jTable1.clearSelection();
    }

    }//GEN-LAST:event_btnDeleteCustomerActionPerformed

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
    private javax.swing.JButton btnDeleteCustomer;
    private javax.swing.JButton btnEditCustomer;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRegNewCustomer;
    private javax.swing.JComboBox<String> cbMail;
    private javax.swing.JComboBox<String> cbName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCustomerInfo;
    private javax.swing.JLabel lblSearchEmail;
    private javax.swing.JLabel lblSearchName;
    // End of variables declaration//GEN-END:variables
}
