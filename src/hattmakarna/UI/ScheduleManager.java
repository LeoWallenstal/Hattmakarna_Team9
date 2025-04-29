/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.UI;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.data.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;
import oru.inf.InfException;

/**
 *
 * @author leowa
 */
public class ScheduleManager {

    private final User userLoggedIn;
    private final JPanel calendarPanel;
    private final JScrollPane scrollOrders;
    private LocalDate startDate;
    private final TaskRegister taskRegister;
    private ArrayList<Task> tasks;
    private OrderRegister orderRegister;
    private final ArrayList<JPanel> emptyCells;
    private JPanel highlightedCell = null;
    private final ArrayList<JPanel> highlightedCells = new ArrayList<>();
    private Set<Integer> expandedOrders = new HashSet<>();

    private final Color doneColor;
    private final Color underWorkColor;
    private final Color readyColor;
    private final Color expressColor;

    public ScheduleManager(User userLoggedIn, JPanel calendarPanel, JScrollPane scrollOrders) {
        this.userLoggedIn = userLoggedIn;
        this.calendarPanel = calendarPanel;
        this.scrollOrders = scrollOrders;
        startDate = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        taskRegister = new TaskRegister();
        tasks = taskRegister.getUsersOngoingTasks(userLoggedIn.getID());
        orderRegister = new OrderRegister();
        emptyCells = new ArrayList<>();

        doneColor = new Color(0xA8C686);       // Soft green
        underWorkColor = new Color(0xF2D388);  // Warm yellow
        readyColor = new Color(0xA2C2E2);      // Gentle blue
        expressColor = new Color(0xE27D60);    // Warm muted red

        initSchedule();
        buildOrdersPanel(false);

    }

    public void refreshSchedule() {
        taskRegister.refreshUsersOngoingTasks(userLoggedIn.getID());
        HashMap<LocalDate, ArrayList<Task>> taskDates = createTaskDateMap();

        for (Component comp : calendarPanel.getComponents()) {
            Object constraint = ((BorderLayout) calendarPanel.getLayout()).getConstraints(comp);
            if (BorderLayout.CENTER.equals(constraint)) {
                calendarPanel.remove(comp);
                break;
            }
        }

        JPanel newDaysPanel = createCalendarDays(taskDates);
        calendarPanel.add(newDaysPanel, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {
            ensureGridAlignment((JPanel) calendarPanel.getComponent(0));
        });

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void initSchedule() {
        calendarPanel.removeAll();
        calendarPanel.setLayout(new BorderLayout());
        calendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel topPanel = setupCalendarTopPanel();
        HashMap<LocalDate, ArrayList<Task>> taskDates = createTaskDateMap();
        JPanel daysPanel = createCalendarDays(taskDates);

        calendarPanel.add(topPanel, BorderLayout.NORTH);

        SwingUtilities.invokeLater(() -> {
            ensureGridAlignment(topPanel);
        });

        calendarPanel.add(daysPanel, BorderLayout.CENTER);
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    /*
 * !!! OBS !!!
 * För att denna metod ska fungera korrekt måste "Horizontal Resizable" och "Vertical Resizable" vara ikryssade
 * i panelens egenskaper (Properties -> Layout) i GUI Builder.
 * 
 * Jag har försökt återskapa detta via kod men inte hittat ett bra sätt att göra det på.
 * 
 * Denna metod syftar till att justera storleken på kalenderns JPanel (calendarPanel) för att:
 * - Undvika fula mellanrum (gaps) mellan panelens yttre border och de inre cellerna (tasks).
 * - Säkerställa att cellerna passar perfekt utan pixel-avvikelser, genom att anpassa till 7-kolumnersrutnätet.
     */
    private void ensureGridAlignment(JPanel topPanel) {

        int calendarHeight = calendarPanel.getHeight();
        int calendarWidth = calendarPanel.getWidth();
        int topHeight = topPanel.getHeight();

        Insets borderInsets = calendarPanel.getBorder().getBorderInsets(calendarPanel);
        int availableHeight = calendarHeight - topHeight - borderInsets.top - borderInsets.bottom;
        int availableWidth = calendarWidth - borderInsets.left - borderInsets.right;

        int remainderHeight = availableHeight % 7;
        if (remainderHeight != 0) {
            availableHeight += (7 - remainderHeight);
        }

        int remainderWidth = availableWidth % 7;
        if (remainderWidth != 0) {
            availableWidth += (7 - remainderWidth);
        }

        int preferredHeight = availableHeight + topHeight + borderInsets.top + borderInsets.bottom;
        int preferredWidth = availableWidth + borderInsets.left + borderInsets.right;
        Dimension preferred = new Dimension(preferredWidth, preferredHeight);

        calendarPanel.setMinimumSize(preferred);
        calendarPanel.setPreferredSize(preferred);
        calendarPanel.setMaximumSize(preferred);

        preferred = new Dimension(scrollOrders.getWidth(), preferredHeight);
        scrollOrders.setMinimumSize(preferred);
        scrollOrders.setPreferredSize(preferred);
        scrollOrders.setMaximumSize(preferred);
    }

    private JPanel setupCalendarTopPanel() {
        Font font = new Font("SansSerif", Font.BOLD, 16);
        Locale locale = Locale.forLanguageTag("sv-SE");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JYearChooser yearChooser = new JYearChooser();
        yearChooser.setYear(startDate.getYear());
        yearChooser.setPreferredSize(new Dimension(70, 25));
        yearChooser.setFont(font);

        JMonthChooser monthChooser = new JMonthChooser(false);
        JComboBox comboBox = (JComboBox) monthChooser.getComboBox();
        comboBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        comboBox.setPreferredSize(new Dimension(120, 25));
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setBorder(BorderFactory.createEmptyBorder());
                return button;
            }
        });

        monthChooser.setMonth(startDate.getMonthValue() - 1);
        monthChooser.setLocale(locale);
        monthChooser.setFont(font);

        JButton backButton = new JButton("◀");
        JButton forwardButton = new JButton("▶");

        setupNavigationListeners(backButton, forwardButton, monthChooser, yearChooser);

        topPanel.add(backButton);
        topPanel.add(Box.createHorizontalStrut(170));
        topPanel.add(yearChooser);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(monthChooser);
        topPanel.add(Box.createHorizontalStrut(170));
        topPanel.add(forwardButton);

        return topPanel;
    }

    private HashMap<LocalDate, ArrayList<Task>> createTaskDateMap() {
        HashMap<LocalDate, ArrayList<Task>> taskDates = new HashMap<>();
        if (tasks != null) {
            for (Task aTask : tasks) {
                LocalDate date = aTask.getStartDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                taskDates.computeIfAbsent(date, k -> new ArrayList<>()).add(aTask);
            }
        }
        return taskDates;
    }

    private JPanel createCalendarDays(HashMap<LocalDate, ArrayList<Task>> taskDates) {
        JPanel daysPanel = new JPanel(new GridLayout(1, 7, 0, 0));
        daysPanel.setOpaque(false);
        emptyCells.clear();

        for (int i = 0; i < 7; i++) {
            JPanel dayColumn = new JPanel(new GridLayout(7, 1, 0, 0));
            dayColumn.setOpaque(false);

            if (i < 6) {
                dayColumn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
            }

            LocalDate day = startDate.plusDays(i);
            ArrayList<Task> tasksForThisDay = taskDates.getOrDefault(day, new ArrayList<>());
            Locale locale = Locale.forLanguageTag("sv-SE");
            String weekdayName = day.getDayOfWeek().getDisplayName(TextStyle.SHORT, locale);
            String labelText = weekdayName + " " + day.getDayOfMonth() + "/" + day.getMonthValue();

            JLabel weekday = new JLabel(labelText, SwingConstants.CENTER);
            weekday.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            dayColumn.add(weekday);

            for (int n = 0; n < 6; n++) {
                JPanel cell = new JPanel(new BorderLayout());
                cell.setOpaque(false);
                if (n < 5) {
                    cell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                }

                if (n < tasksForThisDay.size()) {
                    Task task = tasksForThisDay.get(n);
                    JPanel taskPanel = createTaskPanel(task.getModelName(), String.valueOf(task.getHatId()), task.isFastProduction());

                    JLabel orderLabel = new JLabel("Order #" + task.getOrderId(), SwingConstants.CENTER);
                    orderLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
                    taskPanel.add(orderLabel, BorderLayout.SOUTH);

                    taskPanel.putClientProperty("task", task);
                    makeDraggable(taskPanel);
                    cell.add(taskPanel, BorderLayout.CENTER);
                } else {
                    emptyCells.add(cell);
                }
                dayColumn.add(cell);
            }

            daysPanel.add(dayColumn);
        }
        return daysPanel;
    }

    private JPanel createTaskPanel(String modelName, String hatId, boolean isFastProduction) {
        JPanel panel = new JPanel(new BorderLayout());
        if(isFastProduction)
            panel.setBackground(expressColor);
        else
            panel.setBackground(underWorkColor);

        JLabel nameLabel = new JLabel(modelName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(nameLabel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                String sqlQuery = """ 
                    SELECT hs.spec_id
                    FROM hat_spec hs
                    JOIN hat h ON hs.hat_id = h.hat_id                         
                    WHERE hs.hat_id =""" + hatId;
                try {
                    String specId = idb.fetchSingle(sqlQuery);
                    if (specId != null) {
                        Specification spec = new Specification(specId);
                        String imagePath = spec.getImagePath();
                        String toolTip = "<html>";
                        if (imagePath != null) {
                            imagePath = imagePath.trim();
                            URL imageUrl = getClass().getResource(imagePath);
                            if (imageUrl != null) {
                                toolTip += "<img src='" + imageUrl.toExternalForm() + "'><br>";
                            } else {
                                System.err.println("Kunde inte hitta bilden på: ");
                            }

                        }
                        String desc = "";
                        desc = spec.getDescription();
                        if (desc != null && !desc.isBlank()) {
                            toolTip += desc;
                        } else {
                            toolTip += "Finns ingen beskrivning...";
                        }

                        toolTip += "<br> </html>";
                        panel.setToolTipText(toolTip);
                    }
                } catch (InfException ex) {
                    Logger.getLogger(ScheduleManager.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        return panel;
    }

    public void buildOrdersPanel(boolean keepOrdersOpen) {
        if (keepOrdersOpen) {
            expandedOrders.clear();

            Component view = scrollOrders.getViewport().getView();
            if (view instanceof JPanel listPanel) {
                for (Component orderContainer : listPanel.getComponents()) {
                    if (orderContainer instanceof JPanel containerPanel) {
                        Component[] containerChildren = containerPanel.getComponents();
                        if (containerChildren.length >= 2) {
                            JPanel headerPanel = (JPanel) containerChildren[0]; // Header
                            JPanel itemsPanel = (JPanel) containerChildren[1];  // Items

                            // Find toggleButton inside headerPanel
                            for (Component comp : headerPanel.getComponents()) {
                                if (comp instanceof JButton toggleButton) {
                                    Object idProp = toggleButton.getClientProperty("order_id");
                                    if (idProp instanceof Integer id) {
                                        if (itemsPanel.isVisible()) {
                                            expandedOrders.add(id);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        orderRegister = new OrderRegister();
        ArrayList<Order> orders = orderRegister.getOrders();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        for (Order aOrder : orders) {
            if (aOrder.getStatus() != Status.BEKRÄFTAD && aOrder.getStatus() != Status.PÅGÅENDE) {
                continue;
            }

            String query = """
                SELECT h.hat_id, m.name, u.first_name, u.last_name, t.task_id, o.isFastProduction,
                    CASE WHEN t.task_id IS NOT NULL THEN 1 ELSE 0 END AS in_task,
                    CASE WHEN t.status = 'KLAR' OR 
                           h.hat_id not in (select hat_id from hat_spec) THEN 1 ELSE 0 END AS done
                FROM hat h
                JOIN hat_model m ON h.model_id = m.model_id
                JOIN sales_order o ON h.order_id = o.order_id
                LEFT JOIN task t ON t.hat_id = h.hat_id
                LEFT JOIN user u ON t.user_id = u.user_id
                WHERE h.order_id = """ + aOrder.getOrderId();

            try {
                ArrayList<HashMap<String, String>> hats = idb.fetchRows(query);
                addOrders(listPanel, aOrder.getOrderId(), hats);

            } catch (InfException ex) {
                Logger.getLogger(ScheduleManager.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        scrollOrders.setViewportView(listPanel);
        scrollOrders.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    }

    private void addTaskListener(JPanel taskPanel) {
        taskPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                Object[] options = {"Klarmarkera", "Ta tillbaka", "Avbryt"};
                int result = JOptionPane.showOptionDialog(
                        calendarPanel.getParent(), "Vad vill du göra med hatten?", "Bekräfta",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                Task task = (Task) taskPanel.getClientProperty("task");
                if (result == 0) {

                    if (task != null) {
                        task.setStatus(TaskStatus.KLAR);
                        task.save();
                        refreshSchedule();
                        buildOrdersPanel(true);
                    }
                } else if (result == 1) {
                    if (task != null) {
                        try {
                            int hatId = task.getHatId();
                            String query = "SELECT amount, material_id FROM hat_material WHERE hat_id = " + hatId;
                            ArrayList<HashMap<String, String>> materialList = idb.fetchRows(query);
                            for (HashMap<String, String> row : materialList) {
                                query = "UPDATE material SET amount = amount + " + row.get("amount")
                                        + " WHERE material_id = " + row.get("material_id");
                                idb.update(query);
                            }
                            task.delete();
                            refreshSchedule();
                            buildOrdersPanel(true);
                        } catch (InfException ex) {
                            Logger.getLogger(ScheduleManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        });

    }

    private void addOrders(JPanel parent, int orderId, ArrayList<HashMap<String, String>> hats) {
        if (!hats.isEmpty()) {
            JPanel orderContainer = new JPanel();
            orderContainer.setLayout(new BoxLayout(orderContainer, BoxLayout.Y_AXIS));
            orderContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
            Border padding = BorderFactory.createEmptyBorder(0, 0, 20, 100);
            Border bottomLine = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
            orderContainer.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));

            String orderTitle = "Order #" + orderId;
            JButton toggleButton = new JButton("▶ " + orderTitle);
            toggleButton.setFocusPainted(false);
            toggleButton.setContentAreaFilled(false);
            toggleButton.setBorderPainted(false);
            toggleButton.setHorizontalAlignment(SwingConstants.LEFT);
            toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton openOrderButton = new JButton();

            openOrderButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/info.png")));

            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
            itemsPanel.setVisible(false);

            // Set initially based on expandedOrders
            if (expandedOrders.contains(orderId)) {
                itemsPanel.setVisible(true);
                toggleButton.setText("▼ " + orderTitle);
            }

            JPanel currentRow = null;
            int i = 0;
            for (HashMap<String, String> hat : hats) {
                String hatId = hat.get("hat_id");
                String hatName = hat.get("name");
                String userName = hat.get("first_name") + " " + hat.get("last_name");
                boolean isAssigned = "1".equals(hat.get("in_task"));
                boolean isDone = "1".equals(hat.get("done"));
                boolean isFastProduction = "1".equals(hat.get("isFastProduction"));
                String taskId = hat.get("task_id");

                JPanel item = createTaskPanel(hatName, hatId, isFastProduction);
                int totalHeight = calendarPanel.getHeight();
                int totalWidth = calendarPanel.getWidth();
                int slotHeight = totalHeight / 8;
                int slotWidth = totalWidth / 7;

                if (i % 2 == 0) {
                    currentRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));

                    currentRow.setPreferredSize(new Dimension(Integer.MAX_VALUE, slotHeight + 3));
                    currentRow.setAlignmentX(Component.LEFT_ALIGNMENT);
                    currentRow.setMaximumSize(currentRow.getPreferredSize());
                    itemsPanel.add(currentRow);
                }

                Dimension taskSize = new Dimension(slotWidth - 1, slotHeight - 1);
                item.setPreferredSize(taskSize);
                if (isDone) {
                    item.setBackground(doneColor);
                } else if (isAssigned) {
                    JLabel workerLabel = new JLabel(userName, SwingConstants.CENTER);
                    workerLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
                    item.add(workerLabel, BorderLayout.SOUTH);
                    item.setBackground(underWorkColor);
                    Task task = new Task(taskId);
                    item.putClientProperty("task", task);
                    addTaskListener(item);
                } else {
                    item.setBackground(readyColor);
                    makeDraggable(item);
                }

                if (!isDone && isFastProduction) {
                    item.setBackground(expressColor);
                }

                item.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                item.putClientProperty("hat_id", hatId);

                currentRow.add(item);
                i++;
            }

            toggleButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    calendarPanel.getParent().setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    calendarPanel.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    boolean visible = itemsPanel.isVisible();
                    itemsPanel.setVisible(!visible);
                    toggleButton.setText((visible ? "▶ " : "▼ ") + orderTitle);
                    parent.revalidate();
                    parent.repaint();
                }
            });

            openOrderButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    calendarPanel.getParent().setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    calendarPanel.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    new OrderInfoWindow(ScheduleManager.this, new Order(String.valueOf(orderId))).setVisible(true);
                }
            });

            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
            headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            toggleButton.setFocusable(false);
            toggleButton.putClientProperty("order_id", orderId);
            openOrderButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            openOrderButton.setFocusable(false);
            openOrderButton.setContentAreaFilled(false);

            headerPanel.add(openOrderButton);
            headerPanel.add(toggleButton);

            makeOrderDraggable(orderContainer);
            orderContainer.add(headerPanel);
            orderContainer.add(itemsPanel);

            parent.add(orderContainer);
        }
    }

    private void makeOrderDraggable(JPanel orderContainer) {
        Point initialClick = new Point();
        final JPanel[] tempPanel = new JPanel[1];

        orderContainer.addMouseListener(new MouseAdapter() {
            JFrame frame;

            @Override
            public void mousePressed(MouseEvent e) {

                frame = (JFrame) SwingUtilities.getWindowAncestor(orderContainer);

                JPanel orderTasks = new JPanel();
                orderTasks.setLayout(new BoxLayout(orderTasks, BoxLayout.Y_AXIS));

                for (Component comp : orderContainer.getComponents()) {
                    if (comp instanceof JPanel itemsPanel) {
                        for (Component row : itemsPanel.getComponents()) {
                            if (row instanceof JPanel rowPanel) {
                                for (Component item : rowPanel.getComponents()) {
                                    if (item instanceof JPanel itemPanel) {
                                        if (itemPanel.getBackground() == readyColor) {
                                            JPanel cloned = clonePanel(itemPanel, true, itemPanel.getBackground());
                                            cloned.setMaximumSize(itemPanel.getPreferredSize());
                                            cloned.setPreferredSize(itemPanel.getPreferredSize());
                                            orderTasks.add(cloned);
                                            orderTasks.add(Box.createVerticalStrut(1));
                                        }

                                    } else {
                                        orderTasks.add(new JLabel("Other comp: " + item.getClass().getSimpleName()));

                                    }
                                }
                            }
                        }
                    }
                }

                JComponent glassPane = (JComponent) frame.getGlassPane();
                glassPane.setLayout(null);
                glassPane.setVisible(true);

                Point cursor = SwingUtilities.convertPoint(orderContainer, e.getPoint(), glassPane);
                orderTasks.setLocation(cursor);
                orderTasks.setSize(orderTasks.getPreferredSize());
                tempPanel[0] = orderTasks;
                glassPane.add(tempPanel[0]);
                glassPane.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point droppedLocation = new Point();
                droppedLocation.setLocation(e.getLocationOnScreen());
                frame = (JFrame) SwingUtilities.getWindowAncestor(orderContainer);
                JComponent glassPane = (JComponent) frame.getGlassPane();

                for (Component comp : tempPanel[0].getComponents()) {
                    if (!(comp instanceof JPanel panel)) {
                        continue;
                    }
                    Rectangle tempBounds = panel.getBounds();

                    tempBounds.setLocation(panel.getLocationOnScreen());

                    JPanel bestCell = findBestEmptyCell(tempBounds);

                    if (bestCell
                            != null) {
                        int dropX = bestCell.getLocationOnScreen().x;
                        int calendarX = calendarPanel.getLocationOnScreen().x;
                        int totalWidth = calendarPanel.getWidth();
                        int columnWidth = totalWidth / 7;
                        int columnIndex = Math.max(0, Math.min(6, (dropX - calendarX) / columnWidth));
                        LocalDate dropDate = startDate.plusDays(columnIndex);
                        moveTask(panel, bestCell, null, dropDate);
                    }
                }

                refreshSchedule();
                glassPane.removeAll();
                glassPane.repaint();
                glassPane.setVisible(false);
            }
        });

        orderContainer.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(orderContainer);
                JComponent glassPane = (JComponent) frame.getGlassPane();
                Point cursor = SwingUtilities.convertPoint(orderContainer, e.getPoint(), glassPane);

                if (tempPanel[0] != null) {
                    tempPanel[0].setLocation(cursor.x - initialClick.x, cursor.y - initialClick.y);

                    // Clear previous highlights
                    for (JPanel prev : highlightedCells) {
                        resetHighlightedCellBorder(prev);
                    }
                    highlightedCells.clear();

                    for (Component comp : tempPanel[0].getComponents()) {
                        if (!(comp instanceof JPanel test)) {
                            continue;
                        }

                        Rectangle tempBounds = test.getBounds();
                        Point testLocationOnGlass = SwingUtilities.convertPoint(test.getParent(), test.getLocation(), calendarPanel);
                        tempBounds.setLocation(calendarPanel.getLocationOnScreen().x + testLocationOnGlass.x,
                                calendarPanel.getLocationOnScreen().y + testLocationOnGlass.y);

                        JPanel currentBestCell = findBestEmptyCell(tempBounds);

                        if (currentBestCell != null && !highlightedCells.contains(currentBestCell)) {
                            currentBestCell.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
                            highlightedCells.add(currentBestCell);
                        }
                    }
                }

                glassPane.repaint();
            }

        });
    }

    public void makeDraggable(JPanel panel) {
        Point initialClick = new Point();
        final JPanel[] originCell = new JPanel[1];
        final JPanel[] tempPanel = new JPanel[1];

        panel.addMouseListener(new MouseAdapter() {
            JFrame frame;

            @Override
            public void mousePressed(MouseEvent e) {
                initialClick.setLocation(e.getPoint());
                frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                Point calendarLocationOnScreen = calendarPanel.getLocationOnScreen();
                Dimension calendarSize = calendarPanel.getSize();
                Rectangle calendarBounds = new Rectangle(calendarLocationOnScreen, calendarSize);
                if (calendarBounds.contains(panel.getLocationOnScreen())) {
                    originCell[0] = (JPanel) panel.getParent();
                }
                tempPanel[0] = clonePanel(panel, false, panel.getBackground());
                tempPanel[0].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                Point panelPosInGlass = SwingUtilities.convertPoint(panel.getParent(), panel.getLocation(), frame.getGlassPane());

                JComponent glassPane = (JComponent) frame.getGlassPane();
                glassPane.setLayout(null);
                glassPane.setVisible(true);
                tempPanel[0].setBounds(panelPosInGlass.x, panelPosInGlass.y, panel.getWidth(), panel.getHeight());
                glassPane.add(tempPanel[0]);
                glassPane.repaint();

                panel.setOpaque(false);
                frame.setCursor(Cursor.HAND_CURSOR);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (tempPanel[0] == null) {
                    return;
                }
                Point droppedLocation = new Point();
                droppedLocation.setLocation(e.getLocationOnScreen());
                frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                Point scrollLocationOnScreen = scrollOrders.getLocationOnScreen();
                Dimension scrollSize = scrollOrders.getSize();
                Rectangle scrollBounds = new Rectangle(scrollLocationOnScreen, scrollSize);
                if (scrollBounds.contains(droppedLocation)) {
                    Task task = (Task) panel.getClientProperty("task");
                    if (task != null) {
                        try {
                            int hatId = task.getHatId();
                            String query = "SELECT amount, material_id FROM hat_material WHERE hat_id = " + hatId;
                            ArrayList<HashMap<String, String>> materialList = idb.fetchRows(query);
                            for (HashMap<String, String> row : materialList) {
                                query = "UPDATE material SET amount = amount + " + row.get("amount")
                                        + " WHERE material_id = " + row.get("material_id");
                                idb.update(query);
                            }
                            task.delete();
                            buildOrdersPanel(true);
                        } catch (InfException ex) {
                            Logger.getLogger(ScheduleManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

                JComponent glassPane = (JComponent) frame.getGlassPane();
                Rectangle tempBounds = tempPanel[0].getBounds();

                tempBounds.setLocation(tempPanel[0].getLocationOnScreen());

                JPanel bestCell = findBestEmptyCell(tempBounds);

                if (bestCell
                        != null) {
                    int dropX = bestCell.getLocationOnScreen().x;
                    int calendarX = calendarPanel.getLocationOnScreen().x;
                    int totalWidth = calendarPanel.getWidth();
                    int columnWidth = totalWidth / 7;
                    int columnIndex = Math.max(0, Math.min(6, (dropX - calendarX) / columnWidth));
                    LocalDate dropDate = startDate.plusDays(columnIndex);
                    moveTask(panel, bestCell, originCell[0], dropDate);
                }

                refreshSchedule();

                glassPane.removeAll();

                glassPane.repaint();

                glassPane.setVisible(
                        false);
                tempPanel[0] = null;
                frame.setCursor(Cursor.DEFAULT_CURSOR);

                panel.setOpaque(true);
                panel.setBackground(readyColor);

                for (Component comp
                        : panel.getComponents()) {
                    if (comp instanceof JLabel) {
                        comp.setVisible(true);
                    }
                }

                if (highlightedCell
                        != null) {
                    resetHighlightedCellBorder(highlightedCell);
                    highlightedCell = null;
                }

            }
        }
        );

        panel.addMouseMotionListener(
                new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e
            ) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                JComponent glassPane = (JComponent) frame.getGlassPane();
                Point cursor = SwingUtilities.convertPoint(panel, e.getPoint(), glassPane);

                if (tempPanel[0] != null) {
                    tempPanel[0].setLocation(cursor.x - initialClick.x, cursor.y - initialClick.y);
                    Rectangle tempBounds = tempPanel[0].getBounds();
                    tempBounds.setLocation(tempPanel[0].getLocationOnScreen());

                    JPanel currentBestCell = findBestEmptyCell(tempBounds);

                    if (highlightedCell != null && highlightedCell != currentBestCell) {
                        resetHighlightedCellBorder(highlightedCell);
                    }

                    if (currentBestCell != null && currentBestCell != highlightedCell) {
                        currentBestCell.setBorder(BorderFactory.createLineBorder(new Color(172, 130, 66), 2));
                        highlightedCell = currentBestCell;
                    }
                }

                glassPane.repaint();
            }
        }
        );

    }

    private void resetHighlightedCellBorder(JPanel highlightedCell) {
        Container parent = highlightedCell.getParent();
        Component[] all = parent.getComponents();

        boolean isBottomRow = all[all.length - 1] == highlightedCell;
        if (isBottomRow) {
            highlightedCell.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.GRAY));
        } else {
            highlightedCell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        }
    }

    private void moveTask(JPanel panel, JPanel bestCell, JPanel originCell, LocalDate dropDate) {

        Task task = (Task) panel.getClientProperty("task");

        if (originCell != null) {
            emptyCells.add(originCell);
            originCell.removeAll();

            if (task != null) {
                task.setStartDate(Date.from(dropDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                task.save();
            }
        } else {
            if (task == null) {
                task = createNewTaskFromPanel(panel, dropDate);
                if (task != null) {
                    setHatAsAssigned(panel, task);
                    panel.putClientProperty("task", task);
                    buildOrdersPanel(true);
                }
            }
        }
        bestCell.removeAll();
        bestCell.add(panel);
        panel.setBorder(null);
        bestCell.revalidate();
        bestCell.repaint();
        emptyCells.remove(bestCell);
    }

    private JPanel clonePanel(JPanel originalPanel, boolean showOriginalComponents, Color overrideBackground) {
        JPanel clone = new JPanel();
        clone.setLayout(new BorderLayout());
        clone.setBackground(overrideBackground != null ? overrideBackground : originalPanel.getBackground());
        clone.setSize(originalPanel.getSize());
        clone.setPreferredSize(originalPanel.getPreferredSize());
        clone.setBorder(originalPanel.getBorder());
        clone.putClientProperty("hat_id", originalPanel.getClientProperty("hat_id"));
        for (Component comp : originalPanel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel originalLabel = (JLabel) comp;
                JLabel copy = new JLabel(originalLabel.getText(), originalLabel.getHorizontalAlignment());
                copy.setFont(originalLabel.getFont());
                copy.setToolTipText(originalLabel.getToolTipText());
                clone.add(copy);
                comp.setVisible(showOriginalComponents);
            }
        }

        return clone;
    }

    private void setHatAsAssigned(JPanel original, Task task) {
        Container parent = original.getParent();
        if (parent == null) {
            return;
        }

        JPanel clone = clonePanel(original, true, underWorkColor);

        JLabel workerLabel = new JLabel(userLoggedIn.getFullName(), SwingConstants.CENTER);
        workerLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        clone.putClientProperty("task", task);
        addTaskListener(clone);
        clone.add(workerLabel, BorderLayout.SOUTH);

        int index = -1;
        Component[] siblings = parent.getComponents();
        for (int i = 0; i < siblings.length; i++) {
            if (siblings[i] == original) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            parent.add(clone, index);
            parent.revalidate();
            parent.repaint();
        }
    }

    private Task createNewTaskFromPanel(JPanel taskPanel, LocalDate dropDate) {
        String userId = userLoggedIn.getID();
        String hatId = (String) taskPanel.getClientProperty("hat_id");

        String query = "INSERT INTO task (start_date, status, user_id, hat_id) VALUES"
                + "('" + dropDate + "', " + "'PÅGÅENDE'" + ", " + userId + ", " + hatId + ")";
        try {
            String taskId = idb.getAutoIncrement("task", "task_id");
            idb.insert(query);
            System.out.println(query);

            query = "SELECT amount, material_id FROM hat_material WHERE hat_id = " + hatId;
            ArrayList<HashMap<String, String>> materialList = idb.fetchRows(query);

            System.out.println(query);

            for (HashMap<String, String> row : materialList) {
                query = "UPDATE material SET amount = amount - " + row.get("amount")
                        + " WHERE material_id = " + row.get("material_id");
                idb.update(query);

                System.out.println(query);
            }

            return new Task(taskId);

        } catch (InfException ex) {
            Logger.getLogger(ScheduleManager.class
                    .getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private JPanel findBestEmptyCell(Rectangle tempBounds) {
        Point calendarLocationOnScreen = calendarPanel.getLocationOnScreen();
        Dimension calendarSize = calendarPanel.getSize();
        Rectangle calendarBounds = new Rectangle(calendarLocationOnScreen, calendarSize);
        JPanel bestCell = null;
        double bestOverlapRatio = 0;
        if (calendarBounds.intersects(tempBounds)) {
            for (JPanel cell : emptyCells) {
                Rectangle cellBounds = cell.getBounds();
                Point cellScreenPos = cell.getLocationOnScreen();
                cellBounds.setLocation(cellScreenPos);

                Rectangle intersection = tempBounds.intersection(cellBounds);
                if (!intersection.isEmpty()) {
                    double intersectionArea = intersection.getWidth() * intersection.getHeight();
                    double cellArea = cellBounds.getWidth() * cellBounds.getHeight();

                    double overlapRatio = intersectionArea / cellArea;

                    if (overlapRatio > bestOverlapRatio) {
                        bestOverlapRatio = overlapRatio;
                        bestCell = cell;
                    }
                }
            }
        }
        if (bestOverlapRatio > 0.25) {
            return bestCell;
        } else {
            return null;
        }
    }

    private void setupNavigationListeners(JButton backButton, JButton forwardButton, JMonthChooser monthChooser, JYearChooser yearChooser) {
        backButton.addActionListener(e -> {
            startDate = startDate.minusDays(1);
            monthChooser.setMonth(startDate.getMonthValue() - 1);
            yearChooser.setYear(startDate.getYear());
            refreshSchedule();
        });

        forwardButton.addActionListener(e -> {
            startDate = startDate.plusDays(1);
            monthChooser.setMonth(startDate.getMonthValue() - 1);
            yearChooser.setYear(startDate.getYear());
            refreshSchedule();
        });

        monthChooser.addPropertyChangeListener("month", evt -> {
            int selectedMonth = monthChooser.getMonth() + 1;
            if (selectedMonth != startDate.getMonthValue()) {
                startDate = startDate.withMonth(selectedMonth);
                refreshSchedule();
            }
        });

        yearChooser.addPropertyChangeListener("year", evt -> {
            int selectedYear = yearChooser.getYear();
            if (selectedYear != startDate.getYear()) {
                startDate = startDate.withYear(selectedYear);
                refreshSchedule();
            }
        });

        calendarPanel.addMouseWheelListener(e -> {
            int direction = e.getWheelRotation();
            startDate = startDate.minusDays(direction);
            monthChooser.setMonth(startDate.getMonthValue() - 1);
            yearChooser.setYear(startDate.getYear());
            refreshSchedule();
            calendarPanel.setBackground(new Color(230, 230, 230));
            Timer timer = new Timer(150, evt -> calendarPanel.setBackground(Color.WHITE));
            timer.setRepeats(false);
            timer.start();
        });
    }

}
