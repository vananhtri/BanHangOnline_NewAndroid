package com.example.vananh.banhangfuniture.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vananh.banhangfuniture.Model.OderCustomer;
import com.example.vananh.banhangfuniture.Model.Product;
import com.example.vananh.banhangfuniture.R;

import java.io.InputStream;
import java.util.List;

public class SanPhamAdapter extends ArrayAdapter<Product> {
    public SanPhamAdapter(@NonNull Context context, @NonNull List<Product> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_chi_tiet_don_hang, parent, false);
        }
        TextView tvTenSP = convertView.findViewById(R.id.txtTenSanPham);
        TextView tvSoLuong = convertView.findViewById(R.id.txtSoLuong);
        TextView tvThanhTien=convertView.findViewById(R.id.txtThanhTien);
        ImageView img = convertView.findViewById(R.id.imgSanPham);



        Product product = getItem(position);
        if(product !=null){

            tvTenSP.setText(product.getTenSanPham());
            tvSoLuong.setText(product.getSoLuong());
            tvThanhTien.setText(product.getThanhTien());
            String urlImage =  product.getHinh();
            if(!urlImage.isEmpty()) {
                new DownloadImageTask(img)
                        .execute(urlImage);
            }
        }
        return convertView;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
