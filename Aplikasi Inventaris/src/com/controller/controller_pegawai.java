/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.sql.SQLException;
import com.view.Fr_Pegawai;
import com.view.Popup_TambahPegawai;
import com.view.Popup_Edit;

/**
 *
 * @author USER
 */
public interface controller_pegawai {
    public void tambah (Popup_TambahPegawai Pop,Fr_Pegawai Fr);
//    public void showComboTambah (Popup_TambahPegawai Pop);
    public void showtables (Fr_Pegawai Fr);
    public void showAtributEdit (Popup_Edit Pop,int row);
//    public void pindah (Popup_Edit Pop,int row,int JumlahPindah);
    
//    public void Simpan (FormBarang bg) throws SQLException;
//    public void Ubah (FormBarang bg) throws SQLException;
//    public void Hapus (FormBarang bg) throws SQLException;
//    public void Tampil (FormBarang bg) throws SQLException;
//    public void Bersih (FormBarang bg) throws SQLException;
//    public void KlikTabel (FormBarang bg) throws SQLException;
}
