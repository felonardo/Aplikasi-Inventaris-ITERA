/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi.inventaris;
import java.sql.*;
import javax.swing.JOptionPane;


/**
 *
 * @author USER
 */
public class Koneksi {
    
    Connection connect = null;
    
    public Koneksi(){
    }
    public Connection getCon(){
        try{
            connect=DriverManager.getConnection("jdbc:mysql://localhost/tubes","root","");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Gagal Koneksi "+e);
        }
        return connect;
    
    }
    
}
