package com.example.appnhom9;

public class Disease {
    private String tenBenh, trieuChung, viecNenLam, viecKhongNenLam, cachXuLy;

    public Disease(String tenBenh, String trieuChung, String viecNenLam, String viecKhongNenLam, String cachXuLy) {
        this.tenBenh = tenBenh;
        this.trieuChung = trieuChung;
        this.viecNenLam = viecNenLam;
        this.viecKhongNenLam = viecKhongNenLam;
        this.cachXuLy = cachXuLy;

    }

    public String getTenBenh() {
        return tenBenh;
    }

    public String getTrieuChung() {
        return trieuChung;
    }

    public String getViecNenLam() {
        return viecNenLam;
    }

    public String getViecKhongNenLam() {
        return viecKhongNenLam;
    }

    public String getCachXuLy() {
        return cachXuLy;
    }


}