package com.example.vananh.banhangfuniture.ChucNang;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vananh.banhangfuniture.R;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FragHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragHome() {
        // Required empty public constructor
    }

    public void onResume() {
        super.onResume();
        getActivity().setTitle("GIỚI THIỆU");
    }

    private TextView txtgioiThieu;

    // TODO: Rename and change types and number of parameters
    public static FragHome newInstance(String param1, String param2) {
        FragHome fragment = new FragHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Nhà xinh");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtgioiThieu = (TextView) getView().findViewById(R.id.textgioithieu);
//        ReadFileGioiThieu();
        LoadData("gioithieua.txt");
    }
    public String LoadData(String inFile) {
        String tContents = "";
        try {
            InputStream stream = getActivity().getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
            txtgioiThieu.setText(tContents.toString());
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }
    private void ReadFileGioiThieu() {
        String data;
        int id = getRawResIdByName("gioithieua");
        InputStream in = getActivity().getResources().openRawResource(id);
        InputStreamReader inreader = new InputStreamReader(in);
        BufferedReader bufreader = new BufferedReader(inreader);
        StringBuilder builder = new StringBuilder();
        if (in != null) {
            try {
                while ((data = bufreader.readLine()) != null) {
                    builder.append(data);
                    builder.append("\n");
                }
                in.close();
                txtgioiThieu.setText(builder.toString());
            } catch (IOException ex) {
                Log.e("ERROR", ex.getMessage());
            }
        }

    }

    private int getRawResIdByName(String myvideo) {
        String pkgName = getActivity().getPackageName();
        // Trả về 0 nếu không tìm thấy.
        int resID = getActivity().getResources().getIdentifier(myvideo, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + myvideo + "==> Res ID = " + resID);
        return resID;
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
