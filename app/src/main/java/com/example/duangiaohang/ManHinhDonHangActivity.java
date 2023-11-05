package com.example.duangiaohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class ManHinhDonHangActivity extends AppCompatActivity {

    TabLayout tabLayout_DonHang;
    ViewPager2 viewPager2_DonHang;
    Viewpage2DonHangActivity viewpage2DonHangActivity;
    BottomNavigationView bottomNavigationView_DonHang;
    FrameLayout frameLayout_DonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_don_hang_layout);
        setControl();
        viewPager2_DonHang.setAdapter(viewpage2DonHangActivity);
    }

    private void setControl() {
        tabLayout_DonHang = findViewById(R.id.tablayout_DonHang);
        viewPager2_DonHang = findViewById(R.id.viewpager2_DonHang);
        bottomNavigationView_DonHang = findViewById(R.id.bottom_navDonHang);
frameLayout_DonHang = findViewById(R.id.framlayout_DonHang);
tabLayout_DonHang.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager2_DonHang.setVisibility(View.VISIBLE);
        frameLayout_DonHang.setVisibility(View.GONE);
        viewPager2_DonHang.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        viewPager2_DonHang.setVisibility(View.GONE);
        frameLayout_DonHang.setVisibility(View.VISIBLE);

    }
});
viewPager2_DonHang.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
    @Override
    public void onPageSelected(int position) {
        switch (position){
            case  0 :
            case 1 :
                tabLayout_DonHang.getTabAt(position).select();
        }
        super.onPageSelected(position);
    }
});

    }
}