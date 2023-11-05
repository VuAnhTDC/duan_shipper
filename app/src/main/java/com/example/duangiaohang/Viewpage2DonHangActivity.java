package com.example.duangiaohang;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Viewpage2DonHangActivity extends FragmentStateAdapter {
    public Viewpage2DonHangActivity(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch ( position){
           case 0: return  new MHNhanDonHang_ChuaGiaoFragment();
           case  1: return  new MHNhanDonHang_GiaoSauFragment();
           default: return  new MHNhanDonHang_ChuaGiaoFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
