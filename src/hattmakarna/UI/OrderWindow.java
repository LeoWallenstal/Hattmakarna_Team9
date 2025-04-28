/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;
import hattmakarna.data.Customer;
import hattmakarna.data.CustomerRegister;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.UI.MainMenu;
import hattmakarna.data.Hat;
import hattmakarna.data.Material;
import hattmakarna.data.MaterialHat;
import hattmakarna.data.MaterialPassContainer;
import hattmakarna.data.Model;
import hattmakarna.data.ModelRegister;
import hattmakarna.data.Order;
import hattmakarna.data.Specification;
import hattmakarna.data.Status;
import hattmakarna.data.User;
import hattmakarna.util.PrintDebugger;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import oru.inf.InfException;

/**
 * @author Användaren
 */
public class OrderWindow extends javax.swing.JFrame {

    CustomerRegister customerRegister;
    private User userLoggedIn;
    ModelRegister modelRegister;
    private ArrayList<Model> hatModels;
    private ArrayList<MaterialHat> tmp_materials;
    private DefaultListModel<String> customerModel;

    private ArrayList<Customer> toDisplay;
    
    int customerID = 0;
    private ArrayList<Pair<Hat, Integer>> hatsToOrder;

    private BufferedImage tmp_spec_image_holder = null;
    private BufferedImage tmp_spec_sketch_holder = null;
    private ArrayList<BufferedImage> threeDImages = new ArrayList<>();
    private boolean isUpdatingTable = false;

    /**
     * Creates new form OrderWindow
     */
    public OrderWindow(User userLoggedIn) {
        initComponents();

        this.setTitle("Registrera order");
        
        tillagd_label.setVisible(false);
        hatsToOrder = new ArrayList<>();
        tmp_materials = new ArrayList<>();
        setLocationRelativeTo(null);
        setResizable(false);
        customerRegister = new CustomerRegister();
        modelRegister = new ModelRegister();
        toDisplay = customerRegister.sortBy(Customer::getFirstName, true);
        initTable();
        hatModels = modelRegister.getAllHats();
        fillModels();
        this.userLoggedIn = userLoggedIn;
        btnChooseCustomer.setEnabled(false);
        btnAddOrder.setEnabled(false);

        jrbName.setSelected(true);
        jtCustomer.setAutoCreateRowSorter(true);
        jtCustomer.setDefaultEditor(Object.class, null);
        // only allow whole-row selection (optional, but often desirable)
        jtCustomer.setRowSelectionAllowed(true);
        jtCustomer.setColumnSelectionAllowed(false);

        // single selection only (optional)
        jtCustomer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // remove any pre-existing selection
        jtCustomer.clearSelection();
        
        
        modelRegister.getAllHats().forEach(e -> {
            baseModelCombo.addItem(e.getName());
        });

        btnRemoveCustomer.setEnabled(false);
        //customerModel = (DefaultListModel<String>) customerJList.getModel();

        // Fixa layout för material lista
        BoxLayout b = new BoxLayout(materialList, BoxLayout.Y_AXIS);
        materialList.setLayout(b);
        jScrollPane2.setViewportView(materialList);
        
        ((DefaultTableModel) tblSeeOrder.getModel()).addTableModelListener(e -> {
            if (isUpdatingTable) {
                return;
            }

            if (e.getType() == TableModelEvent.UPDATE && e.getFirstRow() != TableModelEvent.HEADER_ROW) {
                int row = e.getFirstRow();
                if (row >= 0) {
                    isUpdatingTable = true;
                    updateHatsAndTableRow(row);
                    isUpdatingTable = false;
                }
            }
        });
        
        
        //Sökrutan
        
        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                String text = tfSearch.getText();
                if (text.isEmpty()) {
                    refreshTable();
                }
                else{
                    if(jrbName.isSelected()){
                        toDisplay = customerRegister.searchByName(text);
                    }
                    else{
                        toDisplay = customerRegister.searchByEmail(text);
                    }
                    toDisplay = hattmakarna.util.Util.sortBy(toDisplay, Customer::getFirstName, true);
                    displayResults();
                }
            }
            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        });
        
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbgEmailName = new javax.swing.ButtonGroup();
        paneSidebar = new javax.swing.JPanel();
        lblHeader = new javax.swing.JLabel();
        btnNewCustomer = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        tfSearch = new javax.swing.JTextField();
        lblCustomer = new javax.swing.JLabel();
        lblSearchResult = new javax.swing.JLabel();
        btnChooseCustomer = new javax.swing.JButton();
        btnRemoveCustomer = new javax.swing.JButton();
        jrbEmail = new javax.swing.JRadioButton();
        jrbName = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCustomer = new javax.swing.JTable();
        tabbedPane = new javax.swing.JTabbedPane();
        paneStock = new javax.swing.JPanel();
        scrollModel = new javax.swing.JScrollPane();
        lstModels = new javax.swing.JList<>();
        lblChooseModel = new javax.swing.JLabel();
        btnAddOrder = new javax.swing.JButton();
        spnAmount = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        checkFastDelivery = new javax.swing.JCheckBox();
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
        add_material = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        materialList = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAddSketch = new javax.swing.JButton();
        lblPictureText1 = new javax.swing.JLabel();
        file_path_label_img = new javax.swing.JLabel();
        file_path_label_sketch = new javax.swing.JLabel();
        checkFastDeliverySpec = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        baseModelCombo = new javax.swing.JComboBox<>();
        btn3D = new javax.swing.JButton();
        lbl3Dimages = new javax.swing.JLabel();
        tblOrder = new javax.swing.JScrollPane();
        tblSeeOrder = new javax.swing.JTable();
        lblTotalPrice = new javax.swing.JLabel();
        pnlSaveOrder = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblCustomerOrder = new javax.swing.JLabel();
        canvas1 = new java.awt.Canvas();
        customer_label = new javax.swing.JLabel();
        tillagd_label = new javax.swing.JLabel();
        remove_from_order = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

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

        lblCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCustomer.setText("Kund");

        lblSearchResult.setText("Sökresultat:");

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

        rbgEmailName.add(jrbEmail);
        jrbEmail.setText("Epost");

        rbgEmailName.add(jrbName);
        jrbName.setText("Namn");

        jtCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtCustomerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtCustomer);

        javax.swing.GroupLayout paneSidebarLayout = new javax.swing.GroupLayout(paneSidebar);
        paneSidebar.setLayout(paneSidebarLayout);
        paneSidebarLayout.setHorizontalGroup(
            paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneSidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(paneSidebarLayout.createSequentialGroup()
                        .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(paneSidebarLayout.createSequentialGroup()
                                .addComponent(btnRemoveCustomer)
                                .addGap(153, 153, 153)
                                .addComponent(btnChooseCustomer))
                            .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(paneSidebarLayout.createSequentialGroup()
                                    .addComponent(tfSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                    .addGap(84, 84, 84)
                                    .addComponent(btnNewCustomer))
                                .addComponent(lblCustomer)
                                .addComponent(lblSearchResult)
                                .addComponent(btnReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(paneSidebarLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(jrbName)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jrbEmail))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(0, 18, Short.MAX_VALUE)))
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
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbEmail)
                    .addComponent(jrbName))
                .addGap(10, 10, 10)
                .addComponent(lblSearchResult)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveCustomer)
                    .addComponent(btnChooseCustomer))
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

        paneStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paneStockMouseClicked(evt);
            }
        });

        lstModels.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstModelsMouseClicked(evt);
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

        checkFastDelivery.setText("Snabbleverans");
        checkFastDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFastDeliveryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneStockLayout = new javax.swing.GroupLayout(paneStock);
        paneStock.setLayout(paneStockLayout);
        paneStockLayout.setHorizontalGroup(
            paneStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneStockLayout.createSequentialGroup()
                .addGroup(paneStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(paneStockLayout.createSequentialGroup()
                        .addComponent(btnAddOrder)
                        .addGap(18, 18, 18)
                        .addComponent(checkFastDelivery))
                    .addComponent(scrollModel)
                    .addComponent(lblChooseModel)
                    .addGroup(paneStockLayout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(spnAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addGroup(paneStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddOrder)
                    .addComponent(checkFastDelivery))
                .addContainerGap(439, Short.MAX_VALUE))
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

        btnAddFile.setText("Välj");
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
        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        add_material.setText("Lägg till");
        add_material.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_materialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout materialListLayout = new javax.swing.GroupLayout(materialList);
        materialList.setLayout(materialListLayout);
        materialListLayout.setHorizontalGroup(
            materialListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        materialListLayout.setVerticalGroup(
            materialListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 257, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(materialList);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Material lista:");

        btnAddSketch.setText("Välj");
        btnAddSketch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSketchActionPerformed(evt);
            }
        });

        lblPictureText1.setText("Skiss/Fil:");

        file_path_label_img.setText(".");

        file_path_label_sketch.setText(".");

        checkFastDeliverySpec.setText("Snabbleverans");
        checkFastDeliverySpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFastDeliverySpecActionPerformed(evt);
            }
        });

        jLabel4.setText("Basmodell:");

        btn3D.setText("Välj");
        btn3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3DActionPerformed(evt);
            }
        });

        lbl3Dimages.setText("3D-bilder:");

        javax.swing.GroupLayout paneSpecialLayout = new javax.swing.GroupLayout(paneSpecial);
        paneSpecial.setLayout(paneSpecialLayout);
        paneSpecialLayout.setHorizontalGroup(
            paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneSpecialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeaderSpecial)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(paneSpecialLayout.createSequentialGroup()
                                .addComponent(file_path_label_img, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE))
                            .addComponent(file_path_label_sketch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lblDescriptiom, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addGroup(paneSpecialLayout.createSequentialGroup()
                                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneSpecialLayout.createSequentialGroup()
                                        .addComponent(lblSpecPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneSpecialLayout.createSequentialGroup()
                                        .addComponent(lblSize)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbxSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneSpecialLayout.createSequentialGroup()
                                        .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneSpecialLayout.createSequentialGroup()
                                                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblPictureText1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(paneSpecialLayout.createSequentialGroup()
                                                .addComponent(lbl3Dimages)
                                                .addGap(26, 26, 26)))
                                        .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(baseModelCombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAddSketch, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btn3D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(paneSpecialLayout.createSequentialGroup()
                                        .addComponent(lblPictureText)
                                        .addGap(42, 42, 42)
                                        .addComponent(btnAddFile, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                                .addGap(18, 28, Short.MAX_VALUE)
                                .addComponent(add_material)))
                        .addGap(415, 415, 415)
                        .addComponent(lblPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addComponent(btnAddSpecialToOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkFastDeliverySpec)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(lblPicture, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(add_material)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneSpecialLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(baseModelCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSize)
                    .addComponent(cbxSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSpecPrice)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(file_path_label_img))
                    .addGroup(paneSpecialLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPictureText)
                            .addComponent(btnAddFile, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPictureText1)
                    .addComponent(btnAddSketch)
                    .addComponent(file_path_label_sketch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl3Dimages)
                    .addComponent(btn3D))
                .addGap(8, 8, 8)
                .addGroup(paneSpecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddSpecialToOrder)
                    .addComponent(checkFastDeliverySpec))
                .addContainerGap())
        );

        tabbedPane.addTab("Specialmodell", paneSpecial);

        tblSeeOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Model", "Storlek", "Material", "Antal", "St/pris", "Total", "Express"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSeeOrder.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblSeeOrder.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblSeeOrderPropertyChange(evt);
            }
        });
        tblOrder.setViewportView(tblSeeOrder);

        lblTotalPrice.setText("Totalpris:");

        pnlSaveOrder.setText("Spara");
        pnlSaveOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pnlSaveOrderActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Order summering för kund: ");

        lblCustomerOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        customer_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        customer_label.setText("Välj kund");

        tillagd_label.setBackground(new java.awt.Color(0, 0, 0));
        tillagd_label.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        tillagd_label.setForeground(new java.awt.Color(50, 250, 0));
        tillagd_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tillagd_label.setText("Tillagd!");
        tillagd_label.setToolTipText("");

        remove_from_order.setText("Ta bort");
        remove_from_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove_from_orderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(paneSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(customer_label, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(remove_from_order))
                                        .addGap(137, 137, 137)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(164, 164, 164)
                                                .addComponent(lblCustomerOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pnlSaveOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(643, 643, 643)
                                        .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tblOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tillagd_label, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(184, 184, 184))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paneSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCustomerOrder)
                        .addComponent(customer_label)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalPrice)
                    .addComponent(pnlSaveOrder)
                    .addComponent(remove_from_order))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tillagd_label, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabbedPane)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCustomerActionPerformed
        //this.setVisible(false);

        new RegisterCustomerWindow(userLoggedIn, this).setVisible(true);

    }//GEN-LAST:event_btnNewCustomerActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        new MainMenu(userLoggedIn).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnChooseCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseCustomerActionPerformed
        selectCustomer();
    }//GEN-LAST:event_btnChooseCustomerActionPerformed

    private void add_materialActionPerformed() {

    }

    private void displayResults(){
        DefaultTableModel listModel = new DefaultTableModel();
        jtCustomer.setModel(listModel);
        
        //clear
        listModel.setRowCount(0);
        
        listModel.addColumn("Namn");
        listModel.addColumn("Email");
        
        for(Customer aCustomer : toDisplay){
            String[] rowData = new String[2];
            rowData[0] = aCustomer.getFullName();
            rowData[1] = aCustomer.getEmailAdress();
            listModel.addRow(rowData);
        }
    }
    
    private void btnRemoveCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveCustomerActionPerformed
        //Dialogfönster
        int customerIndex = jtCustomer.getSelectedColumn();
        Customer toRemove = toDisplay.get(customerIndex);
        Object[] options = {"Ja", "Nej"};

        int result = JOptionPane.showOptionDialog(this,
                ("Är du säker på att du vill ta bort " + toRemove.getFullName() + "?"),
                "Varning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);

        if (result == 0) {
            /* "Ja" klickades, 
                    tar bort den kunden och stänger dialogen. */
            DefaultTableModel dtModel = (DefaultTableModel) jtCustomer.getModel();
            dtModel.removeRow(customerIndex);
            toDisplay.remove(customerIndex);
            //customerRegister.remove(customerIndex)
            toRemove.delete();
        } else if (result == 1) {
            jtCustomer.clearSelection();
            btnRemoveCustomer.setEnabled(false);
            btnChooseCustomer.setEnabled(false);
        }
    }//GEN-LAST:event_btnRemoveCustomerActionPerformed

    private void paneSidebarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paneSidebarMouseClicked
        btnRemoveCustomer.setEnabled(false);
        btnChooseCustomer.setEnabled(false);
        btnAddOrder.setEnabled(false);
        
        lstModels.clearSelection();
        jtCustomer.clearSelection();
    }//GEN-LAST:event_paneSidebarMouseClicked

    private void checkFastDeliverySpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFastDeliverySpecActionPerformed

    }//GEN-LAST:event_checkFastDeliverySpecActionPerformed

    private void pnlSaveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pnlSaveOrderActionPerformed
        saveOrder();
    }//GEN-LAST:event_pnlSaveOrderActionPerformed

    private void tabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabbedPaneMouseClicked
        btnRemoveCustomer.setEnabled(false);
        btnChooseCustomer.setEnabled(false);
        btnAddOrder.setEnabled(false);
        
        lstModels.clearSelection();
        jtCustomer.clearSelection();
    }//GEN-LAST:event_tabbedPaneMouseClicked

    private void btnAddOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrderActionPerformed
        fillStockHatToOrder();
    }//GEN-LAST:event_btnAddOrderActionPerformed

    private void add_materialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_materialActionPerformed
        MaterialPassContainer pass = new MaterialPassContainer();
        new PickMaterialDialog(pass);

        if (pass.m == null) {
            return;
        }

        boolean contains = false;
        for (MaterialHat m : tmp_materials) {
            if (pass.m.getMaterialID().equals(String.valueOf(m.getMaterial_id()))) {
                contains = true;
                break;
            }
        }

        if (!contains) {
            MaterialHat mh = new MaterialHat();
            mh.setMaterial_id(Integer.parseInt(pass.m.getMaterialID()));
            // mh.setHat_id();
            tmp_materials.add(mh);

            renderMaterial();
        }

     }//GEN-LAST:event_add_materialActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void btnAddSpecialToOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSpecialToOrderActionPerformed
        fillSpecHatToOrder();
    }//GEN-LAST:event_btnAddSpecialToOrderActionPerformed

    private void btnAddFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFileActionPerformed
        tmp_spec_image_holder = Specification.setFileFromUser();
        file_path_label_img.setText(tmp_spec_image_holder.toString());
    }//GEN-LAST:event_btnAddFileActionPerformed

    private void btnAddSketchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSketchActionPerformed
        tmp_spec_sketch_holder = Specification.setFileFromUser();
        file_path_label_sketch.setText(tmp_spec_image_holder.toString());
    }//GEN-LAST:event_btnAddSketchActionPerformed

    private void checkFastDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFastDeliveryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkFastDeliveryActionPerformed

    private void remove_from_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove_from_orderActionPerformed

        int selectedViewIndex = tblSeeOrder.getSelectedRow();

        if (selectedViewIndex < 0) {
            return;
        }

        int modelIndex = tblSeeOrder.convertRowIndexToModel(selectedViewIndex);

        if (modelIndex >= 0 && modelIndex < hatsToOrder.size()) {
            hatsToOrder.remove(modelIndex);
            updateOrderTable();
        }
    }//GEN-LAST:event_remove_from_orderActionPerformed

    private void tblSeeOrderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblSeeOrderPropertyChange

    }//GEN-LAST:event_tblSeeOrderPropertyChange

    private void paneStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paneStockMouseClicked
        btnRemoveCustomer.setEnabled(false);
        btnChooseCustomer.setEnabled(false);
        btnAddOrder.setEnabled(false);
        
        jtCustomer.clearSelection();
        lstModels.clearSelection();
    }//GEN-LAST:event_paneStockMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        btnRemoveCustomer.setEnabled(false);
        btnChooseCustomer.setEnabled(false);
        btnAddOrder.setEnabled(false);
        jtCustomer.clearSelection();
        lstModels.clearSelection();
    }//GEN-LAST:event_formMouseClicked

    private void lstModelsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstModelsMouseClicked
        btnAddOrder.setEnabled(true);
        btnRemoveCustomer.setEnabled(false);
        btnChooseCustomer.setEnabled(false);
        
        jtCustomer.clearSelection();
    }//GEN-LAST:event_lstModelsMouseClicked

    private void jtCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCustomerMouseClicked
        lstModels.clearSelection();
        
        btnAddOrder.setEnabled(false);
        btnRemoveCustomer.setEnabled(true);
        btnChooseCustomer.setEnabled(true);
    }//GEN-LAST:event_jtCustomerMouseClicked

    private void btn3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3DActionPerformed
        threeDImages = Specification.set3DFilesFromUser();
    }//GEN-LAST:event_btn3DActionPerformed

    private void refreshTable(){
        toDisplay = customerRegister.sortBy(Customer::getFirstName, true);
        PrintDebugger.info("refreshTable()");
        DefaultTableModel listModel = new DefaultTableModel();
        jtCustomer.setModel(listModel);
        
        listModel.addColumn("Namn");
        listModel.addColumn("Email");
        
        for(Customer aCustomer : toDisplay){
            String[] rowData = new String[2];
            rowData[0] = aCustomer.getFullName();
            rowData[1] = aCustomer.getEmailAdress();
            listModel.addRow(rowData);
        }
    }
    
    private void updateHatsAndTableRow(int row) {
        DefaultTableModel model = (DefaultTableModel) tblSeeOrder.getModel();

        Hat hat = hatsToOrder.get(row).left;
        int count = hatsToOrder.get(row).right;

        // Get and update new price
        Object objPrice = model.getValueAt(row, 4);
        if (objPrice instanceof Number) {
            hat.setPrice(((Number) objPrice).doubleValue());
        }

        // Get and update new count
        Object objCount = model.getValueAt(row, 3);
        if (objCount instanceof Number) {
            count = ((Number) objCount).intValue();
        }

        hatsToOrder.set(row, new Pair<>(hat, count));

        // Recalculate individual total
        double rowTotal = hat.getPrice() * count * (hat.isIsExpress() ? 1.2 : 1);
        model.setValueAt(rowTotal, row, 5); // Update total column

        // Optionally update express column too (column 6)
        model.setValueAt(hat.isIsExpress() ? "X" : "", row, 6);

        // Recalculate overall total
        double grandTotal = 0;
        for (Pair<Hat, Integer> entry : hatsToOrder) {
            double itemTotal = entry.left.getPrice() * entry.right * (entry.left.isIsExpress() ? 1.2 : 1);
            grandTotal += itemTotal;
        }

        setTotal(grandTotal);
    }

    private void saveSpecOrder() {
        Specification specification = new Specification();
        String description = txtSpecialDesc.getText();
        String size = String.valueOf(cbxSize.getSelectedItem());
        specification.setDesciption(description);
        specification.save();

        MaterialPassContainer pass = new MaterialPassContainer();
        new PickMaterialDialog(pass);

        if (pass.m == null) {
            return;
        }

        boolean contains = false;
        for (MaterialHat m : tmp_materials) {
            if (pass.m.getMaterialID().equals(String.valueOf(m.getMaterial_id()))) {
                contains = true;
                break;
            }
        }

        if (!contains) {
            MaterialHat mh = new MaterialHat();
            mh.setMaterial_id(Integer.parseInt(pass.m.getMaterialID()));

            // mh.setHat_id();
            tmp_materials.add(mh);

            renderMaterial();
        }

    }

    private void renderMaterial() {
        materialList.removeAll();

        final int[] index = {0};

        tmp_materials.forEach(mat -> {
            JPanel jp = new JPanel();
            jp.setMaximumSize(new Dimension(300, 35));
            JLabel label = new JLabel(mat.getMaterial().getName());

            jp.add(label);

            JTextField field = new JTextField(String.valueOf(mat.getAmount()));

            field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    FraktSedelUI.handleNumberStringJField(e);
                    if (field.getText().isEmpty()) {
                        return;
                    }

                    int amount = Integer.parseInt(field.getText());
                    mat.setAmount(amount);
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
            jp.add(field);

            JLabel unitLabel = new JLabel(mat.getMaterial().getUnit());

            jp.add(unitLabel);

            final int const_index = index[0];

            JButton btn = new JButton("Ta bort");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tmp_materials.remove(const_index);
                    renderMaterial();
                }
            });

            jp.add(btn);

            index[0]++;
            materialList.add(jp);
        });
        materialList.revalidate();
        materialList.repaint();
    }

    private void saveOrder() {

        if (customerID == 0) {
            JOptionPane.showMessageDialog(this, "Välj en kund... :/");
            return;
        }

        if (hatsToOrder.size() == 0) {
            JOptionPane.showMessageDialog(this, "Ange hattar för ordern!");
            return;
        }

        List<Hat> expressOrder = new ArrayList<Hat>();
        List<Hat> normalOrder = new ArrayList<Hat>();

        hatsToOrder.forEach(e -> {

            for (int i = 0; i < e.right; i++) {
                if (e.left.isIsExpress()) {
                    expressOrder.add((Hat) e.left.clone());
                } else {
                    normalOrder.add((Hat) e.left.clone());
                }
            }

        });

        if (normalOrder.size() > 0) {
            // Spara order i sales_order
            Order normalOrderObject = new Order();
            normalOrderObject.setCustomerId(customerID);
            normalOrderObject.setFastProduction(false);
            normalOrderObject.setReceivedDate(Calendar.getInstance().getTime());
            normalOrderObject.setStatus(Status.MOTTAGEN);
            normalOrderObject.setHats(normalOrder);

            final double[] total = {0};
            // Calc total price
            normalOrder.forEach(e -> {
                total[0] += e.getPrice();
            });
            normalOrderObject.setTotalPrice(total[0]);

            if (!normalOrderObject.save()) {
                JOptionPane.showMessageDialog(this, "Fel inträffade när ordern skulle sparas!");
                //  hatsToOrder = (ArrayList<Hat>) normalOrder;
            } else {
                hatsToOrder.clear();
                updateOrderTable();
            }
        }

        if (expressOrder.size() > 0) {
            // Spara order i sales_order
            Order expressOrderObject = new Order();
            expressOrderObject.setCustomerId(customerID);
            expressOrderObject.setFastProduction(true);
            expressOrderObject.setReceivedDate(Calendar.getInstance().getTime());
            expressOrderObject.setStatus(Status.MOTTAGEN);
            expressOrderObject.setHats(expressOrder);

            final double[] total = {0};            // Calc total price
            normalOrder.forEach(e -> {
                total[0] += e.getPrice();
            });
            expressOrderObject.setTotalPrice(total[0]);

            if (!expressOrderObject.save()) {
                JOptionPane.showMessageDialog(this, "Fel inträffade när express order skulle sparas!");
                // hatsToOrder.addAll(expressOrder);
            } else {
                updateOrderTable();
            }
        }

        if (hatsToOrder.size() == 0) {

            new Thread() {
                @Override
                public void run() {
                    tillagd_label.setVisible(true);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OrderWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tillagd_label.setVisible(false);
                }
            }.start();
        }

    }

    public void selectCustomer() {
        int index = jtCustomer.getSelectedRow();
        
        Customer c = toDisplay.get(index);
        customerID = Integer.parseInt(c.getCustomerID());
        customer_label.setText(c.getFullName());
    }

    public void refreshCustomers() {
        customerRegister = new CustomerRegister();
        initTable();
    }

    private void initTable() {
        DefaultTableModel listModel = new DefaultTableModel();
        jtCustomer.setModel(listModel);
        
        listModel.addColumn("Namn");
        listModel.addColumn("Email");
        
        for(Customer aCustomer : toDisplay){
            String[] rowData = new String[2];
            rowData[0] = aCustomer.getFullName();
            rowData[1] = !aCustomer.getEmailAdresses().isEmpty()
                ? aCustomer.getEmailAdress() : "";
            listModel.addRow(rowData);
        }
    }

    private void fillModels() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        lstModels.setModel(listModel);

        for (Model model : hatModels) {
            if (!model.getModelID().equals("1")) {
                listModel.addElement(model.getName());
            }
        }

    }

    private void fillStockHatToOrder() {

        Model selectedModel = null;

        if (lstModels.getSelectedIndex() != -1) {
            selectedModel = hatModels.get(lstModels.getSelectedIndex() + 1);
        } else {
            return;
        }

        int quantity = (int) spnAmount.getValue();
        boolean isExpress = checkFastDelivery.isSelected();
        String mId = selectedModel.getModelID();

        int alikeIndex = -1;
        int newCount = 0;
        int i = 0;
        for (Pair<Hat, Integer> p : hatsToOrder) {

            if (p.left.getModelId().equals(mId) && p.left.isIsExpress() == isExpress && !p.left.isIsSpecial()) {
                alikeIndex = i;
                newCount = quantity + p.right;
                break;
            }
            i++;
        }

        if (alikeIndex != -1) {
            hatsToOrder.set(i, new Pair<Hat, Integer>(hatsToOrder.get(i).left, newCount));
        } else {
            Hat hat = new Hat();
            hat.setSize("one-size");
            hat.setModelId(mId);
            hat.setPrice(hat.getModel().getPrice());
            hat.setIsSpecial(false);
            hat.setIsExpress(isExpress);
            hatsToOrder.add(new Pair<Hat, Integer>(hat, quantity));
        }

        updateOrderTable();
    }

    private void setTotal(double total) {
        lblTotalPrice.setText("Totalpris: " + total + " kr");
    }

    private void fillSpecHatToOrder() {
        String description = txtSpecialDesc.getText();
        String size = cbxSize.getSelectedItem().toString();
        String priceString = txtPrice.getText();
        double price = Double.parseDouble(priceString);

        String pickedBaseModel = modelRegister.getAllHats().get(baseModelCombo.getSelectedIndex()).getModelID();

        Hat h = new Hat();
        h.setPrice(price);
        h.setModelId(pickedBaseModel);
        h.setIsSpecial(true);
        h.setSize(size);
        h.getSpecification().setDesciption(description);
        h.getSpecification().setSkiss(tmp_spec_sketch_holder);
        h.getSpecification().setImgImage(tmp_spec_image_holder);
        h.getSpecification().setExtraImages(threeDImages);
        
        h.getMaterials().addAll(tmp_materials);
        h.setIsExpress(checkFastDeliverySpec.isSelected());
        hatsToOrder.add(new Pair<Hat, Integer>(h, 1));

        updateOrderTable();

        tmp_materials.clear();

        renderMaterial();

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
    private javax.swing.JButton add_material;
    private javax.swing.JComboBox<String> baseModelCombo;
    private javax.swing.JButton btn3D;
    private javax.swing.JButton btnAddFile;
    private javax.swing.JButton btnAddOrder;
    private javax.swing.JButton btnAddSketch;
    private javax.swing.JButton btnAddSpecialToOrder;
    private javax.swing.JButton btnChooseCustomer;
    private javax.swing.JButton btnNewCustomer;
    private javax.swing.JButton btnRemoveCustomer;
    private javax.swing.JButton btnReturn;
    private java.awt.Canvas canvas1;
    private javax.swing.JComboBox<String> cbxSize;
    private javax.swing.JCheckBox checkFastDelivery;
    private javax.swing.JCheckBox checkFastDeliverySpec;
    private javax.swing.JLabel customer_label;
    private javax.swing.JLabel file_path_label_img;
    private javax.swing.JLabel file_path_label_sketch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton jrbEmail;
    private javax.swing.JRadioButton jrbName;
    private javax.swing.JTable jtCustomer;
    private javax.swing.JLabel lbl3Dimages;
    private javax.swing.JLabel lblChooseModel;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblCustomerOrder;
    private javax.swing.JLabel lblDescriptiom;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblHeaderSpecial;
    private javax.swing.JLabel lblPicture;
    private javax.swing.JLabel lblPictureText;
    private javax.swing.JLabel lblPictureText1;
    private javax.swing.JLabel lblSearchResult;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblSpecPrice;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JList<String> lstModels;
    private javax.swing.JPanel materialList;
    private javax.swing.JPanel paneSidebar;
    private javax.swing.JPanel paneSpecial;
    private javax.swing.JPanel paneStock;
    private javax.swing.JButton pnlSaveOrder;
    private javax.swing.ButtonGroup rbgEmailName;
    private javax.swing.JButton remove_from_order;
    private javax.swing.JScrollPane scrollModel;
    private javax.swing.JSpinner spnAmount;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JScrollPane tblOrder;
    private javax.swing.JTable tblSeeOrder;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JLabel tillagd_label;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextArea txtSpecialDesc;
    // End of variables declaration//GEN-END:variables

    private void updateOrderTable() {

        ((DefaultTableModel) tblSeeOrder.getModel()).setRowCount(0);

        Map<Hat, Integer> hatCountMap = new HashMap<>();

        hatsToOrder.forEach(hat
                -> hatCountMap.put(hat.left, hatCountMap.getOrDefault(hat.left, 0) + hat.right)
        );
        final double total[] = {0};

        hatsToOrder.forEach(e -> {

            StringBuilder materialListString = new StringBuilder();

            e.left.getMaterials().forEach(mh -> {
                materialListString.append(mh.getMaterial().getName()).append(", ");
            });

            ((DefaultTableModel) tblSeeOrder.getModel()).addRow(new Object[]{
                e.left.getModel().getName() + (e.left.isIsSpecial() ? "(Special)" : ""), // Model namn

                e.left.getSize(), // Storlek
                materialListString.toString(), // Material lista
                e.right, // Antal
                e.left.getPrice(), // Styck pris
                e.left.getPrice() * e.right * (e.left.isIsExpress() ? 1.2 : 1),
                e.left.isIsExpress() ? "X" : ""}); // Totalt pris

            total[0] += e.left.getPrice() * e.right * (e.left.isIsExpress() ? 1.2 : 1);
        });
        setTotal(total[0]);

        repaint();
        revalidate();

    }
}
