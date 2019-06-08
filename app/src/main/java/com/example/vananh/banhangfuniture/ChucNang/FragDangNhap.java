package com.example.vananh.banhangfuniture.ChucNang;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.banhangfuniture.Constant.Constant;
import com.example.vananh.banhangfuniture.Model.ResponseData;
import com.example.vananh.banhangfuniture.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragDangNhap.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragDangNhap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragDangNhap extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragDangNhap() {
        // Required empty public constructor
    }

    private EditText user, pass;
    private Button login, huy;

    // TODO: Rename and change types and number of parameters
    public static FragDangNhap newInstance(String param1, String param2) {
        FragDangNhap fragment = new FragDangNhap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Đăng nhập");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_dang_nhap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = getActivity().findViewById(R.id.EditUser);
        pass = getActivity().findViewById(R.id.EditPass);
        login = getActivity().findViewById(R.id.ButtonLogin);
        huy = getActivity().findViewById(R.id.ButtonHuy);

        //Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        } else {
            checkLogin();
            return;
        }
    }

    private void checkLogin() {
        String userName = user.getText().toString();
        String password = pass.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constant.BASE_URL + "api/MobileAPI/LoginMobile";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailOrPhone", userName); //name giống với giá trị truyền vào bên mvc
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        ResponseData responseData = gson.fromJson(String.valueOf(jsonObject), ResponseData.class);
                        if (!responseData.getSuccess()) {
                            onLoginFailed();
                            return;
                        }

                        parseData(responseData);
                        onLoginSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onLoginFailed();
            }
        });
        queue.add(stringRequest);
    }

    //save information to sharedReference
    private void parseData(ResponseData responseData) {

        SharedPreferences.Editor editor = getContext().getSharedPreferences("MyPref", MODE_PRIVATE).edit();

        Gson gson = new Gson();
        String join = gson.toJson(responseData.getData());
        editor.clear();
        editor.putString("customerInfo",join);
        editor.commit();


        //redirect to Home  Page;
        FragHome fragment = new FragHome();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    private void onLoginSuccess() {
        Toast.makeText(getContext(), "Login success", Toast.LENGTH_LONG).show();
        FragHome fragment = new FragHome();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void onLoginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();
        login.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String username = user.getText().toString();
        String password = pass.getText().toString();
        if (username.isEmpty()) {
            valid = false;
            user.setError("Không để trống tên đăng nhập");
        }
        if (password.isEmpty()) {
            valid = false;
            pass.setError("Không để trống mật khẩu");
        }
        return valid;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
