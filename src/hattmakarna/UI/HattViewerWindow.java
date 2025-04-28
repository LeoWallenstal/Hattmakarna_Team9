package hattmakarna.UI;

import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.data.Hat;
import hattmakarna.data.Specification;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import hattmakarna.data.User;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import oru.inf.InfException;

public class HattViewerWindow extends javax.swing.JFrame {

    private ArrayList<ImageIcon> images;
    private int currentIndex = 0;
    private int hatID;

    public HattViewerWindow(int hatID) {

        this.hatID = hatID;
        images = new ArrayList<>();
        initComponents();
        init();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (images.isEmpty()) {
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    currentIndex = (currentIndex + 1) % images.size();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    currentIndex = (currentIndex - 1 + images.size()) % images.size();
                }

                lblImage.setIcon(images.get(currentIndex));
            }
        });

    }

    private String fetchHatDescriptionFromDB() {
        String descriptionSQLQuery = "SELECT beskrivning from hat_spec where hat_id = " + hatID;
        String description = "";

        try {
            description = idb.fetchSingle(descriptionSQLQuery);
        } catch (InfException ex) {
            Logger.getLogger(HattViewerWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        return description;
    }

    private void displayDescription() {

        String description = fetchHatDescriptionFromDB();

        if (description == null) {
            txtDescription.setText("Beskrivning saknas för hatten.");
        }
        txtDescription.setText(description);

    }

    private void init() {

        this.setTitle("Hattvisare");
        btnLeft.setFocusable(false);
        btnRight.setFocusable(false);
        setFocusable(true);
        setLocationRelativeTo(null);

        requestFocusInWindow();
        loadImages();
        displayDescription();
        if (!images.isEmpty()) {
            lblImage.setIcon(images.get(currentIndex));
        }

    }

    private void loadImages() {
        String imageDir = "images/hattextra" + hatID;
        File folder = new File(imageDir);
        File[] files = folder.listFiles((dir, name)
                -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

        if (files != null) {
            for (File file : files) {
                images.add(scaleImage(file.getAbsolutePath()));
            }
        }

        String skissFileName = "specSkiss-hat-" + hatID + ".png";
        File specialSkiss = new File("images/" + skissFileName);

        if (specialSkiss.exists()) {
            images.add(scaleImage(specialSkiss.getAbsolutePath()));
        } else {
            System.out.println("Special sketch not found for hatID " + hatID);
        }

        String specialBildFileName = "specBild-hat-" + hatID + ".png";
        File specialFile = new File("images/" + specialBildFileName);

        if (specialFile.exists()) {
            images.add(scaleImage(specialFile.getAbsolutePath()));
        } else {
            System.out.println("Special hat not found for hatID " + hatID);
        }

        if (!images.isEmpty()) {
            lblImage.setIcon(images.get(currentIndex));

        } else {
        btnLeft.setVisible(false);
        btnRight.setVisible(false);
        lblImage.setText("Det finns inga bilder anknytna till denna hatt");
        }
    }

    private ImageIcon scaleImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image scaled = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRight = new javax.swing.JButton();
        separator = new javax.swing.JSeparator();
        btnLeft = new javax.swing.JButton();
        lblDescription = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        lblHeader = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        btnRight.setText(">");
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnLeft.setText("<");
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        lblDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescription.setText("Beskrivning:");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        txtDescription.setFocusable(false);
        scrollPane.setViewportView(txtDescription);

        lblHeader.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHeader.setText("Hattvisare");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnLeft)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRight))))
                    .addComponent(lblHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDescription)
                        .addGap(15, 15, 15)
                        .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeader)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRight)
                            .addComponent(btnLeft)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        if (images.isEmpty()) {
            return;
        }
        currentIndex = (currentIndex + 1) % images.size();
        lblImage.setIcon(images.get(currentIndex));
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        if (images.isEmpty()) {
            return;
        }
        currentIndex = (currentIndex - 1 + images.size()) % images.size();
        lblImage.setIcon(images.get(currentIndex));
    }//GEN-LAST:event_btnLeftActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(HattViewerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(HattViewerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(HattViewerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(HattViewerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new HattViewerWindow().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblImage;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSeparator separator;
    private javax.swing.JTextArea txtDescription;
    // End of variables declaration//GEN-END:variables
}
