/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakarna.UI;

import com.toedter.calendar.*;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.data.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import oru.inf.InfException;

/**
 *
 * @author walle
 */
public class MainMenu extends javax.swing.JFrame {

    private final User userLoggedIn;
    private final OrderRegister orderRegister;
    private final TaskRegister taskRegister;
    private LocalDate startDate;
    JMonthChooser monthChooser;
    JYearChooser yearChooser;
    private final ArrayList<JPanel> taskCells;
    private ArrayList<Task> tasks;

    /**
     * Creates new form MainMenu
     */
    public MainMenu(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
        orderRegister = new OrderRegister();
        taskRegister = new TaskRegister();
        tasks = taskRegister.getTasks();
        taskCells = new ArrayList<>();
        startDate = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        monthChooser = new JMonthChooser();
        yearChooser = new JYearChooser();

        setupYearChooserListener();
        setupMonthChooserListener();
        initComponents();
        initSchedule();
        initOrders();

        setLocationRelativeTo(null);
        lblUserName.setText("Inloggad: " + userLoggedIn.getFirstName());
        if (userLoggedIn.isAdmin()) {
            btnUsers.setVisible(true);
        } else {
            btnUsers.setVisible(false);
        }
    }

    private JPanel setupCalendarTopPanel() {
        Font font = new Font("SansSerif", Font.BOLD, 16);
        Locale locale = Locale.forLanguageTag("sv-SE");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JLabel yearLabel = new JLabel("År: ");
        yearChooser.setYear(startDate.getYear());
        yearChooser.setPreferredSize(new Dimension(60, 25));
        yearLabel.setFont(font);
        yearChooser.setFont(font);

        JLabel monthLabel = new JLabel("Månad: ");
        monthChooser.setMonth(startDate.getMonthValue() - 1);
        monthChooser.setPreferredSize(new Dimension(110, 25));
        monthChooser.setLocale(locale);
        monthLabel.setFont(font);
        monthChooser.setFont(font);

        topPanel.add(yearLabel);
        topPanel.add(yearChooser);
        topPanel.add(monthLabel);
        topPanel.add(monthChooser);
        return topPanel;
    }

    private HashMap<LocalDate, ArrayList<Task>> createTaskDateMap() {
        HashMap<LocalDate, ArrayList<Task>> taskDates = new HashMap<>();
        for (Task aTask : tasks) {
            LocalDate date = aTask.getStartDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            taskDates.computeIfAbsent(date, k -> new ArrayList<>()).add(aTask);
        }
        return taskDates;
    }

    private JPanel createCalendarDays(HashMap<LocalDate, ArrayList<Task>> taskDates) {
        JPanel daysPanel = new JPanel(new GridLayout(1, 7, 0, 0));
        daysPanel.setOpaque(false);

        for (int i = 0; i < 7; i++) {
            JPanel dayColumn = new JPanel(new GridLayout(7, 1, 0, 0));
            dayColumn.setOpaque(false);

            if (i < 6) {
                dayColumn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
            } else {
                dayColumn.setBorder(null);
            }

            LocalDate day = startDate.plusDays(i);
            ArrayList<Task> tasksForThisDay;
            tasksForThisDay = taskDates.getOrDefault(day, new ArrayList<>());
            if (taskDates.containsKey(day)) {

                System.out.println("Datumet hittat: " + day);
            } else {
                System.out.println("Inget på: " + day);
            }
            Locale locale = Locale.forLanguageTag("sv-SE");
            String weekdayName = day.getDayOfWeek().getDisplayName(TextStyle.SHORT, locale);
            String labelText = weekdayName + " " + day.getDayOfMonth();

            JLabel weekday = new JLabel(labelText, SwingConstants.CENTER);
            weekday.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            dayColumn.add(weekday);

            for (int n = 0; n < 6; n++) {
                JPanel taskPanel = new JPanel(new BorderLayout());
                taskPanel.setOpaque(false);
                if (n < 5) {
                    taskPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                }
                JLabel taskLabel = new JLabel();
                if (n < tasksForThisDay.size()) {
                    Task task = tasksForThisDay.get(n);
                    int taskId = task.getTaskId();
                    taskLabel = new JLabel(String.valueOf(taskId), SwingConstants.CENTER);
                    taskLabel.setVerticalAlignment(SwingConstants.CENTER);
                    taskPanel.setBackground(Color.GRAY);
                    taskPanel.setOpaque(true);
                }
                taskCells.add(taskPanel);
                taskPanel.add(taskLabel);
                dayColumn.add(taskPanel, BorderLayout.CENTER);
            }
            daysPanel.add(dayColumn);
        }
        return daysPanel;
    }

    private void initSchedule() {
        calendarPanel.removeAll();
        calendarPanel.setLayout(new BorderLayout());
        calendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel topPanel = setupCalendarTopPanel();
        HashMap<LocalDate, ArrayList<Task>> taskDates = createTaskDateMap();
        JPanel daysPanel = createCalendarDays(taskDates);

        calendarPanel.add(topPanel, BorderLayout.NORTH);
        calendarPanel.add(daysPanel, BorderLayout.CENTER);
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    public void initOrders() {
        ArrayList<Order> orders = orderRegister.getOrders();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        for (Order aOrder : orders) {
            if (aOrder.getStatus() != Status.BEKRÄFTAD) {
                continue;
            }

            String query = "SELECT name from hat_model WHERE model_id in"
                    + "(SELECT hat_id FROM hat WHERE order_id = " + aOrder.getOrder_id() + ")";

            try {
                ArrayList<String> hats = idb.fetchColumn(query);
                addOrders(listPanel, "Order #" + aOrder.getOrder_id(), hats);
            } catch (InfException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        scrollOrders.setViewportView(listPanel);
    }

    private void addOrders(JPanel parent, String orderTitle, ArrayList<String> items) {
        // Container for this order (vertical)
        JPanel orderContainer = new JPanel();
        orderContainer.setLayout(new BorderLayout());
        orderContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        orderContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Toggle button
        JButton toggleButton = new JButton("▶ " + orderTitle);
        toggleButton.setFocusPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setHorizontalAlignment(SwingConstants.LEFT);

        // Panel to hold item squares
        JPanel itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        itemsPanel.setVisible(false);

        for (String itemName : items) {
            JPanel item = new JPanel();
            int totalHeight = calendarPanel.getHeight();
            int totalWidth = calendarPanel.getWidth();
            int slotHeight = totalHeight / 8;
            int slotWidth = totalWidth / 7;

            Dimension taskSize = new Dimension(slotWidth-1, slotHeight-1);
            item.setPreferredSize(taskSize);
            item.setBackground(Color.LIGHT_GRAY);
            item.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            item.add(new JLabel(itemName));
            makeDraggable(item);
            itemsPanel.add(item);
        }

        // Toggle logic
        toggleButton.addActionListener(e -> {
            boolean visible = itemsPanel.isVisible();
            itemsPanel.setVisible(!visible);
            toggleButton.setText((visible ? "▶ " : "▼ ") + orderTitle);
            parent.revalidate();
            parent.repaint();
        });

        // Add components to order container
        orderContainer.add(toggleButton, BorderLayout.NORTH);
        orderContainer.add(itemsPanel, BorderLayout.CENTER);

        // Add this order to the main list panel
        parent.add(orderContainer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSignOut = new javax.swing.JButton();
        lblCompanyName = new javax.swing.JLabel();
        btnCustomer = new javax.swing.JButton();
        btnOrder = new javax.swing.JButton();
        btnMaterial = new javax.swing.JButton();
        btnHat = new javax.swing.JButton();
        lblUserName = new javax.swing.JLabel();
        btnUsers = new javax.swing.JButton();
        calendarPanel = new javax.swing.JPanel();
        scrollOrders = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        btnSignOut.setText("Logga ut");
        btnSignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignOutActionPerformed(evt);
            }
        });

        lblCompanyName.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblCompanyName.setText("Otto & Judith's Hattmakeri");

        btnCustomer.setText("Kunder");
        btnCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerActionPerformed(evt);
            }
        });

        btnOrder.setText("Order");
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        btnMaterial.setText("Material");
        btnMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterialActionPerformed(evt);
            }
        });

        btnHat.setText("Hattar");
        btnHat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHatActionPerformed(evt);
            }
        });

        lblUserName.setText("Inloggad: ");

        btnUsers.setText("Hantera anställda");
        btnUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsersActionPerformed(evt);
            }
        });

        calendarPanel.setBackground(new java.awt.Color(255, 255, 255));
        calendarPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        calendarPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                calendarPanelMouseWheelMoved(evt);
            }
        });
        calendarPanel.setLayout(new java.awt.BorderLayout());

        jButton1.setText("<");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(">");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSignOut)
                            .addComponent(lblUserName)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(lblCompanyName)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUsers)
                        .addGap(20, 20, 20)
                        .addComponent(btnHat, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(569, 569, 569)
                                .addComponent(jButton2))
                            .addComponent(calendarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCustomer)
                    .addComponent(btnOrder)
                    .addComponent(btnHat)
                    .addComponent(btnMaterial)
                    .addComponent(btnUsers))
                .addGap(18, 18, 18)
                .addComponent(lblUserName)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollOrders, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(calendarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSignOut)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignOutActionPerformed
        // TODO add your handling code here:
        new LogInWindow(idb).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSignOutActionPerformed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        new OrderOverviewWindow(userLoggedIn).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnOrderActionPerformed

    private void btnCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerActionPerformed
        // TODO add your handling code here:
        new CustomerInformationWindow().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnCustomerActionPerformed

    private void btnMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterialActionPerformed
        // TODO add your handling code here:
        new MaterialOrderWindow(userLoggedIn).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMaterialActionPerformed

    private void btnHatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHatActionPerformed
        // TODO add your handling code here:
        new HattWindow().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnHatActionPerformed

    private void btnUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsersActionPerformed
        this.setVisible(false);
        new EmployeesWindow(userLoggedIn).setVisible(true);
    }//GEN-LAST:event_btnUsersActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        startDate = startDate.minusDays(1);
        initSchedule();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        startDate = startDate.plusDays(1);
        initSchedule();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void calendarPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_calendarPanelMouseWheelMoved
        // TODO add your handling code here:
        int direction = evt.getWheelRotation();
        startDate = startDate.minusDays(direction);
        initSchedule();
        calendarPanel.setBackground(Color.LIGHT_GRAY);
        Timer timer = new Timer(150, e -> calendarPanel.setBackground(Color.WHITE));
        timer.setRepeats(false);
        timer.start();

    }//GEN-LAST:event_calendarPanelMouseWheelMoved

    private void makeDraggable(JPanel panel) {
        Point initialClick = new Point();

        panel.addMouseListener(new MouseAdapter() {
            JPanel ghostPanel;
            JFrame frame;

            @Override
            public void mousePressed(MouseEvent e) {
                initialClick.setLocation(e.getPoint());

                frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                JComponent glassPane = (JComponent) frame.getGlassPane();
                glassPane.setLayout(null);
                glassPane.setVisible(true);

                ghostPanel = new JPanel();
                ghostPanel.setBackground(panel.getBackground());
                ghostPanel.setSize(panel.getSize());
                ghostPanel.setBorder(panel.getBorder());
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof JLabel label) {
                        ghostPanel.add(new JLabel(label.getText()));
                    }
                }

                Point panelPosInGlass = SwingUtilities.convertPoint(panel.getParent(), panel.getLocation(), frame.getGlassPane());

                ghostPanel.setBounds(panelPosInGlass.x, panelPosInGlass.y, panel.getWidth(), panel.getHeight());
                glassPane.add(ghostPanel);
                glassPane.repaint();

                panel.setVisible(false);

                frame.setCursor(Cursor.HAND_CURSOR);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (ghostPanel == null) {
                    return;
                }
                Point ghostLocation = ghostPanel.getLocationOnScreen();
                Point calendarLocationOnScreen = calendarPanel.getLocationOnScreen();
                Dimension calendarSize = calendarPanel.getSize();
                Rectangle calendarBounds = new Rectangle(calendarLocationOnScreen, calendarSize);

                JComponent glassPane = (JComponent) frame.getGlassPane();

                if (calendarBounds.contains(ghostLocation)) {
                    Point ghostCenter = new Point(
                            ghostPanel.getX() + ghostPanel.getWidth() / 2,
                            ghostPanel.getY() + ghostPanel.getHeight() / 2
                    );

                    JPanel closestCell = null;
                    double closestDistance = Double.MAX_VALUE;

                    for (JPanel cell : taskCells) {
                        Point cellOnGlass = SwingUtilities.convertPoint(
                                cell.getParent(), cell.getLocation(), glassPane
                        );

                        Rectangle cellBounds = new Rectangle(cellOnGlass, cell.getSize());
                        Point cellCenter = new Point(
                                cellBounds.x + cellBounds.width / 2,
                                cellBounds.y + cellBounds.height / 2
                        );

                        double distance = ghostCenter.distance(cellCenter);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestCell = cell;
                        }
                    }

                    if (closestCell != null) {
                        Point cellPos = SwingUtilities.convertPoint(
                                closestCell.getParent(), closestCell.getLocation(), glassPane
                        );

                        ghostPanel.setLocation(cellPos);

                        closestCell.removeAll();
                        closestCell.add(panel);
                        panel.setVisible(true);
                        closestCell.revalidate();
                        closestCell.repaint();
                    }
                }

                glassPane.remove(ghostPanel);
                glassPane.repaint();
                glassPane.setVisible(false);
                ghostPanel = null;
                frame.setCursor(Cursor.DEFAULT_CURSOR);
                panel.setVisible(true);
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                JComponent glassPane = (JComponent) frame.getGlassPane();

                Point cursor = SwingUtilities.convertPoint(panel, e.getPoint(), glassPane);

                for (Component comp : glassPane.getComponents()) {
                    if (comp instanceof JPanel ghost) {
                        ghost.setLocation(cursor.x - initialClick.x, cursor.y - initialClick.y);
                    }
                }

                glassPane.repaint();
            }
        });

    }

    private void setupYearChooserListener() {
        yearChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if ("year".equals(evt.getPropertyName())) {
                    int selectedYear = yearChooser.getYear();
                    if (selectedYear != startDate.getYear()) {
                        startDate = startDate.withYear(selectedYear);
                        initSchedule();
                    }

                }
            }
        });
    }

    private void setupMonthChooserListener() {
        monthChooser.addPropertyChangeListener("month", new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                int selectedMonth = monthChooser.getMonth(); // 0-indexed
                System.out.println("Month changed to: " + (selectedMonth + 1));
                startDate = startDate.withMonth(selectedMonth + 1);
                initSchedule();
            }
        });
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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomer;
    private javax.swing.JButton btnHat;
    private javax.swing.JButton btnMaterial;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnSignOut;
    private javax.swing.JButton btnUsers;
    private javax.swing.JPanel calendarPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lblCompanyName;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JScrollPane scrollOrders;
    // End of variables declaration//GEN-END:variables
}
