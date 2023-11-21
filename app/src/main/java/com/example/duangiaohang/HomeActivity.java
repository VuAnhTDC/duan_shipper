package com.example.duangiaohang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.RecyclerView.CustomAdapterHomeShipper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTC;

    ArrayList<OrderData> orderDataArrayList = new ArrayList<>();
    CustomAdapterHomeShipper customAdapterHomeShipper;
    LinearLayout linnerlayout;

    Context context;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_trang_chu_shippe_layout);
context= this;
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("OrderProduct");


        setControl();
        customAdapterHomeShipper = new CustomAdapterHomeShipper(orderDataArrayList, context);
       // LinearLayoutManager linearLayoutManagerTC = new LinearLayoutManager(this);
        recyclerViewTC.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewTC.setAdapter(customAdapterHomeShipper);
      //  getListItemShipperUser();
        setEvent();


    }

    private void setEvent() {

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


    private void getListItemShipperUser() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("OrderProduct");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderDataArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                        OrderData orderData = snapshot1.getValue(OrderData.class);
                        if (orderData.getStatusOrder() == 1) {
                            orderDataArrayList.add(orderData);
                            Log.d("FirebaseData", "Data: " + orderData.toString());

                        }
                    }
                }

                System.out.println("systemout"+orderDataArrayList.size());
                customAdapterHomeShipper.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Lỗi khi tải dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });



    }



    private void setControl() {
        recyclerViewTC = findViewById(R.id.recyclerViewTC);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewTC.addItemDecoration(dividerItemDecoration);
    }
}
