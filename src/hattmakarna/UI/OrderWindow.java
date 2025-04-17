/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import hattmakarna.data.Customer;
import hattmakarna.data.CustomerRegister;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.UI.MainMenu;
import hattmakarna.data.Hat;
import hattmakarna.data.Material;
import hattmakarna.data.Model;
import hattmakarna.data.ModelRegister;
import hattmakarna.data.Order;
import hattmakarna.data.Specification;
import hattmakarna.data.User;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import oru.inf.InfException;

/**
 *
 * @author Användaren
 */
public class OrderWindow extends javax.swing.JFrame {

    private double totalPrice;
    private double originalTotalPrice = 0;
    private boolean isExpress;
    CustomerRegister customerRegister;
    private User userLoggedIn;
    ModelRegister modelRegister;
    private ArrayList<Model> hatModels;
    private DefaultTableModel tableModel;
    private DefaultListModel<String> customerModel;
    int customerID = 0;

    /**
     * Creates new form OrderWindow
     */
    public OrderWindow(User userLoggedIn) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        customerRegister = new CustomerRegister();
        modelRegister = new ModelRegister();
        fillSearchResults();
        hatModels = modelRegister.getAllHats();
        fillModels();
        this.userLoggedIn = userLoggedIn;
        tableModel = new DefaultTableModel(new String[]{"Modell", "Beskrivning", "Storlek", "Antal"}, 0);
        tblSeeOrder.setModel(tableModel);
        btnRemoveCustomer.setEnabled(false);
        
        customerModel = (DefaultListModel<String>)customerJList.getModel();

        totalPrice = 0;
        isExpress = false;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneSidebar = new javax.swing.JPanel();
        lblHeader = new javax.swing.JLabel();
        btnNewCustomer = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        txtSearchEmail = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblCustomer = new javax.swing.JLabel();
        lblSearchResult = new javax.swing.JLabel();
        scrollSearchResult = new javax.swing.JScrollPane();
        customerJList = new javax.swing.JList<>();
        btnChooseCustomer = new javax.swing.JButton();
        btnRemoveCustomer = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        paneStock = new javax.swing.JPanel();
        scrollModel = new javax.swing.JScrollPane();
        lstModels = new javax.swing.JList<>();
        lblChooseModel = new javax.swing.JLabel();
        btnAddOrder = new javax.swing.JButton();
        spnAmount = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        paneSpecial = new javax.swing.JPanel();
        lblHeaderSpecial = new javax.swing.JLabel();
        lblDescriptiom = new javax.swing.JLabel();
        lblSize = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSpecialDesc = new javax.swing.JTextArea();
        cbxSize = new javax.swing.JComboBox<>();
        lblPictureText = new javax.swing.JLabel();
        btnAddFile = new javax.swing.JButton();
        btnAddSpecialToOrder = new javax.swing.JButton();
        lblPicture = new javax.swing.JLabel();
        lblSpecPrice = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        paneOrder = new javax.swing.JPanel();
        tblOrder = new javax.swing.JScrollPane();
        tblSeeOrder = new javax.swing.JTable();
        pnlSaveOrder = new javax.swing.JButton();
        lblTotalPrice = new javax.swing.JLabel();
        checkFastDelivery = new javax.swing.JCheckBox();
        lblCustomerOrder = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        paneSidebar.setForeground(new java.awt.Color(102, 204, 255));
        paneSidebar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paneSidebarMouseClicked(evt);
            }
        });

        lblHeader.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblHeader.setText("Registrera Order");

        btnNewCustomer.setText("Ny Kund");
        btnNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewCustomerActionPerformed(evt);
            }
        });

        btnReturn.setText("Tillbaka");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnSearch.setText("Sök");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCustomer.setText("Kund");

        lblSearchResult.setText("Sökresultat:");

        customerJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerJListMouseClicked(evt);
            }
        });
        scrollSearchResult.setViewportView(customerJList);

        btnChooseCustomer.setText("Välj kund");
        btnChooseCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseCustomerActionPerformed(evt);
            }
        });

        btnRemoveCustomer.setText("Ta bort");
        btnRemoveCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneSidebarLayout = new javax.swing.GroupLayout(paneSidebar);
        paneSidebar.setLayout(paneSidebarLayout);
        paneSidebarLayout.setHorizontalGroup(
            paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneSidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(paneSidebarLayout.createSequentialGroup()
                        .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(paneSidebarLayout.createSequentialGroup()
                                .addComponent(txtSearchEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNewCustomer))
                            .addComponent(scrollSearchResult)
                            .addComponent(lblCustomer)
                            .addComponent(lblSearchResult)
                            .addGroup(paneSidebarLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(btnChooseCustomer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRemoveCustomer))
                            .addComponent(btnReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 96, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneSidebarLayout.setVerticalGroup(
            paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneSidebarLayout.createSequentialGroup()
                .addComponent(lblHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCustomer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnNewCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSearchResult)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollSearchResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChooseCustomer)
                    .addComponent(btnRemoveCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReturn)
                .addContainerGap())
        );

        tabbedPane.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        tabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabbedPaneMouseClicked(evt);
            }
        });

        scrollModel.setViewportView(lstModels);

        lblChooseModel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblChooseModel.setText("Välj modell");

        btnAddOrder.setText("Lägg till i order");
        btnAddOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOrderActionPerformed(evt);
            }
        });

        spnAmount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel3.setText("Antal");

        javax.swing.GroupLayout paneStockLayout = new javax.swing.GroupLayout(paneStock);
        paneStock.setLayout(paneStockLayout);
        paneStockLayout.setHorizontalGroup(
            paneStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneStockLayout.createSequentialGroup()
                .addGroup(paneStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddOrder)
                    .addGroup(paneStockLayout.createSequentialGroup()
                        .addComponent(spnAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollModel)
                    .addComponent(lblChooseModel))
                .addGap(0, 293, Short.MAX_VALUE))
        );
        paneStockLayout.setVerticalGroup(
            paneStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChooseModel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(paneStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(btnAddOrder)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Lagerförd", paneStock);

        lblHeaderSpecial.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHeaderSpecial.setText("Specialtillverkning");

        lblDescriptiom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescriptiom.setText("Beskrivning:");

        lblSize.setText("Storlek:");

        txtSpecialDesc.setColumns(20);
        txtSpecialDesc.setRows(5);
        jScrollPane3.setViewportView(txtSpecialDesc);

        cbxSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "XS", "S", "M", "L", "XL", "XXL" }));

        lblPictureText.setText("Bild/Fil:");

        btnAddFile.setText("Fil");
        btnAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFileActionPerformed(evt);
            }
        });

        btnAddSpecialToOrder.setText("Lägg till i order");
        btnAddSpecialToOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSpecialToOrderActionPerformed(evt);
            }
        });

        lblSpecPrice.setText("Pris:");

        txtPrice.setText("0");

        javax.swing.GroupLayout paneSpecialLayout = new javax.swing.GroupLayout(paneSpecial);
        paneSpecial.setLayout(paneSpecialLayout);
        paneSpecialLayout.setHorizontalGroup(
            paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneSpecialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeaderSpecial)
                    .addComponent(lblDescriptiom)
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addComponent(lblSpecPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAddSpecialToOrder)
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(paneSpecialLayout.createSequentialGroup()
                                .addComponent(lblSize)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lblPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addComponent(lblPictureText, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddFile)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        paneSpecialLayout.setVerticalGroup(
            paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneSpecialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeaderSpecial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDescriptiom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSize)
                    .addComponent(cbxSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSpecPrice)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPictureText)
                    .addComponent(btnAddFile))
                .addGap(32, 32, 32)
                .addComponent(btnAddSpecialToOrder)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Specialmodell", paneSpecial);

        tblSeeOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Modell", "Storlek", "Material", "Antal"
            }
        ));
        tblOrder.setViewportView(tblSeeOrder);

        pnlSaveOrder.setText("Spara");
        pnlSaveOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pnlSaveOrderActionPerformed(evt);
            }
        });

        lblTotalPrice.setText("Totalpris:");

        checkFastDelivery.setText("Snabbleverans");
        checkFastDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFastDeliveryActionPerformed(evt);
            }
        });

        lblCustomerOrder.setText("jLabel1");

        javax.swing.GroupLayout paneOrderLayout = new javax.swing.GroupLayout(paneOrder);
        paneOrder.setLayout(paneOrderLayout);
        paneOrderLayout.setHorizontalGroup(
            paneOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkFastDelivery)
                .addGap(215, 215, 215)
                .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSaveOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tblOrder)
            .addGroup(paneOrderLayout.createSequentialGroup()
                .addComponent(lblCustomerOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        paneOrderLayout.setVerticalGroup(
            paneOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCustomerOrder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addGroup(paneOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pnlSaveOrder)
                    .addComponent(lblTotalPrice)
                    .addComponent(checkFastDelivery))
                .addContainerGap())
        );

        tabbedPane.addTab("Se Order", paneOrder);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(paneSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paneSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabbedPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCustomerActionPerformed
        //this.setVisible(false);

        new RegisterCustomerWindow(userLoggedIn, this).setVisible(true);

    }//GEN-LAST:event_btnNewCustomerActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        fillSearchResults();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFileActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "bmp", "gif");

        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            lblPicture.setIcon(new ImageIcon(selectedFile.getAbsolutePath()));
        }

    }//GEN-LAST:event_btnAddFileActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        new MainMenu(userLoggedIn).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnAddOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrderActionPerformed
        fillStockHatToOrder();
    }//GEN-LAST:event_btnAddOrderActionPerformed

    private void btnAddSpecialToOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSpecialToOrderActionPerformed
        fillSpecHatToOrder();
    }//GEN-LAST:event_btnAddSpecialToOrderActionPerformed

    private void checkFastDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFastDeliveryActionPerformed
        isExpress = checkFastDelivery.isSelected();

        if (isExpress) {
            totalPrice = originalTotalPrice * 1.2; // +20%
        } else {
            totalPrice = originalTotalPrice; // tillbaka till utan express
        }

        lblTotalPrice.setText("Totalpris: " + totalPrice + " kr");

    }//GEN-LAST:event_checkFastDeliveryActionPerformed

    private void pnlSaveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pnlSaveOrderActionPerformed
        saveOrder();
    }//GEN-LAST:event_pnlSaveOrderActionPerformed

    private void btnChooseCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseCustomerActionPerformed
        selectCustomer();
    }//GEN-LAST:event_btnChooseCustomerActionPerformed

    private void btnRemoveCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveCustomerActionPerformed
            //Dialogfönster
            int customerIndex = customerJList.getSelectedIndex();
            Customer toRemove = customerRegister.getCustomer(customerIndex);
            Object[] options = {"Ja", "Nej"};

            int result = JOptionPane.showOptionDialog(this, 
                ("Är du säker på att du vill ta bort " + toRemove.getFullName() + "?"), 
                "Varning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                null, options, options[0]);

            if (result == 0) {
                /* "Ja" klickades, 
                    tar bort den kunden och stänger dialogen. */
                customerModel.remove(customerIndex);
                customerRegister.remove(customerIndex);
                toRemove.delete();
            } 
            else if (result == 1) {
                customerJList.clearSelection();
                btnRemoveCustomer.setEnabled(false);
            }
    }//GEN-LAST:event_btnRemoveCustomerActionPerformed

    private void customerJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerJListMouseClicked
        btnRemoveCustomer.setEnabled(true);
    }//GEN-LAST:event_customerJListMouseClicked

    private void tabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabbedPaneMouseClicked
        btnRemoveCustomer.setEnabled(false);
        customerJList.clearSelection();
    }//GEN-LAST:event_tabbedPaneMouseClicked

    private void paneSidebarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paneSidebarMouseClicked
        btnRemoveCustomer.setEnabled(false);
        customerJList.clearSelection();
    }//GEN-LAST:event_paneSidebarMouseClicked

    private void saveSpecOrder() {
        Specification specification = new Specification();
        String description = txtSpecialDesc.getText();
        String size = String.valueOf(cbxSize.getSelectedItem());
        specification.setDesciption(description);
        specification.save();

    }

    private void saveOrder() {
    try {
        // Räkna ut rätt totalpris beroende på expressleverans
        if (isExpress) {
            totalPrice = originalTotalPrice * 1.2;
        } else {
            totalPrice = originalTotalPrice;
        }

        // Spara order i sales_order
        String query = "INSERT INTO sales_order (price, customer_id, status, recived_date, material_ordered) VALUES ("
                + totalPrice + ", "
                + customerID + ", "
                + "'mottagen', "
                + "'2025-04-14', "
                + "0);";
        idb.insert(query);

        // Hämta nyaste order_id
        String getOrderIdQuery = "SELECT MAX(order_id) FROM sales_order;";
        String orderId = idb.fetchSingle(getOrderIdQuery);

        // Gå igenom alla rader i beställnings-tabellen
        DefaultTableModel model = (DefaultTableModel) tblSeeOrder.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String modelName = model.getValueAt(i, 0).toString();
            int quantity = Integer.parseInt(model.getValueAt(i, 3).toString());

            // Hämta model_id
            String modelId = "1"; // fallback
            for (Model m : hatModels) {
                if (m.getName().equalsIgnoreCase(modelName)) {
                    modelId = m.getModelID();
                    break;
                }
            }

            // Om det är en specialhatt (modelId = 1)
            if (modelId.equals("1")) {
                String desc = model.getValueAt(i, 1).toString();
                String size = model.getValueAt(i, 2).toString();

                for (int j = 0; j < quantity; j++) {
                    // Lägg till i hat
                    String hatQuery = "INSERT INTO hat (model_id, order_id) VALUES ("
                            + modelId + ", " + orderId + ");";
                    idb.insert(hatQuery);

                    // Hämta hat_id
                    String hatId = idb.fetchSingle("SELECT MAX(hat_id) FROM hat");

                    // Lägg till i hat_spec
                    String insertSpecQuery = "INSERT INTO hat_spec (beskrivning, size, hat_id) VALUES ('"
                            + desc.replace("'", "''") + "', '"
                            + size + "', " + hatId + ");";
                    idb.insert(insertSpecQuery);
                }
            }
            // Lagerförd hatt (alla andra modelId)
            else {
                for (int j = 0; j < quantity; j++) {
                    String hatQuery = "INSERT INTO hat (model_id, order_id) VALUES ("
                            + modelId + ", " + orderId + ");";
                    idb.insert(hatQuery);
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Order sparad!");

    } catch (InfException ex) {
        Logger.getLogger(OrderWindow.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Felaktigt antal eller innehåll i tabellen.", "Fel", JOptionPane.ERROR_MESSAGE);
    }
}

    public void selectCustomer() {
        String selectedCustomer = customerJList.getSelectedValue();
        for (Customer customer : customerRegister.getAllCustomers()) {
            if (customer.getFullName().equalsIgnoreCase(selectedCustomer)) {
                customerID = Integer.parseInt(customer.getCustomerID());
                break;
            }
        }
        lblCustomerOrder.setText("Vald kund: " + selectedCustomer);
    }

    public void refreshCustomers() {
        customerRegister = new CustomerRegister();
        fillSearchResults();
    }

    private void fillSearchResults() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        customerJList.setModel(listModel);
        ArrayList<Customer> customerMailList = customerRegister.searchByEmail(txtSearchEmail.getText());
        ArrayList<Customer> customerNameList = customerRegister.searchByName(txtSearchEmail.getText());
        HashMap<String, Customer> customerList = new HashMap<>();

        for (Customer customer : customerMailList) {
            customerList.put(customer.getCustomerID(), customer);
        }

        for (Customer customer : customerNameList) {
            customerList.put(customer.getCustomerID(), customer);
        }

        for (Customer customer : customerList.values()) {
            listModel.addElement(customer.getFullName());
        }
    }

    private void fillModels() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        lstModels.setModel(listModel);

        for (Model model : hatModels) {
            listModel.addElement(model.getName());
        }

    }

    private void fillStockHatToOrder() {

        String selectedModel = lstModels.getSelectedValue();
        String id = "";
        for (Model model : hatModels) {
            if (model.getName().equalsIgnoreCase(selectedModel)) {
                id = model.getModelID();
                break;
            }
        }
        int quantity = (int) spnAmount.getValue();

        Model model = modelRegister.getModel(id);

        if (model != null) {
            double price = model.getPrice();
            double totalPriceForItem = price * quantity;

            tableModel.addRow(new Object[]{
                model.getName(),
                "",
                "",
                quantity,});

            originalTotalPrice += totalPriceForItem;
            lblTotalPrice.setText("Totalpris: " + originalTotalPrice + " kr");
        } else {
            System.out.println("Ingen modell vald!");
        }

    }

    private void fillSpecHatToOrder() {
        String description = txtSpecialDesc.getText();
        String size = cbxSize.getSelectedItem().toString();
        String priceString = txtPrice.getText();
        double price = Double.parseDouble(priceString);
        int quantity = 1;

        tableModel.addRow(new Object[]{
            "Specialhatt",
            description,
            size,
            quantity,});

        originalTotalPrice += price;
        lblTotalPrice.setText("Totalpris: " + originalTotalPrice + " kr");

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
            java.util.logging.Logger.getLogger(OrderWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new OrderWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFile;
    private javax.swing.JButton btnAddOrder;
    private javax.swing.JButton btnAddSpecialToOrder;
    private javax.swing.JButton btnChooseCustomer;
    private javax.swing.JButton btnNewCustomer;
    private javax.swing.JButton btnRemoveCustomer;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbxSize;
    private javax.swing.JCheckBox checkFastDelivery;
    private javax.swing.JList<String> customerJList;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblChooseModel;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblCustomerOrder;
    private javax.swing.JLabel lblDescriptiom;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblHeaderSpecial;
    private javax.swing.JLabel lblPicture;
    private javax.swing.JLabel lblPictureText;
    private javax.swing.JLabel lblSearchResult;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblSpecPrice;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JList<String> lstModels;
    private javax.swing.JPanel paneOrder;
    private javax.swing.JPanel paneSidebar;
    private javax.swing.JPanel paneSpecial;
    private javax.swing.JPanel paneStock;
    private javax.swing.JButton pnlSaveOrder;
    private javax.swing.JScrollPane scrollModel;
    private javax.swing.JScrollPane scrollSearchResult;
    private javax.swing.JSpinner spnAmount;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JScrollPane tblOrder;
    private javax.swing.JTable tblSeeOrder;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtSearchEmail;
    private javax.swing.JTextArea txtSpecialDesc;
    // End of variables declaration//GEN-END:variables
}
