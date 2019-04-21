/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi.inventaris;
import javax.swing.*;
import java.sql.*;

/**
 *
 * @author USER
 */
public class Main extends UI_Login{
    
    
    
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UI_Login login = new UI_Login();
                login.setVisible(true);
                
                
                
                
                
             
                
            }
            
            
        });
    }
}
