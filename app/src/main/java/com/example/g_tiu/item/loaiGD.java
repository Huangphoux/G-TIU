package com.example.g_tiu.item;

public class loaiGD {
    private String MaLGD;
    private String TenLGD;
    private String Loai;
    private double NganSach;

    public loaiGD(String MaLGD, String TenLGD, String Loai, double NganSach) {
        this.MaLGD = MaLGD;
        this.TenLGD = TenLGD;
        this.Loai = Loai;
        this.NganSach = NganSach;
    }

    public String getMaLGD() {
        return MaLGD;
    }

    public void setMaLGD(String maLGD) {
        MaLGD = maLGD;
    }

    public String getTenLGD() {
        return TenLGD;
    }

    public void setTenLGD(String tenLGD) {
        TenLGD = tenLGD;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public double getNganSach() {
        return NganSach;
    }

    public void setNganSach(double nganSach) {
        NganSach = nganSach;
    }
}
