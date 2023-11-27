package com.example.duangiaohang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duangiaohang.RecyclerView.OrderStatusListViewPageAdapter;
import com.example.duangiaohang.RecyclerView.Order_ListViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import com.example.duangiaohang.R;

import java.util.Objects;

public class OrderDeliveredFragmentHomepage extends Fragment {
    TabLayout tabLayout_ScreenOrderList;
    ViewPager viewPager_ScreenOrderList;
    OrderStatusListViewPageAdapter statusListViewPageAdapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_delivered_screen, container, false);
        setControl();
        setIntiazation();
        return view;
    }

    private void setControl() {
        tabLayout_ScreenOrderList = view.findViewById(R.id.tabLayout_OrderDeliveredFragment);
        viewPager_ScreenOrderList = view.findViewById(R.id.viewPager_OrderDeliveredFragment);
    }

    private void setIntiazation() {
        statusListViewPageAdapter = new OrderStatusListViewPageAdapter(requireActivity().getSupportFragmentManager());
        viewPager_ScreenOrderList.setAdapter(statusListViewPageAdapter);
        tabLayout_ScreenOrderList.setupWithViewPager(viewPager_ScreenOrderList);

    }

}