package com.example.duangiaohang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duangiaohang.Fragment.HomeFragmentHomepage;
import com.example.duangiaohang.Fragment.OrderDeliveredFragmentHomepage;
import com.example.duangiaohang.Fragment.OrderFragmentHomepage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    Context context;
    HomeFragmentHomepage homeFragment = new HomeFragmentHomepage();
    OrderFragmentHomepage orderFragment = new OrderFragmentHomepage();
    OrderDeliveredFragmentHomepage orderDeliveredFragment = new OrderDeliveredFragmentHomepage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_trang_chu_shippe_layout);
        context= this;
        setControl();
        loadingFragment(homeFragment);
        setEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnShop_Actionbar){
            Intent intent = new Intent(context, AccountInformationActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.mnNotification_Actionbar){
            Intent intent = new Intent(context, AccountInformationActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mnAccouunt_Actionbar){
            Intent intent = new Intent(context,AccountInformationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_top_trang_chu, menu);
        return super.onPrepareOptionsMenu(menu);
    }


    private void setEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.buttom_TrangChu_DonHang){
                    loadingFragment(homeFragment);
                    return true;
                } else if (item.getItemId() == R.id.buttom_DonHang_DonHang) {
                    loadingFragment(orderFragment);
                    return true;
                }
//                else if (item.getItemId() == R.id.buttom_NhanHang_DonHang){
//                    loadingFragment(orderDeliveredFragment);
//                    return true;
//                }
                return false;
            }
        });
    }
    private void loadingFragment(Fragment fragmentLoading){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragmentLoading);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setControl() {
        frameLayout = findViewById(R.id.framelayout_screenhome);
        bottomNavigationView = findViewById(R.id.bottom_navDonHang);
    }
}