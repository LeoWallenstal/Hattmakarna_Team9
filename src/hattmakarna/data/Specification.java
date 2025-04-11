/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import hattmakarna.Hattmakarna;
import hattmakarna.util.Util;
import oru.inf.InfException;

/**
 *
 * 
 */
public class Specification {

	private int spec_id;
	private String beskrivning;
	private int hat_id;
	private String img_path;

	public Specification(String specificationID) {

		try {
			Util.DatabaseObjectMapper(this, Hattmakarna.idb.fetchRow("SELECT * FROM hat_spec WHERE ID = "));
		} catch (InfException ex) {
			ex.printStackTrace();
		}
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

	public void setSkiss(String imgPath) {
		this.img_path = imgPath;
	}

	/**
	 * Promptar användaren med ett fönster för att välja en bild fil.
	 * 
	 * @return file-object till den bild fil som valts. Null om ingen bild valts
	 */
	public static File getFileFromUser() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}

		return null;
	}

	public void save() {
		/*
		 * String sqlQuery = "UPDATE hat_spec SET " + "beskrivning = '" + description +
		 * "', " + "size = '" + size + "', " + "skiss = '" + skiss + "', " +
		 * "WHERE customer_id = " + hatID;
		 * 
		 * try { Hattmakarna.idb.update(sqlQuery); } catch (InfException ex) {
		 * System.out.println(ex.getMessage() + " in save(), Specification.java"); }
		 */
	}

	public void delete() {

	}
}
