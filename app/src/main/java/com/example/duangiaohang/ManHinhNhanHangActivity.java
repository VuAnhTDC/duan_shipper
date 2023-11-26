package com.example.duangiaohang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.duangiaohang.Models.ShipperData;

import java.util.ArrayList;
import java.util.List;

public class ManHinhNhanHangActivity extends AppCompatActivity {

    private RecyclerView recUsersShipper;
//    private CustomAdapterNhanHangActivity customAdapterNhanHangActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_nhan_hang_layout);

        setControl();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recUsersShipper.setLayoutManager(linearLayoutManager);
//        customAdapterNhanHangActivity = new CustomAdapterNhanHangActivity(getListUserShipper());
//        recUsersShipper.setAdapter(customAdapterNhanHangActivity);
        //tạo đường kẻ cho list
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);


    }

    private void setControl() {
        recUsersShipper = findViewById(R.id.recUserShipper);

    }

    private List<ShipperData> getListUserShipper() {
        List<ShipperData> listUser = new ArrayList<>();
        // listUser.add(new ShipperData("demo1","hello","hello","hello","hello","hello","hello"));
        return listUser;
    }

}