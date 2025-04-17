package hattmakarna.UI;

import hattmakarna.data.Material;
import hattmakarna.data.MaterialPassContainer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import oru.inf.InfException;

/**
 * Dialogruta för att välja ett material ur databasen. Visar en JList med alla
 * material, samt knappar för att välja eller avbryta. MaterialID skickas
 * tillbaka till det objekt som implementerar MaterialPassContainer.
 *
 * @author leonb
 */
public class PickMaterialDialog extends JDialog {

    private ArrayList<String> ids;
    private MaterialPassContainer m;
    private JList<String> material_list;
    private JButton chooseButton;
    private JButton cancelButton;
    private JButton createButton;
    private JButton deleteButton;

    public PickMaterialDialog(MaterialPassContainer m) {
        this.m = m;

        setTitle("Välj material");
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        populateList();
        addEventHandlers();

        setVisible(true);
    }

    private void initComponents() {
        material_list = new JList<>();
        material_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        chooseButton = new JButton("Välj");
        cancelButton = new JButton("Avbryt");
        createButton = new JButton("Skapa");
        deleteButton = new JButton("Ta bort");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(chooseButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(material_list), BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateList() {
        try {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            ids = hattmakarna.data.Hattmakarna.idb.fetchColumn("select material_id from material");
            for (String id : ids) {
                listModel.addElement(new Material(id).getName());
            }
            material_list.setModel(listModel);
        } catch (InfException ex) {
            Logger.getLogger(PickMaterialDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addEventHandlers() {
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = material_list.getSelectedIndex();
                if (selectedIndex >= 0) {
                    String selectedMaterialId = ids.get(selectedIndex);
                    m.m = new Material(selectedMaterialId); // pass it to caller
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(PickMaterialDialog.this, "Välj ett material först.", "Ingen markering", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // just close the dialog
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog createDialog = new JDialog();
                createDialog.setTitle("Nytt material");
                createDialog.setModal(true);
                createDialog.setSize(300, 400);
                createDialog.setResizable(false);
                createDialog.setLocationRelativeTo(null);
                createDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JLabel name = new JLabel("Namn:");
                JTextField nameField = new JTextField("0");
                JLabel unit = new JLabel("Enhet:");
                JTextField unitField = new JTextField(""); //Alt att detta byts ut till en JComboBox

                // Huvudpanelen med BoxLayout (vertikal layout)
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

                // Panel för namn och enhet
                JPanel inputPanel = new JPanel();
                inputPanel.add(name);
                inputPanel.add(nameField);
                inputPanel.add(unit);
                inputPanel.add(unitField);

                // Panel för knappar (centrerad layout)
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton createBtn = new JButton("Skapa");
                JButton cancelBtn = new JButton("Avbryt");
                buttonPanel.add(cancelBtn);
                buttonPanel.add(createBtn);

                mainPanel.add(Box.createVerticalStrut(100));//Lägger till mellanrum högst upp
                mainPanel.add(inputPanel);//Lägger till delen med label och textfield
                mainPanel.add(Box.createVerticalStrut(180)); //Lägger till mellanrum till knapparna
                mainPanel.add(buttonPanel); //Lägger till delen med knapparna

                createDialog.add(mainPanel);

                cancelBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createDialog.dispose(); //stänger rutan utan ändringar
                    }
                });

                createBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Material s = new Material();
                        s.setName(nameField.getText());
                        s.setUnit(unitField.getText());

                        s.save();

                        createDialog.dispose();

                        populateList();
                    }
                });
                createDialog.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = material_list.getSelectedIndex();
                if (selectedIndex >= 0) {
                    String selectedMaterialId = ids.get(selectedIndex);
                    new Material(selectedMaterialId).delete(); // pass it to caller
                    populateList();
                } else {
                    JOptionPane.showMessageDialog(PickMaterialDialog.this, "Välj ett material först.", "Ingen markering", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
