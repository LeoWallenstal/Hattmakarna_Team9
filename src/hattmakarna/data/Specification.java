/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.UIManager;
import oru.inf.InfException;

/**
 *
 *
 */
public class Specification extends DatabaseObject {

    private int spec_id;
    private String beskrivning;
    private int hat_id;
    private String img_path;
    private BufferedImage skissImage = null;
    public static String SAVE_TO_PATH = "Images/";

    public Specification(String specificationID) {
        super(specificationID);
    }

    public Specification() {

    }

    public int getSpecID() {
        return spec_id;
    }

    public String getDescription() {
        return beskrivning;
    }

    public int getHatID() {
        return hat_id;
    }

    public String getImagePath() {
        return img_path;
    }

    public void setDesciption(String newDescription) {
        this.beskrivning = newDescription;
    }

    public void setSkiss(BufferedImage image) {
        this.skissImage = image;
    }

    public void setHatId(int id) {
        this.hat_id = id;
    }

    /**
     * Promptar användaren med ett fönster för att välja en bild fil.
     *
     * @return file-object till den bild fil som valts. Null om ingen bild valts
     */
    public void setFileFromUser() {

        try {
            // Set native look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();

                this.skissImage = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(Specification.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected String getTabelName() {
        return "hat_spec";
    }

    @Override
    protected String getIdAttributeName() {
        return "spec_id";
    }

    @Override
    protected String getIdString() {
        if (!hasId) {
            return null;
        }

        return Integer.toString(spec_id);
    }

    @Override
    public boolean save() {

        // Spara bild vald till fil
        String fileName = "SpecBild-hat-" + hat_id + ".png";
        File fileToSave = new File(SAVE_TO_PATH);

        fileToSave.getParentFile().mkdir();
        if (!fileToSave.canWrite()) {
            return false;
        }

        try {
            ImageIO.write(skissImage, "png", fileToSave);
            return super.save();
        } catch (IOException ex) {
            Logger.getLogger(Specification.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
