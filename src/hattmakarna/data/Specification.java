/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import oru.inf.InfDB;
import oru.inf.InfException;


/**
 *
 * @author Användaren
 */
public class Specification {

    private InfDB idb;
    private String specificationID;
    private String description;
    private String size;
    private String hatID;
    private String skiss;

    public Specification(HashMap<String, String> hat_SpecMap, InfDB idb) {
        this.idb = idb;

        this.specificationID = hat_SpecMap.get("spec_id");
        this.description = hat_SpecMap.get("beskrivning");
        this.size = hat_SpecMap.get("size");
        this.hatID = hat_SpecMap.get("hat_id");
        this.skiss = hat_SpecMap.get("skiss");
    }

    public Specification(String specificationID, InfDB idb) {
        this.idb = idb;

        HashMap<String, String> aSpecification = new HashMap<>();
        String sqlQuery = "SELECT * FROM hat_spec WHERE ID = " + specificationID;

        try {
            aSpecification = idb.fetchRow(sqlQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage() + ", in Specification(), Specification.java");

        }
    }

    public String getSpecID() {
        return specificationID;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public String getHatID() {
        return hatID;
    }

    public String getSkiss() {
        return skiss;
    }

    public void setDesciption(String newDescription) {
//    if (Validerare.){}
        this.description = newDescription;
    }

    public void setSize(String newSize) {
//    if (Validerare.){}
        this.size = newSize;
    }

    public void setSkiss(String newPathway) {
//    if (Validerare.){}
        this.skiss = newPathway;
    }

    public void choosePath() {
//    if (Validerare.)
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Välj en fil");
        fileChooser.setFileFilter(new ExtensionFileFilter("JPG and JPEG", new String[] { "JPG", "JPEG" }));
    }

    public void save() {
        String sqlQuery = "UPDATE hat_spec SET " + "beskrivning = '" + description + "', "
                + "size = '" + size + "', "
                + "skiss = '" + skiss + "', "
                + "WHERE customer_id = " + hatID;

        try {
            idb.update(sqlQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage() + " in save(), Specification.java");
        }

    }

    public void delete() {

    }

    public void create() {

    }

}
