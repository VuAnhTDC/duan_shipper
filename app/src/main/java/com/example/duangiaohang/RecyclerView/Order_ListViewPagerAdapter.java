package com.example.duangiaohang.RecyclerView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duangiaohang.Fragment.DeliveredSuccessfullyFragment;
import com.example.duangiaohang.Fragment.DeliveringFragment;
import com.example.duangiaohang.Fragment.NotDeliveryFragment;
import com.example.duangiaohang.Fragment.ReceiveFragment;
import com.google.firebase.database.annotations.Nullable;

public class Order_ListViewPagerAdapter extends FragmentStatePagerAdapter {


    public Order_ListViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReceiveFragment();
            case 1:
                return new DeliveringFragment();
            case 2:
                return new DeliveredSuccessfullyFragment();
            case 3:
                return new NotDeliveryFragment();
        }
        return new ReceiveFragment();
    }
    @Override
    public int getCount() {
        return 4;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "nhận hàng";
                break;
            case 1:
                title = "đơn giao";
                break;
            case 2:
                title = "hoàn thành đơn";
                break;
            case 3:
                title = "giao sau";
                break;
        }
        return title;
    }
}
