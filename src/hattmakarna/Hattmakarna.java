/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hattmakarna;

import oru.inf.InfDB;
import oru.inf.InfException;
/**
 *
 * @author walle
 */
public class Hattmakarna {

    private static InfDB idb;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
        idb = new InfDB("hattmakaren", "3306", "dbHattAdmin", "dbHattAdminPW");
        new LogInWindow(idb).setVisible(true);
        }
        catch(InfException ex) {
                System.out.println(ex.getMessage());
                } 
    }
    
}
