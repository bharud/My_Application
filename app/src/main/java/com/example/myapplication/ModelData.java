package com.example.myapplication;

public class ModelData {
    public String nama, jenis_kelamin, id;
    public int nim;

    public ModelData(){

    }

    public ModelData(String nama, String jenis_kelamin, int nim) {
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.nim = nim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public int getNim() {
        return nim;
    }

    public void setNim(int nim) {
        this.nim = nim;
    }
}
