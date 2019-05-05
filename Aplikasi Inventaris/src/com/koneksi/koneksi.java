package com.koneksi;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class koneksi {
    private static Connection koneksi;
    
    public static Connection getKoneksi(){
        if(koneksi == null){
            try{
                String url = "jdbc:mysql://localhost/tubes";
                String user = "root";
                String pwd = "";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = (Connection) DriverManager.getConnection(url, user, pwd);
                
            //    JOptionPane.showMessageDialog(null, "Koneksi Sukses!!!");
            }catch(Exception e){
                System.out.println(e);
            }
        }return koneksi;
    }
    
//    public static void main(String[] args) {
//        Connection koneksi = new koneksi().getKoneksi();
//    }
}
