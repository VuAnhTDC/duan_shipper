package com.example.duangiaohang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MHNhanDonHang_GiaoSauFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MHNhanDonHang_GiaoSauFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.m_h_nhan_duoc_hang_giao_sau_fragment, container, false);
    }
}