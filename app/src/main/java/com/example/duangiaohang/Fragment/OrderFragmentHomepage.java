package com.example.duangiaohang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duangiaohang.R;
import com.example.duangiaohang.RecyclerView.Order_ListViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class OrderFragmentHomepage extends Fragment {
    TabLayout tabLayout_ScreenOrderList;
    ViewPager viewPager_ScreenOrderList;
    Order_ListViewPagerAdapter orderListViewPagerAdapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_screen, container, false);
        setControl();
        setIntiazation();
        return view;
    }
    private void setIntiazation() {
        orderListViewPagerAdapter = new Order_ListViewPagerAdapter(requireActivity().getSupportFragmentManager());
        viewPager_ScreenOrderList.setAdapter(orderListViewPagerAdapter);
        tabLayout_ScreenOrderList.setupWithViewPager(viewPager_ScreenOrderList);
    }

    private void setControl() {
        tabLayout_ScreenOrderList = view.findViewById(R.id.tabLayout_ScreenOrderList);
        viewPager_ScreenOrderList = view.findViewById(R.id.viewPager_ScreenOrderList);
    }
}