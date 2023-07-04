package com.example.pengaduanmasyarakatrevisi;

public class Laporan {
    private String currentuserid;
    private String judul;
    private String keluhan;
    private String kondisi;
    private String lokasi;
    private String gambar;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Laporan(String currentuserid, String judul, String keluhan, String kondisi, String lokasi, String gambar) {
        this.currentuserid = currentuserid;
        this.judul = judul;
        this.keluhan = keluhan;
        this.kondisi = kondisi;
        this.lokasi = lokasi;
        this.gambar = gambar;
    }

    public Laporan(){

    }

    public String getCurrentuserid() {
        return currentuserid;
    }

    public void setCurrentuserid(String currentuserid) {
        this.currentuserid = currentuserid;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


}
