package com.example.duangiaohang.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.R;
import com.example.duangiaohang.RecyclerView.CustomAdapterHomeShipper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragmentHomepage extends Fragment {
    RecyclerView recyclerView;
    ArrayList<OrderData> orderDataArrayList = new ArrayList<>();
    CustomAdapterHomeShipper customAdapterHomeShipper;
    View view;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    public static HashMap<String, OrderData> listOrder = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        setControl();
        setIntiazation();
        getListItemOrder();
        return view;
    }

    private void getListItemOrder() {
        databaseReference = firebaseDatabase.getReference("OrderProduct");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderDataArrayList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshotOrder : snapshot.getChildren()){
                        OrderData orderData = snapshotOrder.getValue(OrderData.class);
                        assert orderData != null;
                        if (orderData.getStatusOrder()==1){
//                            System.out.println("lay danh sach order: "+orderData.toString());
//                            String codeOrder = snapshotOrder.getKey();
//                            System.out.println("mã Oder order: "+codeOrder);
//                            listOrder.put(codeOrder,orderData);
                            orderDataArrayList.add(orderData);
                            customAdapterHomeShipper.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Lỗi khi tải dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setIntiazation() {
        customAdapterHomeShipper = new CustomAdapterHomeShipper(orderDataArrayList, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(customAdapterHomeShipper);
        customAdapterHomeShipper.notifyDataSetChanged();

    }
    private void setControl() {
        recyclerView = view.findViewById(R.id.recyclerViewTC);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}