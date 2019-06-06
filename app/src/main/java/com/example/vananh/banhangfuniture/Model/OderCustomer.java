package com.example.vananh.banhangfuniture.Model;

import java.util.List;

public class OderCustomer {
    int MaDatHang;
    String NgayDat;
    Double TongTien;
    List<Product> products;
    public OderCustomer() {
    }

    public OderCustomer(int maDatHang, String ngayDat, Double tongTien, List<Product> products) {
        MaDatHang = maDatHang;
        NgayDat = ngayDat;
        TongTien = tongTien;
        this.products = products;
    }

    public int getMaDatHang() {
        return MaDatHang;
    }

    public void setMaDatHang(int maDatHang) {
        MaDatHang = maDatHang;
    }

    public String getNgayDat() {
        return NgayDat;
    }

    public void setNgayDat(String ngayDat) {
        NgayDat = ngayDat;
    }

    public Double getTongTien() {
        return TongTien;
    }

    public void setTongTien(Double tongTien) {
        TongTien = tongTien;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
