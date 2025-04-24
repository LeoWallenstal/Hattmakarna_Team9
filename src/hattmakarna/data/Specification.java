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
import java.util.ArrayList;

/**
 *
 *
 */
public class Specification extends DatabaseObject {

    private int spec_id;
    private String beskrivning;
    private int hat_id;

    private String img_path;
    private BufferedImage imgImage = null;
    private String skiss_path;
    private BufferedImage skissImage = null;
    private ArrayList<BufferedImage> extraImages = new ArrayList<>();
    private ArrayList<String> extraImagePaths = new ArrayList<>();

    public BufferedImage getImgImage() {
        return imgImage;
    }

    public void setImgImage(BufferedImage imgImage) {
        this.imgImage = imgImage;
    }

    public String getSkiss_path() {
        return skiss_path;
    }

    public void setSkiss_path(String skiss_path) {
        this.skiss_path = skiss_path;
    }

    public BufferedImage getSkissImage() {
        return skissImage;
    }

    public void setSkissImage(BufferedImage skissImage) {
        this.skissImage = skissImage;
    }

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

    public ArrayList<BufferedImage> getExtraImages() {
        return extraImages;
    }

    public ArrayList<String> getExtraImagePaths() {
        return extraImagePaths;
    }

    public void setExtraImages(ArrayList<BufferedImage> extraImages) {
        this.extraImages = extraImages;
    }

    public void setExtraImagePaths(ArrayList<String> extraImagePaths) {
        this.extraImagePaths = extraImagePaths;
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

    public static ArrayList<BufferedImage> set3DFilesFromUser() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "jpeg", "gif", "png");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true); // Tillåt flera filer

        int returnVal = chooser.showOpenDialog(null);
        ArrayList<BufferedImage> images = new ArrayList<>();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = chooser.getSelectedFiles();

            for (File file : selectedFiles) {
                try {
                    BufferedImage img = ImageIO.read(file);
                    if (img != null) {
                        images.add(img);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Specification.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return images;
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
            if (imgImage != null) {

                String fileName = "specBild-hat-" + hat_id + ".png";
                File fileToSave = new File(SAVE_TO_PATH + fileName);
                fileToSave.getParentFile().mkdirs();

                ImageIO.write(imgImage, "png", fileToSave);

                img_path = fileToSave.getPath().replace("\\", "\\\\");
            }
            // Spara bild vald till fil 
            if (skissImage != null) {

                String fileName = "specSkiss-hat-" + hat_id + ".png";
                File fileToSave = new File(SAVE_TO_PATH + fileName);
                fileToSave.getParentFile().mkdirs();

                ImageIO.write(skissImage, "png", fileToSave);

                skiss_path = fileToSave.getPath().replace("\\", "\\\\");
            }
            if (extraImages != null && !extraImages.isEmpty()) {
                

                for (int i = 0; i < extraImages.size(); i++) {
                    BufferedImage img = extraImages.get(i);

                    String fileName = "extra-" + hat_id + "-" + i + ".png";
                    File fileToSave = new File(SAVE_TO_PATH + fileName);
                    fileToSave.getParentFile().mkdirs();

                    ImageIO.write(img, "png", fileToSave);
                    extraImagePaths.add(fileToSave.getPath().replace("\\", "\\\\"));
                }
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

    @Override
    public Specification clone() {
        Specification copy = new Specification();
        copy.spec_id = this.spec_id;  // Consider omitting or resetting this depending on DB usage
        copy.beskrivning = this.beskrivning;
        copy.hat_id = this.hat_id;
        copy.img_path = this.img_path;
        copy.skiss_path = this.skiss_path;

        // Deep copy images if present
        if (this.imgImage != null) {
            copy.imgImage = deepCopyBufferedImage(this.imgImage);
        }

        if (this.skissImage != null) {
            copy.skissImage = deepCopyBufferedImage(this.skissImage);
        }

        return copy;
    }

    private static BufferedImage deepCopyBufferedImage(BufferedImage original) {
        BufferedImage copy = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                original.getType()
        );
        copy.setData(original.getData());
        return copy;
    }
}
