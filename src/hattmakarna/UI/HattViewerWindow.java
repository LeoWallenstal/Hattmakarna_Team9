package hattmakarna.UI;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import hattmakarna.data.User;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

    private void init() {

        this.setTitle("Hattvisare");
        btnLeft.setFocusable(false);
        btnRight.setFocusable(false);
        setFocusable(true);
        setLocationRelativeTo(null);

        requestFocusInWindow();
        loadImages();
        lblImage.setIcon(images.get(currentIndex));
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

        if (!images.isEmpty()) {
            lblImage.setIcon(images.get(currentIndex));
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

        pnlBild = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        btnRight = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        separator = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnlBild.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnlBild.setPreferredSize(new java.awt.Dimension(311, 311));

        javax.swing.GroupLayout pnlBildLayout = new javax.swing.GroupLayout(pnlBild);
        pnlBild.setLayout(pnlBildLayout);
        pnlBildLayout.setHorizontalGroup(
            pnlBildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBildLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBildLayout.setVerticalGroup(
            pnlBildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separator)
                    .addComponent(pnlBild, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlBild, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addComponent(btnLeft)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(btnRight)
                .addContainerGap(296, Short.MAX_VALUE))
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
    private javax.swing.JLabel lblImage;
    private javax.swing.JPanel pnlBild;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables
}
