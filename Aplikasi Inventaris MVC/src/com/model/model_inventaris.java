/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.controller.controller_inventaris;
import com.koneksi.koneksi;
import com.view.Fr_Inventaris;
import com.view.Popup_Edit;
import com.view.Popup_TambahInventaris;
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
public class model_inventaris implements controller_inventaris{

            Connection Connect = koneksi.getKoneksi();
    @Override
    public void tambah(Popup_TambahInventaris Pop, Fr_Inventaris Fr) {
        String nama_barang=Pop.txtNamaB.getText().trim();
        int jum=Integer.valueOf(Pop.txtJumlah.getText().trim());
        
        System.out.println(nama_barang);
        System.out.println(jum);
        
        //harus ditambahkan cara mencegah data kosong dan sejenisnya tanya asprak
               
        int id_lokasi=Pop.CmbLokasi.getSelectedIndex()+1;
        int id_jenis=Pop.CmbJenis.getSelectedIndex()+1;
        int id_vendor=Pop.CmbVendor.getSelectedIndex()+1;
        String kondisi=String.valueOf(Pop.CmbKondisi.getSelectedItem());
        int permanen=Pop.CmbKet.getSelectedIndex();
        
        
        System.out.println("idjenis:"+id_jenis);
        
        String insert="INSERT INTO barang (nama_barang,id_lokasi,jumlah_barang,id_jenis_barang,kondisi,permanen,id_vendor,flag) VALUES ('"
                +nama_barang+"',"
                +id_lokasi+","
                +jum+","
                +id_jenis+",'"
                +kondisi+"',"
                +permanen+","
                +id_vendor+","
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
    public void showtables(Fr_Inventaris Fr) {
            
            Object [] rows={"Jenis","Nama Barang","Jumlah","Lokasi","Keterangan","Kondisi","Vendor"};
            DefaultTableModel dtm=new DefaultTableModel(null,rows);
          
           
            Fr.TbBarang.setModel(dtm);
            Fr.TbBarang.setBorder(null);
            Fr.TbBarang.removeAll();
//            jScrollPane1.setVisible(true);
//            jScrollPane1.setViewportView(Fr.TbBarang);
             String query="select nama_barang,jumlah_barang,nama_jenis,nama_lokasi,nama_vendor,kondisi,permanen from barang,jenis_barang,lokasi,vendor where barang.id_jenis_barang=jenis_barang.id_jenis && barang.id_lokasi=lokasi.id_lokasi && barang.id_vendor=vendor.id_vendor;";
        
        
        try{
            Statement stat = Connect.createStatement();
            ResultSet hasil = stat.executeQuery(query);
            
            while(hasil.next()){
                Object[] obj=new Object[7];
                obj[0]=hasil.getString("nama_jenis");
                obj[1]=hasil.getString("nama_barang");
                obj[2]=hasil.getString("jumlah_barang");
                obj[3]=hasil.getString("nama_lokasi");
                obj[4]=hasil.getString("permanen");
                obj[5]=hasil.getString("kondisi");
                obj[6]=hasil.getString("nama_vendor");
                        
                dtm.addRow(obj);
                        
             }
        }
        catch (SQLException e){
            System.out.println("Gagal eksekusi query select");
    }
    }
    
public void showComboTambah(Popup_TambahInventaris combo){
        
        try {
        
        Object[] objLokasi = new Object[5];    
        String queryVendor = "select nama_vendor from vendor;";
        String queryJenis = "select nama_jenis from jenis_barang;";
        String queryLokasi = "select nama_lokasi from lokasi;";
        String queryRuang = "select nama_ruang from ruang";

        
        Statement stat = Connect.createStatement();  
        
        ResultSet hasilVendor = stat.executeQuery(queryVendor);
        
        //show combo Vendor
        while(hasilVendor.next()){
            Object[] obj = new Object[5];
            
            obj[0] = hasilVendor.getString("nama_vendor");
            
            combo.CmbVendor.addItem(obj[0]);      
        }
        hasilVendor.close();
        
        //show combo Jenis
        ResultSet hasilJenis = stat.executeQuery(queryJenis);  
        while(hasilJenis.next()){
            Object[] obj = new Object[5];
            
            obj[0] = hasilJenis.getString("nama_jenis");
            
            combo.CmbJenis.addItem(obj[0]);      
        }
        hasilJenis.close();
        
        //show combo Lokasi  
        ResultSet hasilLokasi = stat.executeQuery(queryLokasi);  
        while(hasilLokasi.next()){
//            Object[] obj = new Object[5];
            
            objLokasi[0] = hasilLokasi.getString("nama_lokasi");
            
            combo.CmbLokasi.addItem(objLokasi[0]);      
        }
            hasilLokasi.close(); 
        
        //show combo Ruang
        ResultSet hasilRuang = stat.executeQuery(queryRuang);  
        while(hasilRuang.next()){
            Object[] obj = new Object[5];
            
            obj[0] = hasilRuang.getString("nama_ruang");
            
            combo.CmbRuang.addItem(obj[0]);      
        }
            hasilRuang.close();
            stat.close();
        
        //show combo Keterangan
            Object[] Ket =new Object[2];
            Ket[0]="Permanen";
            Ket[1]="Tidak Permanen";
            combo.CmbKet.addItem(Ket[0]);
            combo.CmbKet.addItem(Ket[1]);
        
        //show combo Kondisi
            Object[] Kondisi = new Object[3];
            Kondisi[0]="Baru";
            Kondisi[1]="Rusak";
            Kondisi[2]="Service";
            combo.CmbKondisi.addItem(Kondisi[0]);
            combo.CmbKondisi.addItem(Kondisi[1]);
            combo.CmbKondisi.addItem(Kondisi[2]);
            
         
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
    }

//    int id=Integer.valueOf(Fr.TbBarang.getRowSelectionAllowed())+1;

    @Override
    public void showAtributEdit(Popup_Edit edit,Fr_Inventaris Fr) {
            
            String query=" Select jumlah_barang,nama_barang,nama_vendor from barang,vendor where id_barang="+Fr.TbBarang.getRowSelectionAllowed()+" && barang.id_vendor=vendor.id_vendor;";
        
        try{
        
            Statement stat = Connect.createStatement();
            ResultSet hasil = stat.executeQuery(query);
            
                while(hasil.next()){
                    int jum=hasil.getInt("jumlah_barang");
                    String nama=hasil.getString("nama_barang");
                    String vendor=hasil.getString("nama_vendor");
                    
                    
//                    txtJumlah.setText(String.valueOf(jum));
//                    lblN.setText("harus <="+jum);
//                    lblNama.setText(nama);
//                    lblVendor.setText(vendor);
                }       
        }
        
        catch (SQLException e){
            
        }
    
        
    }
    
    
}
