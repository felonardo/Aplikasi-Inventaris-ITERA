
package com.model;

import com.controller.controller_login;
import com.koneksi.koneksi;
import com.view.UI_Login;
import com.view.UI_Main;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class model_login implements controller_login{
  
    @Override
    public void Login(UI_Login lgn) throws SQLException {
       try{
           
           String password = new String(lgn.txtPassword.getPassword());
           Connection con = koneksi.getKoneksi();
           Statement st = con.createStatement();
           String sql = "SELECT username,password FROM petugas where username ='" + lgn.txtUsername.getText()
                        + "' and password = '" + password + "'";
           ResultSet rs = st.executeQuery(sql);

           
           if(rs.next()){

               if(lgn.txtUsername.getText().equals(rs.getString("username")) && password.equals(rs.getString("password"))){
                    UI_Main Main = new UI_Main();
                    JOptionPane.showMessageDialog(lgn, "Selamat Datang Admin!");
                    Main.setVisible(true);
                    lgn.setVisible(false);
                    lgn.dispose();
               }
               
               rs.close();
               st.close();
               
           }else{
                    JOptionPane.showMessageDialog(lgn, "Periksa Kembali Username Anda!");
                    lgn.txtUsername.requestFocus();
               }

       }catch(Exception e){
           JOptionPane.showMessageDialog(lgn,"gagal"+e);
       }
    }
}
