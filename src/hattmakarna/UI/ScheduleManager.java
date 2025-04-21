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
    private final OrderRegister orderRegister;
    private final ArrayList<JPanel> emptyCells;
    private JPanel highlightedCell = null;

    public ScheduleManager(User userLoggedIn, JPanel calendarPanel, JScrollPane scrollOrders) {
        this.userLoggedIn = userLoggedIn;
        this.calendarPanel = calendarPanel;
        this.scrollOrders = scrollOrders;
        startDate = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        taskRegister = new TaskRegister();
        tasks = taskRegister.getTasks();
        orderRegister = new OrderRegister();
        emptyCells = new ArrayList<>();

        initSchedule();
        initOrders();
    }

    public void refreshSchedule() {
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
                    JPanel taskPanel = createTaskPanel(task);
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

    private JPanel createTaskPanel(Task task) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setOpaque(true);
        
        Hat hat = new Hat(String.valueOf(task.getHatId()));
        Model model = new Model(hat.getModelId());

        JLabel label = new JLabel(model.getName(), SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        panel.putClientProperty("task", task);

        URL imageUrl = getClass().getResource("/resources/icons/magician-hat.png");
        String toolTip = "<html>"
                + "<img src='" + imageUrl.toExternalForm() + "' width='64' height='64'><br>"
                + "Placeholder för tooltip.<br>"
                + "Kanske lägga till någon beskrivning här? :)<br>"
                + "</html>";

        panel.setToolTipText(toolTip);
        return panel;
    }

    public void initOrders() {
        ArrayList<Order> orders = orderRegister.getOrders();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        for (Order aOrder : orders) {
            if (aOrder.getStatus() != Status.BEKRÄFTAD) {
                continue;
            }

            String query = """
                SELECT h.hat_id, m.name 
                FROM hat h
                JOIN hat_model m ON h.model_id = m.model_id
                WHERE h.hat_id NOT IN (SELECT hat_id FROM task)
                  AND h.order_id = """ + aOrder.getOrder_id();

            try {
                ArrayList<HashMap<String, String>> hats = idb.fetchRows(query);
                addOrders(listPanel, aOrder, hats);
            } catch (InfException ex) {
                Logger.getLogger(ScheduleManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        scrollOrders.setViewportView(listPanel);
        scrollOrders.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    }

    private void addOrders(JPanel parent, Order order, ArrayList<HashMap<String, String>> hats) {
        if (!hats.isEmpty()) {
            JPanel orderContainer = new JPanel();
            orderContainer.setLayout(new BoxLayout(orderContainer, BoxLayout.Y_AXIS));
            orderContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
            orderContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            String orderTitle = "Order #" + order.getOrder_id();
            JButton toggleButton = new JButton("▶ " + orderTitle);
            toggleButton.setFocusPainted(false);
            toggleButton.setContentAreaFilled(false);
            toggleButton.setBorderPainted(false);
            toggleButton.setHorizontalAlignment(SwingConstants.LEFT);
            toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
            itemsPanel.setVisible(false);

            JPanel currentRow = null;
            int i = 0;
            for (HashMap<String, String> hat : hats) {
                String hatId = hat.get("hat_id");
                String hatName = hat.get("name");

                JPanel item = new JPanel();
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
                item.setBackground(Color.LIGHT_GRAY);
                item.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                item.add(new JLabel(hatName));
                item.putClientProperty("hat_id", hatId);
                makeDraggable(item);
                currentRow.add(item);
                i++;
            }
            toggleButton.addActionListener(e -> {
                boolean visible = itemsPanel.isVisible();
                itemsPanel.setVisible(!visible);
                toggleButton.setText((visible ? "▶ " : "▼ ") + orderTitle);
                parent.revalidate();
                parent.repaint();
            });

            orderContainer.add(toggleButton);
            orderContainer.add(itemsPanel);

            parent.add(orderContainer);
        }
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
                tempPanel[0] = createTempPanel(panel);
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

                JComponent glassPane = (JComponent) frame.getGlassPane();
                Rectangle tempBounds = tempPanel[0].getBounds();
                tempBounds.setLocation(tempPanel[0].getLocationOnScreen());

                JPanel bestCell = findBestEmptyCell(tempBounds);

                if (bestCell != null) {
                    int dropX = bestCell.getLocationOnScreen().x;
                    int calendarX = calendarPanel.getLocationOnScreen().x;
                    int totalWidth = calendarPanel.getWidth();
                    int columnWidth = totalWidth / 7;
                    int columnIndex = Math.max(0, Math.min(6, (dropX - calendarX) / columnWidth));
                    LocalDate dropDate = startDate.plusDays(columnIndex);
                    moveTask(panel, bestCell, originCell[0], dropDate);
                }

                glassPane.removeAll();
                glassPane.repaint();
                glassPane.setVisible(false);
                tempPanel[0] = null;
                frame.setCursor(Cursor.DEFAULT_CURSOR);
                panel.setOpaque(true);
                panel.setBackground(Color.LIGHT_GRAY);

                if (highlightedCell != null) {
                    Container parent = highlightedCell.getParent();
                    Component[] all = parent.getComponents();

                    boolean isBottomRow = all[all.length - 1] == highlightedCell;
                    if (isBottomRow) {
                        highlightedCell.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.GRAY));
                    } else {
                        highlightedCell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                    }
                    highlightedCell = null;
                }

                for (Component c : panel.getComponents()) {
                    c.setVisible(true);
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                JComponent glassPane = (JComponent) frame.getGlassPane();
                Point cursor = SwingUtilities.convertPoint(panel, e.getPoint(), glassPane);

                if (tempPanel[0] != null) {
                    tempPanel[0].setLocation(cursor.x - initialClick.x, cursor.y - initialClick.y);
                    Rectangle tempBounds = tempPanel[0].getBounds();
                    tempBounds.setLocation(tempPanel[0].getLocationOnScreen());

                    JPanel currentBestCell = findBestEmptyCell(tempBounds);

                    if (highlightedCell != null && highlightedCell != currentBestCell) {
                        Container parent = highlightedCell.getParent();
                        Component[] all = parent.getComponents();

                        boolean isBottomRow = all[all.length - 1] == highlightedCell;
                        if (isBottomRow) {
                            highlightedCell.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.GRAY));
                        } else {
                            highlightedCell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                        }
                    }

                    if (currentBestCell != null && currentBestCell != highlightedCell) {
                        currentBestCell.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
                        highlightedCell = currentBestCell;
                    }
                }

                glassPane.repaint();
            }
        });

    }

    private void moveTask(JPanel panel, JPanel bestCell, JPanel originCell, LocalDate dropDate) {
        bestCell.removeAll();
        bestCell.add(panel);
        panel.setBorder(null);
        panel.setVisible(true);
        bestCell.revalidate();
        bestCell.repaint();
        emptyCells.remove(bestCell);

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
                    tasks.add(task);
                    panel.putClientProperty("task", task);
                }
            }
        }
        taskRegister.refreshTasks();
        tasks = taskRegister.getTasks();
    }

    private Task createNewTaskFromPanel(JPanel taskPanel, LocalDate dropDate) {
        String userId = userLoggedIn.getID();
        String hatId = (String) taskPanel.getClientProperty("hat_id");

        String query = "INSERT INTO task (start_date, status, user_id, hat_id) VALUES"
                + "('" + dropDate + "', " + "'PÅGÅENDE'" + ", " + userId + ", " + hatId + ")";
        try {
            String taskId = idb.getAutoIncrement("task", "task_id");
            idb.insert(query);
            return new Task(taskId);
        } catch (InfException ex) {
            Logger.getLogger(ScheduleManager.class.getName()).log(Level.SEVERE, null, ex);
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

    private JPanel createTempPanel(JPanel original) {
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(original.getBackground());
        tempPanel.setSize(original.getSize());
        tempPanel.setBorder(original.getBorder());
        for (Component comp : original.getComponents()) {
            comp.setVisible(false);
            if (comp instanceof JLabel label) {
                tempPanel.add(new JLabel(label.getText()));
            }
        }
        return tempPanel;
    }

    private void setupNavigationListeners(JButton backButton, JButton forwardButton, JMonthChooser monthChooser, JYearChooser yearChooser) {
        backButton.addActionListener(e -> {
            startDate = startDate.minusDays(1);
            monthChooser.setMonth(startDate.getMonthValue()-1);
            yearChooser.setYear(startDate.getYear());
            refreshSchedule();
        });

        forwardButton.addActionListener(e -> {
            startDate = startDate.plusDays(1);
            monthChooser.setMonth(startDate.getMonthValue()-1);
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
            monthChooser.setMonth(startDate.getMonthValue()-1);
            yearChooser.setYear(startDate.getYear());
            refreshSchedule();
            calendarPanel.setBackground(new Color(230, 230, 230));
            Timer timer = new Timer(150, evt -> calendarPanel.setBackground(Color.WHITE));
            timer.setRepeats(false);
            timer.start();
        });
    }

}
