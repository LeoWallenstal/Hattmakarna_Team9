package hattmakarna.UI;

import hattmakarna.data.Material;
import hattmakarna.data.MaterialPassContainer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
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

    public PickMaterialDialog(MaterialPassContainer m) {
        this.m = m;

        setTitle("Välj material");
        setModal(true);
        setSize(300, 400);
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

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(chooseButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(createButton);

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
                     JOptionPane.showMessageDialog(null,
                    "Att skapa material går ej än, tyvärr!", "error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
