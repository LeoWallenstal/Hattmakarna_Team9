/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import hattmakarna.data.Customer;
import hattmakarna.data.Hat;
import hattmakarna.data.Order;
import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import oru.inf.InfException;

/**
 * FraktSedelUI hanterar visning och utskrift av fraktsedlar för kundorder.
 * Denna klass ansvarar för att ladda kund- och orderdata, visa informationen i
 * ett GUI, samt generera och öppna en PDF-fraktsedel baserad på en HTML-mall.
 * <p>
 * Användaren kan inte redigera kundinformation, men kan justera fraktrelaterade
 * fält innan fraktsedeln skrivs ut.
 * </p>
 *
 * @author leonb
 */
public class FraktSedelUI extends javax.swing.JFrame {

    private Order order;
    private JFrame parent;

    /**
     * Skapar ett nytt fönster för att visa och hantera fraktsedlar. Hämtar
     * kund- och orderdata baserat på angivet order-ID och initierar
     * komponenter.
     *
     * @param parent det överordnade fönstret som öppnade denna vy
     * @param id order-ID som används för att hämta information om ordern och
     * kunden
     */
    public FraktSedelUI(JFrame parent, String id) {
        this.parent = parent;
        initComponents();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setupCustomerInfo(id);
        setupDefaultOrderInfo();
    }

    /**
     * Fyller i standardinformation för ordern såsom vikt, moms och totalpris.
     * Används efter att ordern har laddats för att initialisera formuläret.
     */
    private void setupDefaultOrderInfo() {
        weight_field.setText("0");
        vat_field.setText("25");
        freight_field.setText("0");
        order_id.setText("#" + order.getOrder_id());

        order.getHattarObjects().forEach(e -> {
            ((DefaultTableModel) order_summary_table.getModel()).addRow(new String[]{e.gethatId(), e.getModel().getName(), String.format("%.2f kr", e.getPrice())});
        });

        total_field.setText(getTotal());

    }

    /**
     * Räknar ut och returnerar det totala priset för ordern, inklusive hattar
     * och eventuell frakt.
     *
     * @return en sträng med det totala priset formaterat med två decimaler
     */
    private String getTotal() {

        double summa = 0;

        // Summan av alla hattar
        for (Hat h : order.getHattarObjects()) {
            summa += h.getPrice();
        }

        summa += (Double.parseDouble(vat_field.getText()) / 100) * summa;
        summa += Double.parseDouble(freight_field.getText());

        return String.format("%.2f", summa);
    }

    /**
     * Hämtar kunddata kopplat till order-ID och fyller i fälten med kundens
     * namn, adress, e-postadresser och telefonnummer.
     *
     * @param id order-ID som används för att identifiera kunden
     */
    private void setupCustomerInfo(String id) {
        order = new Order(id);

        Customer customer = order.getCustomer();

        namn_field.setText(customer.getFirstName());
        namn_field.setEnabled(false);
        lastname_field.setText(customer.getLastName());
        lastname_field.setEnabled(false);
        adress_field.setText(customer.getAdress());
        adress_field.setEnabled(false);

        customer.getTelephoneNumbers().forEach(e -> {
            phone_combobox.addItem(e);
        });

        customer.getEmailAdresses().forEach(e -> {
            email_combobox.addItem(e);
        });

    }

    /**
     * Genererar en PDF-fraktsedel genom att läsa in en HTML-mall, ersätta
     * platshållare med aktuell data, och spara resultatet som en PDF-fil.
     * Försöker öppna PDF-filen automatiskt om systemet stöder det.
     */
    private void printDeckleration() {
        try {

            String html = Files.readString(Paths.get("htmlFiles/shippingLabel.html"), StandardCharsets.UTF_8);

            html = html.replace("{weight}", weight_field.getText());
            html = html.replace("{orderId}", String.valueOf(order.getOrder_id()));
            html = html.replace("{shipping}", "Postnord AB, 2 days");
            html = html.replace("{value}", getTotal());
            html = html.replace("{shippingCode}", "#" + shippingNumberField.getText());

            html = html.replace("{name}", order.getCustomer().getFullName());
            html = html.replace("{address}", order.getCustomer().getAdress());
            html = html.replace("{email}", email_combobox.getSelectedItem().toString());
            html = html.replace("{telephone}", phone_combobox.getSelectedItem().toString());

            StringBuilder br = new StringBuilder();
            order.getHattarObjects().forEach(e -> {

                br.append("<tr>");
                br.append("<td>");
                br.append("Hat model:");
                br.append("</td>");
                br.append("<td>");
                br.append(e.getModel().getName());
                br.append("</td>");
                br.append("</tr>");
            });

            html = html.replace("{orderContent}", br.toString());

            try (FileOutputStream os = new FileOutputStream("output.pdf")) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(html, null);
                builder.toStream(os);
                builder.run();

                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("output.pdf"));
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FraktSedelUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FraktSedelUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(FraktSedelUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (java.io.IOException ex) {
            Logger.getLogger(FraktSedelUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        order_id = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        order_summary_table = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        namn_field = new javax.swing.JTextField();
        lastname_field = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        adress_field = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        weight_field = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        vat_field = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        freight_field = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        total_field = new javax.swing.JTextField();
        email_combobox = new javax.swing.JComboBox<>();
        phone_combobox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        shippingNumberField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        return_button = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Order:");

        order_id.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        order_id.setText("jLabel2");

        jScrollPane1.setBackground(new java.awt.Color(225, 150, 200));

        order_summary_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hat id", "model", "pris"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(order_summary_table);

        jLabel3.setText("Innehåll");

        jButton1.setText("Skriv ut sedel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintShippingDetails(evt);
            }
        });

        jLabel4.setText("Namn");

        namn_field.setEditable(false);
        namn_field.setText("jTextField1");
        namn_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namn_fieldActionPerformed(evt);
            }
        });

        lastname_field.setText("jTextField1");
        lastname_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastname_fieldActionPerformed(evt);
            }
        });

        jLabel5.setText("Efternamn");

        jLabel6.setText("Adress");

        adress_field.setText("jTextField1");
        adress_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adress_fieldActionPerformed(evt);
            }
        });

        jLabel7.setText("E-post");

        jLabel8.setText("Tel");

        jSeparator1.setBackground(new java.awt.Color(42, 42, 42));
        jSeparator1.setForeground(new java.awt.Color(6, 6, 6));

        jLabel9.setText("Mottagare");

        jLabel10.setText("Paket information");

        jLabel11.setText("Vikt");

        weight_field.setText("jTextField1");
        weight_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weight_fieldActionPerformed(evt);
            }
        });
        weight_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                weight_fieldKeyTyped(evt);
            }
        });

        jLabel12.setText("Moms");

        vat_field.setText("jTextField1");
        vat_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vat_fieldActionPerformed(evt);
            }
        });
        vat_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                vat_fieldKeyTyped(evt);
            }
        });

        jLabel13.setText("Frakt");

        freight_field.setText("jTextField1");
        freight_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freight_fieldActionPerformed(evt);
            }
        });
        freight_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                freight_fieldKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                freight_fieldKeyTyped(evt);
            }
        });

        jLabel14.setText("Total");

        total_field.setText("jTextField1");
        total_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                total_fieldActionPerformed(evt);
            }
        });
        total_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                total_fieldKeyTyped(evt);
            }
        });

        email_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                email_comboboxActionPerformed(evt);
            }
        });

        phone_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phone_comboboxActionPerformed(evt);
            }
        });

        jLabel2.setText("kg");

        jLabel15.setText("%");

        jLabel16.setText("kr");

        jLabel17.setText("kr");

        shippingNumberField.setText("jTextField1");
        shippingNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shippingNumberFieldActionPerformed(evt);
            }
        });
        shippingNumberField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                shippingNumberFieldKeyTyped(evt);
            }
        });

        jLabel19.setText("Frakt-nr");

        return_button.setText("Tillbaka");
        return_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(order_id))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(return_button))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shippingNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(vat_field, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(freight_field, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(weight_field, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(adress_field, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(phone_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(email_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(namn_field, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(total_field, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lastname_field, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(70, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(return_button)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(order_id))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(namn_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(lastname_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(adress_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(email_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(phone_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(weight_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(vat_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(freight_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(total_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(shippingNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
     * Validerar alla inmatade fält i formuläret. Säkerställer att vikt, moms,
     * frakt och totalbelopp är giltiga värden och att fraktnumret inte är tomt.
     *
     * @return true om all data är giltig, annars false
     */
    private boolean allInputsValid() {
        if (Double.parseDouble(weight_field.getText().replace(",", ".")) <= 0) {
            return false;
        }
        if (Double.parseDouble(freight_field.getText().replace(",", ".")) < 0) {
            return false;
        }
        if (Double.parseDouble(total_field.getText().replace(",", ".")) < 0) {
            return false;
        }
        if (Double.parseDouble(vat_field.getText().replace(",", ".")) < 0) {
            return false;
        }
        if (shippingNumberField.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Event-handler för "Skriv ut sedel"-knappen. Kontrollerar att alla fält
     * innehåller giltig information innan fraktsedeln skrivs ut. Visar ett
     * felmeddelande om valideringen misslyckas.
     *
     * @param evt action-event genererat av knapptryckningen
     */
    private void PrintShippingDetails(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintShippingDetails

        if (allInputsValid())
            printDeckleration();
        else
            JOptionPane.showMessageDialog(this,
                    "Ett eller mer fält är fel", "error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_PrintShippingDetails

    private void namn_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namn_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namn_fieldActionPerformed

    private void lastname_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastname_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastname_fieldActionPerformed

    private void adress_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adress_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adress_fieldActionPerformed

    private void weight_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weight_fieldActionPerformed
        System.out.print(evt.toString());
    }//GEN-LAST:event_weight_fieldActionPerformed

    private void vat_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vat_fieldActionPerformed

        total_field.setText(getTotal());
    }//GEN-LAST:event_vat_fieldActionPerformed

    private void freight_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_freight_fieldActionPerformed
        total_field.setText(getTotal());
    }//GEN-LAST:event_freight_fieldActionPerformed

    private void total_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_total_fieldActionPerformed
    }//GEN-LAST:event_total_fieldActionPerformed

    private void email_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_email_comboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_email_comboboxActionPerformed

    private void phone_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phone_comboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phone_comboboxActionPerformed

    private void weight_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weight_fieldKeyTyped

        handleNumberStringJField(evt);
    }//GEN-LAST:event_weight_fieldKeyTyped

    private void vat_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vat_fieldKeyTyped
        handleNumberStringJField(evt);
        total_field.setText(getTotal());
    }//GEN-LAST:event_vat_fieldKeyTyped

    private void freight_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_freight_fieldKeyTyped
        handleNumberStringJField(evt);
        total_field.setText(getTotal());
    }//GEN-LAST:event_freight_fieldKeyTyped

    private void total_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_total_fieldKeyTyped
        handleNumberStringJField(evt);
    }//GEN-LAST:event_total_fieldKeyTyped

    private void shippingNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shippingNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shippingNumberFieldActionPerformed

    private void shippingNumberFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shippingNumberFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_shippingNumberFieldKeyTyped

    private void return_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_buttonActionPerformed
        // new MainMenu(userLoggedIn).setVisible(true);
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_return_buttonActionPerformed

    private void freight_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_freight_fieldKeyPressed
        total_field.setText(getTotal());
    }//GEN-LAST:event_freight_fieldKeyPressed

    private void handleNumberStringJField(java.awt.event.KeyEvent evt) {
        JTextField field = (JTextField) evt.getComponent();
        field.setText((field.getText() + evt.getKeyChar()).replaceAll("[^0-9,]", ""));
        evt.consume();
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
            java.util.logging.Logger.getLogger(FraktSedelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FraktSedelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FraktSedelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FraktSedelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    hattmakarna.data.Hattmakarna.connectToDB();
                } catch (InfException ex) {
                    Logger.getLogger(FraktSedelUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                new FraktSedelUI(null, "1").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adress_field;
    private javax.swing.JComboBox<String> email_combobox;
    private javax.swing.JTextField freight_field;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField lastname_field;
    private javax.swing.JTextField namn_field;
    private javax.swing.JLabel order_id;
    private javax.swing.JTable order_summary_table;
    private javax.swing.JComboBox<String> phone_combobox;
    private javax.swing.JButton return_button;
    private javax.swing.JTextField shippingNumberField;
    private javax.swing.JTextField total_field;
    private javax.swing.JTextField vat_field;
    private javax.swing.JTextField weight_field;
    // End of variables declaration//GEN-END:variables
}
