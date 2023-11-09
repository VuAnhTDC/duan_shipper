package com.example.duangiaohang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.Models.ListItemUserTC;
import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.Models.ShopData;
import com.example.duangiaohang.R;
import com.example.duangiaohang.RecyclerView.MHTrangChuShipperAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MHTrangChuShipperActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTC;
    private ArrayList<ListItemUserTC> listItemUserTCArrayList = new ArrayList<>();

    private MHTrangChuShipperAdapter mhTrangChuShipperAdapter;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_trang_chu_shippe_layout);
        setControl();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("OrderProduct");
        mhTrangChuShipperAdapter = new MHTrangChuShipperAdapter(listItemUserTCArrayList);
        recyclerViewTC.setAdapter(mhTrangChuShipperAdapter);
        getListItemShipperUser();
    }


    private void getListItemShipperUser() {
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listItemUserTCArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ListItemUserTC listItemUserTC = dataSnapshot.getValue(ListItemUserTC.class);
                    listItemUserTCArrayList.add(listItemUserTC);
                }
                mhTrangChuShipperAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MHTrangChuShipperActivity.this, "Lỗi khi tải dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
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
