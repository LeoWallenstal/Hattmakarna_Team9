/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import hattmakarna.data.Customer;
import hattmakarna.data.User;
import static hattmakarna.util.Validerare.*;
import hattmakarna.util.Util;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import hattmakarna.util.CountryList;
import javax.swing.ScrollPaneConstants;
import hattmakarna.data.EmailOrPhone;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author annae
 */
public class EditCustomer extends javax.swing.JFrame {

    /**
     * Creates new form EditCustomer
     */
    
    private final Customer copy;
    private Customer aCustomer;
    private User userLoggedIn;
    private DefaultListModel<String> dlPhoneModel;
    private DefaultListModel<String> dlEmailModel;
    private ArrayList<String> phoneModel;
    private ArrayList<String> emailModel;
    private boolean editOK = true;

    
    
    public EditCustomer(Customer aCustomer) {
        this.aCustomer = aCustomer;
        copy = new Customer(aCustomer);
 
        
        initComponents();
        this.setTitle("Hattmakarna - Redigera kund");
        btnDeletePhone.setEnabled(false);
        btnDeleteEmail.setEnabled(false);
        setLocationRelativeTo(null);
        initCBCountry();
        setCustomerInfoText();
        jScrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        lblCustomer.setText("Redigera " + aCustomer.getFullName() + "s uppgifter");
        lblFirstNameError.setVisible(false);
        lblLastNameError.setVisible(false);
        lblAdressError.setVisible(false);
        lblPostalCodeError.setVisible(false);
        lblCountryError.setVisible(false);
        btnSave.setEnabled(false);
        
        dlPhoneModel = (DefaultListModel<String>)jlPhone.getModel();
        dlEmailModel = (DefaultListModel<String>)jlEmail.getModel();
        
        //Setting up listeners
        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e){  checkChanges(); }
            public void removeUpdate(DocumentEvent e){  checkChanges(); }
            public void changedUpdate(DocumentEvent e){ checkChanges(); }
        };
        
        dlPhoneModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                checkChanges();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                checkChanges();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                checkChanges();
            }
        });
        
        dlEmailModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                checkChanges();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                checkChanges();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                checkChanges();
            }
        });
        
    
        tfFirstName.getDocument().addDocumentListener(docListener);
        tfLastName.getDocument().addDocumentListener(docListener);
        tfAdress.getDocument().addDocumentListener(docListener);
        tfPostalCode.getDocument().addDocumentListener(docListener);
        cbCountry.addActionListener(e -> checkChanges());
        
        phoneModel = new ArrayList<String>();
        for(int i = 0; i < jlPhone.getModel().getSize(); i++){
            phoneModel.add(jlPhone.getModel().getElementAt(i));
        }
        
        emailModel = new ArrayList<String>();
        for(int i = 0; i < jlEmail.getModel().getSize(); i++){
            emailModel.add(jlEmail.getModel().getElementAt(i));
        }
            
    }
    
    private void checkChanges() {
        boolean fieldsChanged = !tfFirstName.getText().equals(aCustomer.getFirstName()) ||
            !tfLastName.getText().equals(aCustomer.getLastName()) ||
            !tfAdress.getText().equals(aCustomer.getAdress()) ||
            !tfPostalCode.getText().equals(aCustomer.getPostalCode()) ||
            !cbCountry.getSelectedItem().toString().equals(aCustomer.getCountry());
        
      
        
        boolean phoneListChanged = !Util.contentEquals(aCustomer.getTelephoneNumbers(), phoneModel);
        boolean emailListChanged = !Util.contentEquals(aCustomer.getEmailAdresses(), emailModel);
        
        boolean anythingChanged = fieldsChanged || phoneListChanged || emailListChanged;
        
        btnSave.setEnabled(anythingChanged);
    }
    
    private void initCBCountry(){
        cbCountry.addItem("Välj land...");
        for(String aCountry : CountryList.getCountries()){
            cbCountry.addItem(aCountry);
        }
    }
    
    public DefaultListModel<String> getModel(EmailOrPhone which){
        return switch(which){
            case PHONE -> dlPhoneModel;
            case EMAIL -> dlEmailModel;
        };
    }
    
    public JButton getSaveButton(){
        return btnSave;
    }
    
    
    private void setCustomerInfoText(){
        int countryIndex = getCountryIndex();
        tfFirstName.setText(aCustomer.getFirstName());
        tfLastName.setText(aCustomer.getLastName());
        setPhoneValues();
        setEmailValues();
        tfAdress.setText(aCustomer.getAdress());
        tfPostalCode.setText(aCustomer.getPostalCode());
        if(countryIndex < 0){
            cbCountry.setSelectedIndex(0);
        }
        else{
            cbCountry.setSelectedIndex(countryIndex + 1);
        }
    }
    
    private int getCountryIndex(){
        for(int i = 0; i < CountryList.getCountries().size(); i++){
            if(CountryList.getCountries().get(i).equals(aCustomer.getCountry())){
                return i;
            }
        }
        System.out.println("getCountryIndex(), Landet hittades inte");
        return -1;
    }
    
    private void setPhoneValues(){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        for(String phoneNumbers : aCustomer.getTelephoneNumbers()){
            listModel.addElement(phoneNumbers);
        }
        
        jlPhone.setModel(listModel);
    }
    
    private void setEmailValues(){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        for(String eMail : aCustomer.getEmailAdresses()){
            listModel.addElement(eMail);
        }
        
        jlEmail.setModel(listModel);
    }
    
    private boolean hasUnsavedChanges(){
        if(!aCustomer.getFirstName().equals(tfFirstName.getText())){
            return true;
        }
        
        if(!aCustomer.getLastName().equals(tfLastName.getText())){
            return true;
        }
        
        if(!aCustomer.getAdress().equals(tfAdress.getText())){
            return true;
        }
        
        if(!aCustomer.getPostalCode().equals(tfPostalCode.getText())){
            return true;
        }
        
        String country = (String) cbCountry.getSelectedItem();
        if(!aCustomer.getCountry().equals(country)){
            return true;
        }
        
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCustomer = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblAdress = new javax.swing.JLabel();
        tfFirstName = new javax.swing.JTextField();
        tfLastName = new javax.swing.JTextField();
        tfAdress = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlEmail = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlPhone = new javax.swing.JList<>();
        btnAddPhone = new javax.swing.JButton();
        btnDeletePhone = new javax.swing.JButton();
        btnAddEmail = new javax.swing.JButton();
        btnDeleteEmail = new javax.swing.JButton();
        lblPostalCode = new javax.swing.JLabel();
        lblCountry = new javax.swing.JLabel();
        tfPostalCode = new javax.swing.JTextField();
        cbCountry = new javax.swing.JComboBox<>();
        lblFirstNameError = new javax.swing.JLabel();
        lblLastNameError = new javax.swing.JLabel();
        lblAdressError = new javax.swing.JLabel();
        lblPostalCodeError = new javax.swing.JLabel();
        lblCountryError = new javax.swing.JLabel();
        lblSaved = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        lblCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCustomer.setText("Redigera kund");

        lblFirstName.setText("Förnamn:");

        lblLastName.setText("Efternamn:");

        lblEmail.setText("E-postadress:");

        lblPhone.setText("Telefonnummer:");

        lblAdress.setText("Adress:");

        tfFirstName.setText("jTextField1");
        tfFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFirstNameActionPerformed(evt);
            }
        });
        tfFirstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfFirstNameKeyTyped(evt);
            }
        });

        tfLastName.setText("jTextField2");
        tfLastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfLastNameKeyTyped(evt);
            }
        });

        tfAdress.setText("jTextField5");
        tfAdress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfAdressKeyTyped(evt);
            }
        });

        btnBack.setText("Tillbaka");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnSave.setText("Spara");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jlEmail.setPreferredSize(new java.awt.Dimension(200, 100));
        jlEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlEmailMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jlEmail);

        jlPhone.setPreferredSize(new java.awt.Dimension(200, 100));
        jlPhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlPhoneMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jlPhone);

        btnAddPhone.setText("Lägg till");
        btnAddPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPhoneActionPerformed(evt);
            }
        });

        btnDeletePhone.setText("Ta bort");
        btnDeletePhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePhoneActionPerformed(evt);
            }
        });

        btnAddEmail.setText("Lägg till");
        btnAddEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmailActionPerformed(evt);
            }
        });

        btnDeleteEmail.setText("Ta bort");
        btnDeleteEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteEmailActionPerformed(evt);
            }
        });

        lblPostalCode.setText("Postnummer:");

        lblCountry.setText("Land:");

        tfPostalCode.setText("jTextField1");
        tfPostalCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfPostalCodeKeyTyped(evt);
            }
        });

        cbCountry.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCountryItemStateChanged(evt);
            }
        });
        cbCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCountryActionPerformed(evt);
            }
        });

        lblFirstNameError.setText("jLabel1");

        lblLastNameError.setText("jLabel2");

        lblAdressError.setText("jLabel3");

        lblPostalCodeError.setText("jLabel4");

        lblCountryError.setText("jLabel1");

        lblSaved.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSaved, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblLastName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                            .addComponent(lblFirstName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(12, 12, 12))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAdress)
                                            .addComponent(lblCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPostalCode))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfPostalCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(tfAdress, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfLastName, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfFirstName, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbCountry, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblPostalCodeError, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                    .addComponent(lblCountryError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAdressError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblLastNameError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblFirstNameError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDeleteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblEmail))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDeletePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(185, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFirstNameError))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastName)
                    .addComponent(tfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLastNameError))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdress)
                    .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdressError))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPostalCode)
                    .addComponent(tfPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPostalCodeError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCountry)
                    .addComponent(cbCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCountryError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBack)
                            .addComponent(btnSave)
                            .addComponent(lblSaved))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletePhone)
                        .addGap(68, 68, 68)
                        .addComponent(btnAddEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteEmail)
                        .addGap(56, 56, 56))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        
        if(!validateName(tfFirstName.getText()) || tfFirstName.getText().isEmpty()){
            editOK = false;
            if(!validateName(tfFirstName.getText())){
                lblFirstNameError.setText("Får bara innehålla bokstäver och bindestreck!");
            }
            else if(tfFirstName.getText().isEmpty()){
                lblFirstNameError.setText("Får inte vara tomt!");
            }
            lblFirstNameError.setVisible(true);
        }
        else{
            aCustomer.setFirstName(tfFirstName.getText());
        }
        
        if(!validateName(tfLastName.getText()) || tfLastName.getText().isEmpty()){
            editOK = false;
            if(!validateName(tfLastName.getText())){
                lblLastNameError.setText("Får bara innehålla bokstäver och bindestreck!");
            }
            else if(tfLastName.getText().isEmpty()){
                lblLastNameError.setText("Får inte vara tomt!");
            }
            lblLastNameError.setVisible(true);
        }
        else{
            aCustomer.setLastName(tfLastName.getText());
        }
        
        if(!validateAdress(tfAdress.getText()) || tfAdress.getText().isEmpty()){
            editOK = false;
            if(!validateAdress(tfAdress.getText())){
                lblAdressError.setText("Får bara innehålla bokstäver, siffror och bindestreck!");
            }
            else if(tfAdress.getText().isEmpty()){
                lblAdressError.setText("Får inte vara tomt!");
            }
            lblAdressError.setVisible(true);
        }
        else{
            aCustomer.setAdress(tfAdress.getText());
        }
        
        if(!validatePostalCode(tfPostalCode.getText()) || tfPostalCode.getText().isEmpty()){
            editOK = false;
            if(!validatePostalCode(tfPostalCode.getText())){
                lblPostalCodeError.setText("Får bara innehålla siffror! (xxxxx eller xxx xx)");
            }
            else if(tfPostalCode.getText().isEmpty()){
                lblPostalCodeError.setText("Får inte vara tomt!");
            }
            lblPostalCodeError.setVisible(true);
        }
        else{
            aCustomer.setPostalCode(tfPostalCode.getText());
        }
        
        if(cbCountry.getSelectedItem().equals("Välj land...")){
            editOK = false;
            lblCountryError.setText("Inget land valt!");
            lblCountryError.setVisible(true);
        }
        else{
            aCustomer.setCountry((String)cbCountry.getSelectedItem());
        }
        
        if(editOK){            
            aCustomer.save(copy);
            lblSaved.setText("Kunduppgifter uppdaterade!");
            lblSaved.setVisible(true);
        }
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tfFirstNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFirstNameKeyTyped
        btnSave.setEnabled(true);
        editOK = true;
        lblFirstNameError.setVisible(false);
        lblSaved.setVisible(false);
    }//GEN-LAST:event_tfFirstNameKeyTyped

    private void tfLastNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfLastNameKeyTyped
        btnSave.setEnabled(true);
        editOK = true;
        lblLastNameError.setVisible(false);
        lblSaved.setVisible(false);
    }//GEN-LAST:event_tfLastNameKeyTyped

    private void tfAdressKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfAdressKeyTyped
        btnSave.setEnabled(true);
        editOK = true;
        lblAdressError.setVisible(false);
        lblSaved.setVisible(false);
    }//GEN-LAST:event_tfAdressKeyTyped

    private void btnDeletePhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePhoneActionPerformed
        aCustomer.removeTelephoneNumber(jlPhone.getSelectedIndex());
        dlPhoneModel.remove(jlPhone.getSelectedIndex());
        dlPhoneModel.removeElementAt(jlPhone.getSelectedIndex());
        btnSave.setEnabled(true);
        btnDeletePhone.setEnabled(false);
        if(dlPhoneModel.getSize() == 0){
            btnDeletePhone.setEnabled(false);
        }
        lblSaved.setVisible(false);
    }//GEN-LAST:event_btnDeletePhoneActionPerformed

    private void btnAddPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPhoneActionPerformed
        lblSaved.setVisible(false);
        new AddEmailPhoneWindow(aCustomer, EmailOrPhone.PHONE, this).setVisible(true);
    }//GEN-LAST:event_btnAddPhoneActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if(hasUnsavedChanges()){
            new UnsavedChangesWindow(aCustomer, userLoggedIn).setVisible(true);
        }
        else{
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void tfPostalCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPostalCodeKeyTyped
        btnSave.setEnabled(true);
        editOK = true;
        lblPostalCode.setVisible(false);
        lblSaved.setVisible(false);
    }//GEN-LAST:event_tfPostalCodeKeyTyped

    private void cbCountryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCountryItemStateChanged
        btnSave.setEnabled(true);
        editOK = true;
        lblCountryError.setVisible(false);
        lblSaved.setVisible(false);
    }//GEN-LAST:event_cbCountryItemStateChanged

    private void jlPhoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPhoneMouseClicked
        btnDeletePhone.setEnabled(true);
        btnDeleteEmail.setEnabled(false);
        jlEmail.clearSelection();
    }//GEN-LAST:event_jlPhoneMouseClicked

    private void jlEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlEmailMouseClicked
        btnDeleteEmail.setEnabled(true);
        btnDeletePhone.setEnabled(false);
        jlPhone.clearSelection();
    }//GEN-LAST:event_jlEmailMouseClicked

    private void btnDeleteEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteEmailActionPerformed
        aCustomer.removeEmailAdress(jlEmail.getSelectedIndex());
        dlEmailModel.removeElement(jlEmail.getSelectedIndex());
        dlEmailModel.remove(jlEmail.getSelectedIndex());
        btnSave.setEnabled(true);
        btnDeleteEmail.setEnabled(false);
        if(dlEmailModel.getSize() == 0){
            btnDeleteEmail.setEnabled(false);
        }
        lblSaved.setVisible(false);
    }//GEN-LAST:event_btnDeleteEmailActionPerformed

    private void btnAddEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmailActionPerformed
        lblSaved.setVisible(false);
        new AddEmailPhoneWindow(aCustomer, EmailOrPhone.EMAIL, this).setVisible(true);
    }//GEN-LAST:event_btnAddEmailActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        jlPhone.clearSelection();
        jlEmail.clearSelection();
        btnDeletePhone.setEnabled(false);
        btnDeleteEmail.setEnabled(false);
    }//GEN-LAST:event_formMouseClicked

    private void tfFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFirstNameActionPerformed

    private void cbCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCountryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCountryActionPerformed

   
    
    
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
            java.util.logging.Logger.getLogger(EditCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new EditCustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEmail;
    private javax.swing.JButton btnAddPhone;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeleteEmail;
    private javax.swing.JButton btnDeletePhone;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbCountry;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<String> jlEmail;
    private javax.swing.JList<String> jlPhone;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblAdressError;
    private javax.swing.JLabel lblCountry;
    private javax.swing.JLabel lblCountryError;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblFirstNameError;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblLastNameError;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPostalCode;
    private javax.swing.JLabel lblPostalCodeError;
    private javax.swing.JLabel lblSaved;
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfFirstName;
    private javax.swing.JTextField tfLastName;
    private javax.swing.JTextField tfPostalCode;
    // End of variables declaration//GEN-END:variables

}
