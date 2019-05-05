/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;
import com.controller.controller_pegawai;
import com.koneksi.koneksi;
import com.view.Fr_Pegawai;
import com.view.Popup_Edit;
import com.view.Popup_TambahPegawai;
import java.awt.Component;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;


/**
 *
 * @author USER
 */
public class model_pegawai implements controller_pegawai{
    Connection Connect = koneksi.getKoneksi();
    
    @Override
    public void tambah(Popup_TambahPegawai Pop, Fr_Pegawai Fr) {
        String username=Pop.txtUsername.getText().trim();
        String password=Pop.txtPassword.getText().trim();
        String nama=Pop.txtNama.getText().trim();
        int nik=Integer.valueOf(Pop.txtNIK.getText().trim());
        
        //harus ditambahkan cara mencegah data kosong dan sejenisnya tanya asprak
        
        String insert="INSERT INTO petugas (username,password,nama_petugas, nik, flag) VALUES ('"
                +username+"','"
                +password+"','"
                +nama+"',"
                +nik+","
                +1+");";
         try{
            PreparedStatement stat = (PreparedStatement) Connect.prepareStatement(insert);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null,"Data Berhasil ditambahkan ");
             showtables(Fr);
    
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data Gagal ditambahkan "+e);;
        }
        
        System.out.println(insert);
    }

    @Override
    public void showtables(Fr_Pegawai Fr) {
            
            Object [] rows={"Username","Nama Petugas","NIK"};
            DefaultTableModel dtm=new DefaultTableModel(null,rows);
          
           
            Fr.TbBarang.setModel(dtm);
            Fr.TbBarang.setBorder(null);
            Fr.TbBarang.removeAll();
             String query="select username, nama_petugas, nik from petugas;";
        
        
        try{
            Statement stat = Connect.createStatement();
            ResultSet hasil = stat.executeQuery(query);
            
            while(hasil.next()){
                Object[] obj=new Object[7];
                obj[0]=hasil.getString("username");
                obj[1]=hasil.getString("nama_petugas");
                obj[2]=hasil.getString("nik");
                        
                dtm.addRow(obj);
             }
        }
        catch (SQLException e){
            System.out.println("Gagal eksekusi query select");
        }
    }

    @Override
    public void showAtributEdit(Popup_Edit Pop,int row) {
            
            String query=" Select jumlah_barang,nama_barang,nama_vendor from barang,vendor where id_barang="+row+" && barang.id_vendor=vendor.id_vendor;";
        
        try{
        
            Statement stat = Connect.createStatement();
            ResultSet hasil = stat.executeQuery(query);
            
                while(hasil.next()){
                    int jum=hasil.getInt("jumlah_barang");
                    String nama=hasil.getString("nama_barang");
                    String vendor=hasil.getString("nama_vendor");
                    
                    
                    Pop.txtJum.setText(String.valueOf(jum));
                    Pop.LblN.setText("harus <="+jum);
                    Pop.lblNama.setText(nama);
                    Pop.lblVendor.setText(vendor);
                }       
        }
        
        catch (SQLException e){
            
        }
    
        
    }
}
