package com.example.vananh.banhangfuniture.ChucNang.DonHang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.vananh.banhangfuniture.Adapter.SanPhamAdapter;
import com.example.vananh.banhangfuniture.Model.Product;
import com.example.vananh.banhangfuniture.R;

import java.util.ArrayList;

public class ActivityDetailOrder extends AppCompatActivity {

    ArrayList<Product> products;
    ListView lvCTDatHang;
    SanPhamAdapter sanPhamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        setTitle("Đơn hàng");
        lvCTDatHang = findViewById(R.id.lsvCTDonHang);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("detail");
        products = bundle.getParcelableArrayList("products");
        sanPhamAdapter = new SanPhamAdapter(this, products);
        lvCTDatHang.setAdapter(sanPhamAdapter);
    }
}
