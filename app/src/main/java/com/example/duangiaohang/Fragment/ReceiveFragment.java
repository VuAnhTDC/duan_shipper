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
import com.example.duangiaohang.RecyclerView.CustomAdapterReceiveOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReceiveFragment extends Fragment {

    // fragment 1 **************************************
    View view;
    RecyclerView rcv_Receive;
    CustomAdapterReceiveOrder customAdapterReceiveOrder;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ArrayList<OrderData> orderDataArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receive_screen, container, false);
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
                        if (orderData.getStatusOrder()==2){
                            orderDataArrayList.add(orderData);
                            customAdapterReceiveOrder.notifyDataSetChanged();
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
        customAdapterReceiveOrder = new CustomAdapterReceiveOrder(orderDataArrayList, requireContext());
        rcv_Receive.setLayoutManager(new LinearLayoutManager(requireContext()));
        rcv_Receive.setAdapter(customAdapterReceiveOrder);
        customAdapterReceiveOrder.notifyDataSetChanged();

    }

    private void setControl() {
        rcv_Receive = view.findViewById(R.id.rcv_Receive_Fraggment);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        rcv_Receive.addItemDecoration(dividerItemDecoration);
    }
}