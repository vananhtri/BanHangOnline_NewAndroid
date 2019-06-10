package com.example.vananh.banhangfuniture.ChucNang.DonHang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.banhangfuniture.Adapter.DatHangAdapter;
import com.example.vananh.banhangfuniture.Constant.Constant;
import com.example.vananh.banhangfuniture.Model.CustomerInfo;
import com.example.vananh.banhangfuniture.Model.OderCustomer;
import com.example.vananh.banhangfuniture.Model.Product;
import com.example.vananh.banhangfuniture.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.MODE_PRIVATE;


public class FragDaGiao extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private DatHangAdapter datHangAdapter;
    private List<OderCustomer> oderCustomers;
    private ListView lvDatHang;
    private GifImageView gifImageView;

    public FragDaGiao() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragDaGiao newInstance(String param1, String param2) {
        FragDaGiao fragment = new FragDaGiao();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_da_giao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvDatHang = getActivity().findViewById(R.id.lsvDatHangDaGiao);
        oderCustomers = new ArrayList<>();
        gifImageView = getActivity().findViewById(R.id.gifLoadingDaGiao);

        //get info customer
        SharedPreferences mPrefs = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Gson gson = new Gson();
        if (mPrefs == null) {
            Toast.makeText(getContext(), "Bạn phải đăng nhập để xem đơn hàng", Toast.LENGTH_LONG).show();
            return;
        }
        String json = mPrefs.getString("customerInfo", "");

        if (json.isEmpty()) {
            Toast.makeText(getContext(), "Bạn phải đăng nhập để xem đơn hàng", Toast.LENGTH_LONG).show();
            return;
        }

       final CustomerInfo customerInfo = gson.fromJson(json, CustomerInfo.class);

        getData(customerInfo);
        lvDatHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ActivityDetailOrder.class);
                OderCustomer order = oderCustomers.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("products", (ArrayList<Product>) order.getProducts());
                intent.putExtra("detail", bundle);
                startActivity(intent);
            }
        });

    }
    // Lấy dữ liệu đơn hàng (GET)
    private void getData(CustomerInfo customerInfo) {
        gifImageView.setVisibility(View.VISIBLE); // sho loading
        int idCustomer = customerInfo.getId();
        int orderStatus = 3; // Đã đặt
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constant.BASE_URL + "api/MobileApi/GetOrderStatus?status=" + orderStatus + "&idCustomer=" + idCustomer;

        final JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        gifImageView.setVisibility(View.INVISIBLE); //hide loading
                        Gson gson = new Gson();

                        Type typeOfT = new TypeToken<List<OderCustomer>>() {
                        }.getType();
                        final List<OderCustomer> orders = gson.fromJson(String.valueOf(jsonArray), typeOfT);
                        if (orders.size() <= 0) {
                            Toast.makeText(getContext(), "Bạn chưa có đơn hàng nào", Toast.LENGTH_LONG).show();
                            return;
                        }
                        oderCustomers = orders;
                        datHangAdapter = new DatHangAdapter(getContext(), oderCustomers);
                        lvDatHang.setAdapter(datHangAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                gifImageView.setVisibility(View.INVISIBLE); //hide loading
                Toast.makeText(getContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
        //code

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
