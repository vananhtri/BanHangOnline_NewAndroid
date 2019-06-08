package com.example.vananh.banhangfuniture.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vananh.banhangfuniture.Model.OderCustomer;
import com.example.vananh.banhangfuniture.Model.Product;
import com.example.vananh.banhangfuniture.R;

import java.util.List;

public class DatHangAdapter extends ArrayAdapter<OderCustomer> {

    private SanPhamAdapter sanPhamAdapter;
    private ListView lvChiTiet;

    public DatHangAdapter(@NonNull Context context, @NonNull List<OderCustomer> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_dat_hang, parent, false);
        }
        TextView tvMaDatHang = convertView.findViewById(R.id.DatHangMaDH);
        TextView tvTongTien = convertView.findViewById(R.id.DatHangTongTien);
        TextView tvNgayDat = convertView.findViewById(R.id.DatHangNgayDat);
        ListView lvCTDatHang = convertView.findViewById(R.id.lsvCTDonHang);
        OderCustomer oderCustomer = getItem(position);
        if (oderCustomer != null) {
            tvMaDatHang.setText(String.valueOf(oderCustomer.getMaDatHang()));
            tvNgayDat.setText(oderCustomer.getNgayDat());
            tvTongTien.setText(oderCustomer.getTongTien().toString());


            List<Product> products = oderCustomer.getProducts();
            sanPhamAdapter = new SanPhamAdapter(getContext(), products);
            lvCTDatHang.setAdapter(sanPhamAdapter);
        }
        return convertView;
    }
}
