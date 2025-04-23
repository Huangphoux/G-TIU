package com.example.g_tiu.item;

public class giaoDich {
    private String MaGD;
    private String TenGD;
    private String NgayGD;
    private double SoTien;
    private String GhiChu;
    private String LoaiGD;

    public giaoDich(String MaGD, String TenGD, String NgayGD, double SoTien, String GhiChu, String LoaiGD) {
        this.MaGD = MaGD;
        this.TenGD = TenGD;
        this.NgayGD = NgayGD;
        this.SoTien = SoTien;
        this.GhiChu = GhiChu;
        this.LoaiGD = LoaiGD;
    }

    public String getMaGD() {
        return MaGD;
    }

    public void setMaGD(String maGD) {
        MaGD = maGD;
    }

    public String getTenGD() {
        return TenGD;
    }

    public void setTenGD(String tenGD) {
        TenGD = tenGD;
    }

    public String getNgayGD() {
        return NgayGD;
    }

    public void setNgayGD(String ngayGD) {
        NgayGD = ngayGD;
    }

    public double getSoTien() {
        return SoTien;
    }

    public void setSoTien(double soTien) {
        SoTien = soTien;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getLoaiGD() {
        return LoaiGD;
    }

    public void setLoaiGD(String loaiGD) {
        LoaiGD = loaiGD;
    }
}
