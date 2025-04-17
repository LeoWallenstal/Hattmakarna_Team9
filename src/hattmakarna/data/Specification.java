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
    public static final String SAVE_TO_PATH = "images/";

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
    public static BufferedImage setFileFromUser() {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();

                return ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(Specification.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
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
        try {
            // Spara bild vald till fil 
            if (skissImage != null) {

                String fileName = "specBild-hat-" + hat_id + ".png";
                File fileToSave = new File(SAVE_TO_PATH + fileName);
                fileToSave.getParentFile().mkdirs();

                ImageIO.write(skissImage, "png", fileToSave);

                img_path = fileToSave.getPath().replace("\\", "\\\\");
            }
            return super.save();
        } catch (IOException ex) {
            Logger.getLogger(Specification.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    protected void setIdString(String id) {
        this.spec_id = Integer.parseInt(id);
    }

}
