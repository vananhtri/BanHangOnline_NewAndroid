package com.example.vananh.banhangfuniture.Model;

public class OderCustomer {
    int MaDatHang;
    String NgayDat;
    String TenSanPham;
    String ThanhTien;
    int SoLuong;
    String Hinh;

    public OderCustomer() {
    }

    public OderCustomer(int maDatHang, String ngayDat, String tenSanPham, String thanhTien, int soLuong, String hinh) {
        MaDatHang = maDatHang;
        NgayDat = ngayDat;
        TenSanPham = tenSanPham;
        ThanhTien = thanhTien;
        SoLuong = soLuong;
        Hinh = hinh;
    }


    public void setMaDatHang(int maDatHang) {
        MaDatHang = maDatHang;
    }

    public void setNgayDat(String ngayDat) {
        NgayDat = ngayDat;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public void setThanhTien(String thanhTien) {
        ThanhTien = thanhTien;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public int getMaDatHang() {
        return MaDatHang;
    }

    public String getNgayDat() {
        return NgayDat;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public String getThanhTien() {
        return ThanhTien;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public String getHinh() {
        return Hinh;
    }

}
