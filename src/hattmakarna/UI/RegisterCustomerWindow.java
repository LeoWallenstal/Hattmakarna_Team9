
package hattmakarna.UI;

import hattmakarna.UI.OrderWindow;
import hattmakarna.data.Customer;
import hattmakarna.data.CustomerRegister;
import hattmakarna.data.User;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.util.CountryList;
import hattmakarna.util.Util;
import java.util.ArrayList;
import oru.inf.InfException;
import static hattmakarna.util.Validerare.*;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.*;

/**
 *
 * @author Användaren
 */

public class RegisterCustomerWindow extends javax.swing.JFrame {
    
    private User userLoggedIn;
    private OrderWindow lastWindow;
    private DefaultListModel<String> dlPhoneModel;
    private DefaultListModel<String> dlEmailModel;
    private ArrayList<String> phoneModel;
    private ArrayList<String> emailModel;
    private Customer blankCustomer;
    private CustomerRegister customerRegister;
    private boolean saved = false;
    private boolean unsavedChanges = false;
    
    
    public RegisterCustomerWindow(User user, OrderWindow lastWindow) {
        initComponents();
        initErrorFlags();
        this.setTitle("Hattmakarna - Registrera kund");
        this.lastWindow = lastWindow;
        customerRegister = new CustomerRegister();
        
        userLoggedIn = user;
        dlPhoneModel = (DefaultListModel<String>)jlPhone.getModel();
        dlEmailModel = (DefaultListModel<String>)jlEmail.getModel();
        
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        blankCustomer = new Customer();
        
        initCBCountry();
        
        
        phoneModel = new ArrayList<String>();
        for(int i = 0; i < jlPhone.getModel().getSize(); i++){
            phoneModel.add(jlPhone.getModel().getElementAt(i));
        }
        
        emailModel = new ArrayList<String>();
        for(int i = 0; i < jlEmail.getModel().getSize(); i++){
            emailModel.add(jlEmail.getModel().getElementAt(i));
        }
        
        
        //Listeners
        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                checkSaveButtonState();
                checkUnsavedChanges();
            }
            public void removeUpdate(DocumentEvent e) { 
                checkSaveButtonState();
                checkUnsavedChanges();
            }
            public void changedUpdate(DocumentEvent e) { 
                checkSaveButtonState();
                checkUnsavedChanges();
            }
        };

        tfFirstName.getDocument().addDocumentListener(docListener);
        tfLastName.getDocument().addDocumentListener(docListener);
        tfAdress.getDocument().addDocumentListener(docListener);
        tfPostalCode.getDocument().addDocumentListener(docListener);
        cbCountry.addActionListener( e -> checkSaveButtonState());
        
        dlPhoneModel.addListDataListener(new ListDataListener() {
            public void contentsChanged(ListDataEvent e) { 
                checkSaveButtonState();
                checkUnsavedChanges();
            }
            public void intervalAdded(ListDataEvent e) { 
                checkSaveButtonState(); 
                checkUnsavedChanges();
            }
            public void intervalRemoved(ListDataEvent e) { 
                checkSaveButtonState(); 
                checkUnsavedChanges();
            }
        });
        
        dlEmailModel.addListDataListener(new ListDataListener() {
            public void contentsChanged(ListDataEvent e) { 
                checkSaveButtonState();
                checkUnsavedChanges();
            }
            public void intervalAdded(ListDataEvent e) { 
                checkSaveButtonState();
                checkUnsavedChanges();
            }
            public void intervalRemoved(ListDataEvent e) { 
                checkSaveButtonState();
                checkUnsavedChanges();
            }
        });
        
    }
    
    private void initErrorFlags(){
        lblFirstNameError.setVisible(false);
        lblLastNameError.setVisible(false);
        lblAdressError.setVisible(false);
        lblPostalCodeError.setVisible(false);
        lblCountryError.setVisible(false);
        lblPhoneError.setVisible(false);
        lblEmailError.setVisible(false);
        lblCustomerExistsError.setVisible(false);
        lblCustomerSaved.setVisible(false);
    }
    
    private void initCBCountry(){
        cbCountry.addItem("Välj land...");
        for(String aCountry : CountryList.getCountries()){
            cbCountry.addItem(aCountry);
        }
    }
    
    private void reset(){
        blankCustomer = new Customer();
        
        tfFirstName.setText("");
        tfLastName.setText("");
        tfAdress.setText("");
        tfPostalCode.setText("");
        cbCountry.setSelectedIndex(0);
        
        dlPhoneModel.clear();
        dlEmailModel.clear();
        phoneModel.clear();
        emailModel.clear();
    }
    
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblRegistreraKund = new javax.swing.JLabel();
        lbltfEmail = new javax.swing.JLabel();
        lbltfPhone = new javax.swing.JLabel();
        lblAdress = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        tfPhone = new javax.swing.JTextField();
        tfAdress = new javax.swing.JTextField();
        lblFirstname = new javax.swing.JLabel();
        tfFirstName = new javax.swing.JTextField();
        lblLastname = new javax.swing.JLabel();
        tfLastName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        lblPostnummer = new javax.swing.JLabel();
        tfPostalCode = new javax.swing.JTextField();
        lblCountry = new javax.swing.JLabel();
        cbCountry = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlPhone = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlEmail = new javax.swing.JList<>();
        btnAddPhone = new javax.swing.JButton();
        btnAddEmail = new javax.swing.JButton();
        lblPhoneError = new javax.swing.JLabel();
        lblEmailError = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblAdressError = new javax.swing.JLabel();
        lblPostalCodeError = new javax.swing.JLabel();
        lblFirstNameError = new javax.swing.JLabel();
        lblLastNameError = new javax.swing.JLabel();
        lblCountryError = new javax.swing.JLabel();
        lblCustomerExistsError = new javax.swing.JLabel();
        btnRemovePhone = new javax.swing.JButton();
        btnRemoveEmail = new javax.swing.JButton();
        btnGoBack = new javax.swing.JButton();
        lblCustomerSaved = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        lblRegistreraKund.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblRegistreraKund.setText("Registrera Kund");

        lbltfEmail.setText("Epost");

        lbltfPhone.setText("Telefonnummer");

        lblAdress.setText("Adress");

        tfEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfEmailKeyReleased(evt);
            }
        });

        tfPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfPhoneKeyReleased(evt);
            }
        });

        tfAdress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfAdressKeyPressed(evt);
            }
        });

        lblFirstname.setText("Förnamn");

        tfFirstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfFirstNameKeyPressed(evt);
            }
        });

        lblLastname.setText("Efternamn");

        tfLastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfLastNameKeyPressed(evt);
            }
        });

        btnSave.setText("Spara");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        lblPostnummer.setText("Postnummmer");

        tfPostalCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPostalCodeKeyPressed(evt);
            }
        });

        lblCountry.setText("Land");

        cbCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCountryActionPerformed(evt);
            }
        });

        jlPhone.setModel(new DefaultListModel<>());
        jlPhone.setFixedCellWidth(200);
        jlPhone.setMaximumSize(new java.awt.Dimension(258, 130));
        jlPhone.setMinimumSize(new java.awt.Dimension(258, 130));
        jlPhone.setPreferredSize(new java.awt.Dimension(258, 130));
        jlPhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlPhoneMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jlPhone);

        jlEmail.setModel(new DefaultListModel<>());
        jlEmail.setFixedCellWidth(200);
        jlEmail.setMaximumSize(new java.awt.Dimension(258, 130));
        jlEmail.setMinimumSize(new java.awt.Dimension(258, 130));
        jlEmail.setPreferredSize(new java.awt.Dimension(258, 130));
        jlEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlEmailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jlEmail);

        btnAddPhone.setText("Lägg till");
        btnAddPhone.setEnabled(false);
        btnAddPhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddPhoneMouseClicked(evt);
            }
        });

        btnAddEmail.setText("Lägg till");
        btnAddEmail.setEnabled(false);
        btnAddEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddEmailMouseClicked(evt);
            }
        });

        lblPhoneError.setText("Ogiltigt telefonnummer!");

        lblEmailError.setText("Ogiltig epostadress!");

        lblPhone.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPhone.setText("Telefonnummer");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblEmail.setText("E-postadresser");

        lblAdressError.setText("Ogiltig adress!");

        lblPostalCodeError.setText("Ogiltigt postnummer!");

        lblFirstNameError.setText("Ogiltigt namn!");

        lblLastNameError.setText("Ogiltigt namn!");

        lblCountryError.setText("Land ej valt!");

        lblCustomerExistsError.setText("Den här kunden finns redan!");

        btnRemovePhone.setText("Ta bort");
        btnRemovePhone.setEnabled(false);
        btnRemovePhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRemovePhoneMouseClicked(evt);
            }
        });

        btnRemoveEmail.setText("Ta bort");
        btnRemoveEmail.setEnabled(false);
        btnRemoveEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRemoveEmailMouseClicked(evt);
            }
        });

        btnGoBack.setText("Tillbaka");
        btnGoBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGoBackMouseClicked(evt);
            }
        });

        lblCustomerSaved.setText("Kund sparad!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfPhone, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbltfPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPhoneError, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbltfEmail)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblEmailError, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPhone)
                                    .addComponent(btnRemovePhone))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblEmail)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnSave))
                                            .addComponent(btnRemoveEmail))
                                        .addGap(0, 17, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblFirstname)
                                            .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblFirstNameError))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblLastname)
                                            .addComponent(lblLastNameError)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblRegistreraKund, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblCustomerExistsError))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblAdress)
                                            .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblAdressError))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblPostalCodeError)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblCountryError))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tfPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblPostnummer))
                                                .addGap(43, 43, 43)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblCountry)
                                                    .addComponent(cbCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGoBack)
                .addGap(18, 18, 18)
                .addComponent(lblCustomerSaved, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegistreraKund, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustomerExistsError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstname)
                    .addComponent(lblLastname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstNameError)
                    .addComponent(lblLastNameError))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdress)
                    .addComponent(lblPostnummer)
                    .addComponent(lblCountry))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lbltfEmail))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAdressError)
                            .addComponent(lblPostalCodeError)
                            .addComponent(lblCountryError))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbltfPhone)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddPhone)
                    .addComponent(btnAddEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmailError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPhoneError))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPhone)
                            .addComponent(lblEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnSave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemovePhone)
                    .addComponent(btnRemoveEmail))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGoBack)
                    .addComponent(lblCustomerSaved))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean creationOK = true;
        
        if(!validateName(tfFirstName.getText())){
            creationOK = false;
            lblFirstNameError.setVisible(true);
        }
        else{
            blankCustomer.setFirstName(tfFirstName.getText());
        }
        
        if(!validateName(tfLastName.getText())){
            creationOK = false;
            lblLastNameError.setVisible(true);
        }
        else{
            blankCustomer.setLastName(tfLastName.getText());
        }
        
        if(!validateAdress(tfAdress.getText())){
            creationOK = false;
            lblAdressError.setVisible(true);
        }
        else{
            blankCustomer.setAdress(tfAdress.getText());
        }
        
        if(!validatePostalCode(tfPostalCode.getText())){
            creationOK = false;
            lblPostalCodeError.setVisible(true);
        }
        else{
            blankCustomer.setPostalCode(tfPostalCode.getText());
        }
        
        if(cbCountry.getSelectedItem().toString().equals("Välj land...")){
            creationOK = false;
            lblCountryError.setVisible(true);
        }
        else{
            blankCustomer.setCountry(cbCountry.getSelectedItem().toString());
        }
        
        if(customerRegister.customerExists(blankCustomer)){
            creationOK = false;
            lblCustomerExistsError.setVisible(true);
        }
        
        if(creationOK){
            customerRegister.add(blankCustomer);
            blankCustomer.insert();
            saved = true;
            lblCustomerSaved.setVisible(true);
            reset();
            
            /*Dehär raderna var här sen innan jag(James) pillade om lite här, så
            jag låter dem stå!*/
            //lastWindow.refreshCustomers();
            //lastWindow.setVisible(true);
        }
        
    
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tfEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfEmailKeyReleased
        if(!tfEmail.getText().isEmpty()){
            btnAddEmail.setEnabled(true);
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                btnAddEmail.doClick();
            }
        }
        else{
            btnAddEmail.setEnabled(false);
        }
        lblEmailError.setVisible(false);
        lblCustomerExistsError.setVisible(false);
        lblCustomerSaved.setVisible(false);
    }//GEN-LAST:event_tfEmailKeyReleased

    private void tfPhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPhoneKeyReleased

        if(!tfPhone.getText().isEmpty()){
            btnAddPhone.setEnabled(true);
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                btnAddPhone.doClick();
            }
        }
        else{
            btnAddPhone.setEnabled(false);
        }
        lblPhoneError.setVisible(false);
        lblCustomerExistsError.setVisible(false);
        lblCustomerSaved.setVisible(false);
    }//GEN-LAST:event_tfPhoneKeyReleased

    private void btnAddPhoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddPhoneMouseClicked
        if(validatePhoneNumber(tfPhone.getText()) 
            && !numberAlreadyAdded(tfPhone.getText()))
        {
            dlPhoneModel.addElement(tfPhone.getText());
            blankCustomer.addTelephoneNumber(tfPhone.getText());
            phoneModel.add(tfPhone.getText());
            tfPhone.setText("");
            btnAddPhone.setEnabled(false);
        }
        else{
            if(!validatePhoneNumber(tfPhone.getText())){
                lblPhoneError.setText("Ogiltigt telefonnummer!");
            }
            else if(numberAlreadyAdded(tfPhone.getText())){
                lblPhoneError.setText("Telefonnummer finns redan!");
            }
            lblPhoneError.setVisible(true);
        }
    }//GEN-LAST:event_btnAddPhoneMouseClicked

    private void btnAddEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddEmailMouseClicked
        if(validateEmail(tfEmail.getText()) 
            && !emailAlreadyAdded(tfEmail.getText()))
        {
            dlEmailModel.addElement(tfEmail.getText());
            blankCustomer.addEmailAdress(tfEmail.getText());
            emailModel.add(tfEmail.getText());
            tfEmail.setText("");
            btnAddEmail.setEnabled(false);
        }
        else{
            if(!validateEmail(tfEmail.getText())){
                lblEmailError.setText("Ogiltigt epostadress!");
            }
            else if(emailAlreadyAdded(tfEmail.getText())){
                lblEmailError.setText("Epostadress finns redan!");
            }
            lblEmailError.setVisible(true);
        }
    }//GEN-LAST:event_btnAddEmailMouseClicked

    private void tfFirstNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFirstNameKeyPressed
        lblFirstNameError.setVisible(false);
        lblCustomerExistsError.setVisible(false);
        lblCustomerSaved.setVisible(false);
    }//GEN-LAST:event_tfFirstNameKeyPressed

    private void tfLastNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfLastNameKeyPressed
        lblLastNameError.setVisible(false);
        lblCustomerExistsError.setVisible(false);
        lblCustomerSaved.setVisible(false);
    }//GEN-LAST:event_tfLastNameKeyPressed

    private void tfAdressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfAdressKeyPressed
        lblAdressError.setVisible(false);
        lblCustomerExistsError.setVisible(false);
        lblCustomerSaved.setVisible(false);
    }//GEN-LAST:event_tfAdressKeyPressed

    private void tfPostalCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPostalCodeKeyPressed
        lblPostalCodeError.setVisible(false);
        lblCustomerExistsError.setVisible(false);
        lblCustomerSaved.setVisible(false);
    }//GEN-LAST:event_tfPostalCodeKeyPressed

    private void cbCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCountryActionPerformed
        lblCountryError.setVisible(false);
        lblCustomerExistsError.setVisible(false);
        lblCustomerSaved.setVisible(false);
    }//GEN-LAST:event_cbCountryActionPerformed

    private void jlPhoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPhoneMouseClicked
        jlEmail.clearSelection();
        btnRemoveEmail.setEnabled(false);
        if(dlPhoneModel.getSize() > 0){
            btnRemovePhone.setEnabled(true);
        }
    }//GEN-LAST:event_jlPhoneMouseClicked

    private void jlEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlEmailMouseClicked
        jlPhone.clearSelection();
        btnRemovePhone.setEnabled(false);
        if(dlEmailModel.getSize() > 0){
            btnRemoveEmail.setEnabled(true);
        }
    }//GEN-LAST:event_jlEmailMouseClicked

    private void btnRemovePhoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemovePhoneMouseClicked
        dlPhoneModel.removeElementAt(jlPhone.getSelectedIndex());
        blankCustomer.removeTelephoneNumber(jlPhone.getSelectedIndex());
        phoneModel.remove(jlPhone.getSelectedIndex());
        jlPhone.clearSelection();
        btnRemovePhone.setEnabled(false);
    }//GEN-LAST:event_btnRemovePhoneMouseClicked

    private void btnRemoveEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoveEmailMouseClicked
        dlEmailModel.removeElementAt(jlEmail.getSelectedIndex());
        blankCustomer.removeEmailAdress(jlEmail.getSelectedIndex());
        emailModel.remove(jlEmail.getSelectedIndex());
        jlEmail.clearSelection();
        btnRemoveEmail.setEnabled(false);
    }//GEN-LAST:event_btnRemoveEmailMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        jlPhone.clearSelection();
        jlEmail.clearSelection();
        btnRemovePhone.setEnabled(false);
        btnRemoveEmail.setEnabled(false);
    }//GEN-LAST:event_formMouseClicked

    private void btnGoBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGoBackMouseClicked
        /*NÅNTING HÄR KOMMA TILLBAKA TILL FÖRRA SKÄRMEN,
          KANSKE OCKSÅ NÅNTING OM OSPARADE ÄNDRINGAR!*/
        if(saved && !unsavedChanges){
            //DEBUG
            System.out.println("Allt är ok, och sparat.");
            //Gå tillbaka till något annat fönster
        }
        else if(!saved && !unsavedChanges){
            //DEBUG
            System.out.println("Inte sparat, och det finns osparade ändringar");
            Object[] options = {"Ja", "Nej"};

            int result = JOptionPane.showOptionDialog(this, "Det finns osparade ändringar, vill du återgå?", 
                "Varning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                null, options, options[0]);

            if (result == 0) {
                // "Ja" klickades, stänger bara lilla dialogen
                
            } 
            else if (result == 1) {
                // "Nej" klickades
                this.dispose(); // Stänger RegisterCustomerWindow.java
            }
        }
    }//GEN-LAST:event_btnGoBackMouseClicked

    private void checkSaveButtonState(){
        boolean tfsNotEmpty = !tfFirstName.getText().isEmpty() && 
            !tfLastName.getText().isEmpty() && !tfAdress.getText().isEmpty() &&
            !tfPostalCode.getText().isEmpty();
        
        boolean countrySelected = !cbCountry.getSelectedItem()
            .toString().equals("Välj land...");
        
        boolean phoneListNotEmpty = jlPhone.getModel().getSize() > 0;
        boolean emailListNotEmpty = jlEmail.getModel().getSize() > 0;
        
        btnSave.setEnabled(tfsNotEmpty && countrySelected && phoneListNotEmpty
            && emailListNotEmpty);
    }
    
    private void checkUnsavedChanges() {
        boolean textFieldsChanged =
            !tfFirstName.getText().equals(blankCustomer.getFirstName()) ||
            !tfLastName.getText().equals(blankCustomer.getLastName()) ||
            !tfAdress.getText().equals(blankCustomer.getAdress()) ||
            !tfPostalCode.getText().equals(blankCustomer.getPostalCode());

        boolean countryChanged = 
            !cbCountry.getSelectedItem().toString().equals(blankCustomer.getCountry());

        boolean phonesChanged =
            !Util.contentEquals(blankCustomer.getTelephoneNumbers(), phoneModel);

        boolean emailsChanged =
            !Util.contentEquals(blankCustomer.getEmailAdresses(), emailModel);

        boolean hasChanges = textFieldsChanged || countryChanged || phonesChanged || emailsChanged;

        unsavedChanges = !hasChanges;
        saved = !hasChanges;
    }
    
    
    
    private boolean numberAlreadyAdded(String telephoneNumber){
        for(int i = 0; i < dlPhoneModel.getSize(); i++){
            if(dlPhoneModel.get(i).equals(telephoneNumber)){
                return true;
            }
        }
        return false;
    }
    
    private boolean emailAlreadyAdded(String email){
        for(int i = 0; i < dlEmailModel.getSize(); i++){
            if(dlEmailModel.get(i).equals(email)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEmail;
    private javax.swing.JButton btnAddPhone;
    private javax.swing.JButton btnGoBack;
    private javax.swing.JButton btnRemoveEmail;
    private javax.swing.JButton btnRemovePhone;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbCountry;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> jlEmail;
    private javax.swing.JList<String> jlPhone;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblAdressError;
    private javax.swing.JLabel lblCountry;
    private javax.swing.JLabel lblCountryError;
    private javax.swing.JLabel lblCustomerExistsError;
    private javax.swing.JLabel lblCustomerSaved;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmailError;
    private javax.swing.JLabel lblFirstNameError;
    private javax.swing.JLabel lblFirstname;
    private javax.swing.JLabel lblLastNameError;
    private javax.swing.JLabel lblLastname;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPhoneError;
    private javax.swing.JLabel lblPostalCodeError;
    private javax.swing.JLabel lblPostnummer;
    private javax.swing.JLabel lblRegistreraKund;
    private javax.swing.JLabel lbltfEmail;
    private javax.swing.JLabel lbltfPhone;
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfFirstName;
    private javax.swing.JTextField tfLastName;
    private javax.swing.JTextField tfPhone;
    private javax.swing.JTextField tfPostalCode;
    // End of variables declaration//GEN-END:variables
}
