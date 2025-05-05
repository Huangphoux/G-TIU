package com.example.g_tiu.item;

public class loaiGD {
    private String MaLGD;
    private String TenLGD;
    private String KieuGD;
    private double NganSach;

    public loaiGD(String MaLGD, String TenLGD, String KieuGD, double NganSach) {
        this.MaLGD = MaLGD;
        this.TenLGD = TenLGD;
        this.KieuGD = KieuGD;
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

    public String getKieuGD() {
        return KieuGD;
    }

    public void setKieuGD(String kieuGD) {
        KieuGD = kieuGD;
    }

    public double getNganSach() {
        return NganSach;
    }

    public void setNganSach(double nganSach) {
        NganSach = nganSach;
    }
}
