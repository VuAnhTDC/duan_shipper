package com.example.duangiaohang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.Models.ListItemUserTC;
import com.example.duangiaohang.Models.OrderData;

import com.example.duangiaohang.RecyclerView.CustomAdapterHomeShipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomerActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTC;
    private final ArrayList<ListItemUserTC> listItemUserTCArrayList = new ArrayList<>();
    ArrayList <OrderData> orderDataArrayList = new ArrayList<>();
    CustomAdapterHomeShipper customAdapterHomeShipper;

Context context;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_trang_chu_shippe_layout);
        setControl();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("OrderProduct");

        customAdapterHomeShipper = new CustomAdapterHomeShipper(orderDataArrayList,this);

        recyclerViewTC.setAdapter(customAdapterHomeShipper);
        getListItemShipperUser();
    }


    private void getListItemShipperUser() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("OrderProduct");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listItemUserTCArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    ListItemUserTC listItemUserTC = dataSnapshot.getValue(ListItemUserTC.class);
//                    listItemUserTCArrayList.add(listItemUserTC);
                    OrderData orderData = dataSnapshot.getValue(OrderData.class);
                    orderDataArrayList.add(orderData);
                }
//                mhTrangChuShipperAdapter.notifyDataSetChanged();
                customAdapterHomeShipper.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomerActivity.this, "Lỗi khi tải dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setControl() {
        recyclerViewTC = findViewById(R.id.recyclerViewTC);
        LinearLayoutManager linearLayoutManagerTC = new LinearLayoutManager(this);
        recyclerViewTC.setLayoutManager(linearLayoutManagerTC);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewTC.addItemDecoration(dividerItemDecoration);
    }
}
