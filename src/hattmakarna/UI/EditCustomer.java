/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import hattmakarna.data.Customer;
import hattmakarna.data.User;
import static hattmakarna.util.Validerare.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author annae
 */
public class EditCustomer extends javax.swing.JFrame {

    /**
     * Creates new form EditCustomer
     */
    
    private final Customer aCustomer;
    private final ArrayList<String> countries;
    private User userLoggedIn;
    private DefaultListModel<String> dlPhoneModel;
    private DefaultListModel<String> dlEmailModel;
    
    
    public EditCustomer(Customer aCustomer) {
        this.aCustomer = aCustomer;
        
        countries = new ArrayList<>(Arrays.asList(
    "Afghanistan", "Albanien", "Algeriet", "Andorra", "Angola", "Antigua och Barbuda", "Argentina",
    "Armenien", "Australien", "Austrien", "Azerbajdzjan", "Bahamas", "Bahrain", "Bangladesh",
    "Barbados", "Belgien", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnien och Hercegovina",
    "Botswana", "Brasilien", "Brunei", "Bulgarien", "Burkina Faso", "Burundi", "Centralafrikanska republiken",
    "Chile", "Colombia", "Comorerna", "Costa Rica", "Croatien", "Cypern", "Danmark", "Dominica",
    "Dominikanska republiken", "Ecuador", "Egypten", "El Salvador", "Ekvatorialguinea", "Eritrea",
    "Estland", "Etiopien", "Fiji", "Filippinerna", "Finland", "Frankrike", "Gabon", "Gambia",
    "Georgien", "Ghana", "Grekland", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",
    "Haiti", "Honduras", "Indien", "Indonesien", "Irak", "Iran", "Irland", "Island", "Israel",
    "Italien", "Jamaica", "Japan", "Jordanien", "Kambodja", "Kamerun", "Kanada", "Kap Verde", 
    "Kazakstan", "Kenya", "Kina", "Kirgizistan", "Kiribati", "Colombia", "Kongo-Kinshasa",
    "Kongo-Brazzaville", "Kroatien", "Kuba", "Kuwait", "Laos", "Lettland", "Libanon", "Liberia",
    "Libyen", "Liechtenstein", "Litauen", "Luxemburg", "Madagaskar", "Malawi", "Malaysia", 
    "Maldiverna", "Mali", "Malta", "Marocko", "Marshallöarna", "Mauretanien", "Mauritius", 
    "Mexiko", "Mikronesiens federerade stater", "Moldavien", "Monaco", "Mongoliet", "Montenegro",
    "Moçambique", "Myanmar", "Namibia", "Nauru", "Nederländerna", "Nepal", "Nicaragua", "Niger",
    "Nigeria", "Nordkorea", "Nordmakedonien", "Norge", "Nya Zeeland", "Oman", "Pakistan", 
    "Palau", "Panama", "Papua Nya Guinea", "Paraguay", "Peru", "Polen", "Portugal", "Qatar",
    "Rumänien", "Ryssland", "Rwanda", "Saint Kitts och Nevis", "Saint Lucia", "Saint Vincent och Grenadinerna",
    "Salomonöarna", "Samoa", "San Marino", "Saudiarabien", "Schweiz", "Senegal", "Serbien", "Seychellerna",
    "Sierra Leone", "Singapore", "Slovakien", "Slovenien", "Somalia", "Spanien", "Sri Lanka", 
    "Storbritannien", "Sudan", "Surinam", "Swaziland", "Sverige", "Sydafrika", "Sydkorea", "Sydsudan",
    "Syrien", "Tadzjikistan", "Taiwan", "Tanzania", "Thailand", "Tjeckien", "Togo", "Tonga",
    "Trinidad och Tobago", "Tunisien", "Turkiet", "Turkmenistan", "Tuvalu", "Tyskland", "Uganda",
    "Ukraina", "Ungern", "Uruguay", "USA", "Uzbekistan", "Vanuatu", "Vatikanstaten", "Venezuela",
    "Vietnam", "Vitryssland", "Zambia", "Zimbabwe"
));

        
        initComponents();
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
        dlPhoneModel = (DefaultListModel<String>) jlPhone.getModel();
        dlEmailModel = (DefaultListModel<String>) jlEmail.getModel();
    }
    
    private void initCBCountry(){
        cbCountry.addItem("Välj land...");
        for(String aCountry : countries){
            cbCountry.addItem(aCountry);
        }
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
        for(int i = 0; i < countries.size(); i++){
            if(countries.get(i).equals(aCustomer.getCountry())){
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        tfLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfLastNameActionPerformed(evt);
            }
        });
        tfLastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfLastNameKeyTyped(evt);
            }
        });

        tfAdress.setText("jTextField5");
        tfAdress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAdressActionPerformed(evt);
            }
        });
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
        cbCountry.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbCountryKeyTyped(evt);
            }
        });

        lblFirstNameError.setText("jLabel1");

        lblLastNameError.setText("jLabel2");

        lblAdressError.setText("jLabel3");

        lblPostalCodeError.setText("jLabel4");

        lblCountryError.setText("jLabel1");

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
                        .addComponent(btnSave))
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
                .addContainerGap(99, Short.MAX_VALUE))
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
                            .addComponent(btnSave))
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

    private void tfLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfLastNameActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        boolean editOK = true;
        
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
        
        if(cbCountry.getSelectedItem().equals("Välj land...")){
            editOK = false;
            lblCountryError.setText("Inget land valt!");
            lblCountryError.setVisible(true);
        }
        
        if(editOK){
            //Här sparas objektet sen
        }
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tfFirstNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFirstNameKeyTyped
        btnSave.setEnabled(true);
    }//GEN-LAST:event_tfFirstNameKeyTyped

    private void tfLastNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfLastNameKeyTyped
        btnSave.setEnabled(true);
    }//GEN-LAST:event_tfLastNameKeyTyped

    private void tfAdressKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfAdressKeyTyped
        btnSave.setEnabled(true);
    }//GEN-LAST:event_tfAdressKeyTyped

    private void tfAdressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAdressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAdressActionPerformed

    private void tfFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFirstNameActionPerformed

    private void btnDeletePhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePhoneActionPerformed
        aCustomer.removeTelephoneNumber(jlPhone.getSelectedIndex());
        dlPhoneModel.remove(jlPhone.getSelectedIndex());
        btnSave.setEnabled(true);
        if(dlPhoneModel.getSize() == 0){
            btnDeletePhone.setEnabled(false);
        }
    }//GEN-LAST:event_btnDeletePhoneActionPerformed

    private void btnAddPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPhoneActionPerformed
        
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
    }//GEN-LAST:event_tfPostalCodeKeyTyped

    private void cbCountryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbCountryKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCountryKeyTyped

    private void cbCountryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCountryItemStateChanged
        btnSave.setEnabled(true);
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
        dlEmailModel.remove(jlEmail.getSelectedIndex());
        btnSave.setEnabled(true);
        if(dlEmailModel.getSize() == 0){
            btnDeleteEmail.setEnabled(false);
        }
    }//GEN-LAST:event_btnDeleteEmailActionPerformed

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
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfFirstName;
    private javax.swing.JTextField tfLastName;
    private javax.swing.JTextField tfPostalCode;
    // End of variables declaration//GEN-END:variables

}
