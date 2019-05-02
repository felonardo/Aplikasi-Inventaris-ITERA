/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi.inventaris;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


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
//            
//            JOptionPane.showMessageDialog(null, "Login Sukses");
//                new UI_Main().setVisible(true);
                
            
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Gagal Koneksi "+e);
        }
        System.out.println(connect);
        return connect;
    
        
    }
      void insertData(Connection Connect , String query){
        try{
            PreparedStatement stat = (PreparedStatement) Connect.prepareStatement(query);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null,"Insert Berhasil ");
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Insert Gagal "+e);;
        }
    }
        
      void updateData(Connection Connect, int id, int jumlahPindah){
          int selisih_jum=0;
          int jumlah_db=0;
          int jenis=0,lokasi=0,permanen=0,vendor=0;
          String[] obj=new String[2];

            
        try{
            String query1="select * from barang where id_barang="+id+";";
            Statement stat = Connect.createStatement();
            ResultSet hasil = stat.executeQuery(query1);
            
            while(hasil.next()){
                jumlah_db=hasil.getInt("jumlah_barang");
                obj[0]=hasil.getString("nama_barang");
                lokasi=hasil.getInt("id_lokasi"); //int
                permanen=hasil.getInt("permanen");  //int
                obj[1]=hasil.getString("kondisi");   //varchar
                vendor=hasil.getInt("id_vendor"); //int
                jenis=hasil.getInt("id_jenis_barang"); //int
                
                
            }
            
        
            
            if(jumlahPindah>0){
                selisih_jum=jumlah_db-jumlahPindah;
                
                
                //query
                String insert="INSERT INTO barang (nama_barang,id_lokasi,jumlah_barang,id_jenis_barang,kondisi,permanen,id_vendor,flag) VALUES ('"+obj[0]+"',"+lokasi+","+jumlahPindah+","+jenis+",'"+obj[1]+"',"+permanen+","+vendor+","+1+");";

                String update0="update barang set "
                +"id_lokasi="+2+" ,"
                +"kondisi='rusak'"+" where id_barang="+id+";";

                String update1="update barang set "
                +"jumlah_barang="+selisih_jum+","
                +"id_lokasi="+2+" ,"
                +"kondisi='rusak' "+"where id_barang="+id+";";
                    
                
                if(jumlah_db==jumlahPindah){
                    PreparedStatement stat2 = (PreparedStatement) Connect.prepareStatement(update0);
                    stat2.executeUpdate();


                }
                else if(jumlah_db>jumlahPindah){

                    try{

                    insertData(Connect, insert);
                    PreparedStatement stat2 = (PreparedStatement) Connect.prepareStatement(update1);
                    stat2.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Update Berhasil ");
                    }
                    catch (SQLException ex){
                        JOptionPane.showMessageDialog(null,"Gagal Update "+ex);
                    }
                }
                else if(jumlahPindah==0){
                   JOptionPane.showMessageDialog(null, "Gagal tidak ada barang yang dipindah"); 
                }
                else{
                    JOptionPane.showMessageDialog(null, "Gagal jumlah barang pindah tidak boleh melebihi barang yang sudah ada");
                }
            }
            else{
              JOptionPane.showMessageDialog(null, "Gagal jumlah barang pindah tidak boleh melebihi barang yang sudah ada");
              
            }
            
//            PreparedStatement stat2 = (PreparedStatement) Connect.prepareStatement(query2);
//            stat2.executeUpdate();
            
                System.out.println("Update Berhasil!");
            
        }
        catch (SQLException e){
            System.out.println("Gagal eksekusi query update"+e);
        
        }
    }
      
       
    public void showData (Connection Connect , DefaultTableModel dtm){
        
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
    
    public void showAtribut(Connection Connect, int id, JTextField txtJumlah, JLabel lblN, JLabel lblNama, JLabel lblVendor){
            
            String query=" Select jumlah_barang,nama_barang,nama_vendor from barang,vendor where id_barang="+id+" && barang.id_vendor=vendor.id_vendor;";
        
        try{
        
            Statement stat = Connect.createStatement();
            ResultSet hasil = stat.executeQuery(query);
            
                while(hasil.next()){
                    int jum=hasil.getInt("jumlah_barang");
                    String nama=hasil.getString("nama_barang");
                    String vendor=hasil.getString("nama_vendor");
                    
                    
                    txtJumlah.setText(String.valueOf(jum));
                    lblN.setText("harus <="+jum);
                    lblNama.setText(nama);
                    lblVendor.setText(vendor);
                }       
        }
        
        catch (SQLException e){
            
        }
    }
   
    void close(Connection Connect){
        try{
            Connect.close();
        }
        catch (SQLException ex){
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE,null,ex);
        }
           
    }
    
}
   
    
    
