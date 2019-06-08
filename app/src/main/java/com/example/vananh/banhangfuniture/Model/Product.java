package com.example.vananh.banhangfuniture.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    String tenSanPham;
    String thanhTien;
    int soLuong;
    String hinh;

    protected Product(Parcel in) {
        tenSanPham = in.readString();
        thanhTien = in.readString();
        soLuong = in.readInt();
        hinh = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Product() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tenSanPham);
        dest.writeString(thanhTien);
        dest.writeInt(soLuong);
        dest.writeString(hinh);
    }
}
