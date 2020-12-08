package com.example.myapplication;

public class ModelUser {
    String gmail, fb, nohp, nama, idnya;

    public ModelUser() {
    }

    public ModelUser(String gmail, String fb, String nohp, String nama, String idnya) {
        this.gmail = gmail;
        this.fb = fb;
        this.nohp = nohp;
        this.nama = nama;
        this.idnya = idnya;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setIdnya(String idnya) {
        this.idnya = idnya;
    }

    public String getGmail() {
        return gmail;
    }

    public String getFb() {
        return fb;
    }

    public String getNohp() {
        return nohp;
    }

    public String getNama() {
        return nama;
    }

    public String getIdnya() {
        return idnya;
    }
}
