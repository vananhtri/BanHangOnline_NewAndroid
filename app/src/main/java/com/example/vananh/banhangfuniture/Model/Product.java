package com.example.vananh.banhangfuniture.Model;

public class Product {
    String TenSanPham;
    String ThanhTien;
    int SoLuong;
    String Hinh;

    public Product() {
    }

    public Product(String tenSanPham, String thanhTien, int soLuong, String hinh) {
        TenSanPham = tenSanPham;
        ThanhTien = thanhTien;
        SoLuong = soLuong;
        Hinh = hinh;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(String thanhTien) {
        ThanhTien = thanhTien;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }
}
