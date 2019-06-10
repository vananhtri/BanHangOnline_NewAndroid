package com.example.vananh.banhangfuniture.ChucNang.DonHang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.banhangfuniture.Adapter.DatHangAdapter;
import com.example.vananh.banhangfuniture.Constant.Constant;
import com.example.vananh.banhangfuniture.Model.CustomerInfo;
import com.example.vananh.banhangfuniture.Model.OderCustomer;
import com.example.vananh.banhangfuniture.Model.Product;
import com.example.vananh.banhangfuniture.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragDatHang.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragDatHang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragDatHang extends Fragment {
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
    public FragDatHang() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragDatHang.
     */
    // TODO: Rename and change types and number of parameters
    public static FragDatHang newInstance(String param1, String param2) {
        FragDatHang fragment = new FragDatHang();
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
        return inflater.inflate(R.layout.fragment_frag_dat_hang, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvDatHang = getActivity().findViewById(R.id.lsvDatHang);
        oderCustomers = new ArrayList<>();
        gifImageView = getActivity().findViewById(R.id.gifLoading);

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

        CustomerInfo customerInfo = gson.fromJson(json, CustomerInfo.class);

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


    private void getData(CustomerInfo customerInfo) {
        gifImageView.setVisibility(View.VISIBLE); // show loading
        int idCustomer = customerInfo.getId(); //lấy id khách hàng
        int orderStatus = 1; // Đã đặt
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
