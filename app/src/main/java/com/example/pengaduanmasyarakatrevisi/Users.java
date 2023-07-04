package com.example.pengaduanmasyarakatrevisi;

public class Users {
    private String nik;
    private  String notelp;
    private String tanggallahir;
    private String username;
    private String password;
    private String Uid;
    private int usertype;

    public Users(String uid,String nik, String no, String tanggallahir, String username, String password, int usertype) {
        Uid = uid;
        this.nik = nik;
        this.notelp = no;
        this.tanggallahir = tanggallahir;
        this.username = username;
        this.password = password;
        usertype = usertype;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getTanggallahir() {
        return tanggallahir;
    }

    public void setTanggallahir(String tanggallahir) {
        this.tanggallahir = tanggallahir;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
