/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;
import oru.inf.InfDB;
import java.util.ArrayList;
import oru.inf.InfException;
import javax.swing.JOptionPane;

/**
 *
 * @author sebas
 */
public class FillComboBox {
    private InfDB idb;
 public FillComboBox(InfDB idb){
this.idb = idb;

}
public void fyllCB(javax.swing.JComboBox<String> comboFyll, String sqlQuery){

        ArrayList<String> comboBoxList;

        try {
            comboFyll.removeAllItems();
            comboBoxList = idb.fetchColumn(sqlQuery);
            if (comboBoxList == null) {
            JOptionPane.showMessageDialog(null, "Inga resultat hittades för frågan.");
            return; }
            for (String innehall : comboBoxList) {
                 if (innehall!=null){
                 comboFyll.addItem(innehall);
                 }
            }
        } catch (InfException ettUndantag) {
            JOptionPane.showMessageDialog(null, "Databasfel");
            System.out.println("Internt felmeddelande: " + ettUndantag.getMessage());
        } catch (Exception ettUndantag) {
            JOptionPane.showMessageDialog(null, "Något gick fel");
            System.out.println("Internt felmeddelande: " + ettUndantag.getMessage());
        }


}






}
