package com.example.duangiaohang.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.OrderDetailsNeedDelivereActivity;
import com.example.duangiaohang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class CustomAdapterCompleteDelivered extends RecyclerView.Adapter<DeliveredSuccessfullyHolder> {
    ArrayList<OrderData> orderDataArrayList = new ArrayList<>();
    Context context;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public CustomAdapterCompleteDelivered(ArrayList<OrderData> orderDataArrayList, Context context) {
        this.orderDataArrayList = orderDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeliveredSuccessfullyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeliveredSuccessfullyHolder(LayoutInflater.from(context).inflate(R.layout.item_delivered_successfully, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredSuccessfullyHolder holder, int position) {
        OrderData orderData = orderDataArrayList.get(position);
        if (orderData != null) {
            holder.tvMaDon.setText("Mã Đơn: "+orderData.getIdOrder());
            getImageProduct(orderData.getIdProduct_Order(), holder);
            final int finalPosition = position;
//            holder.card_Item_oder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, OrderDetailsNeedDelivereActivity.class);
//                    OrderData orderData1 = orderDataArrayList.get(finalPosition);
//                    intent.putExtra("orderData1", orderData1);
//                    System.out.println("Dữ liệu truyền đi tại OrderItem: " + orderData1);
//                    context.startActivity(intent);
//                }
//            });
        }
    }
    private void getImageProduct(String idProduct, DeliveredSuccessfullyHolder holder) {
        databaseReference = firebaseDatabase.getReference("ImageProducts/"+idProduct);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        if (snapshot1.exists()){
                            for (DataSnapshot dataImage: snapshot1.getChildren()){
                                String image = Objects.requireNonNull(dataImage.getValue()).toString();
                                System.out.println("đường linh ảnh sản phẩm: " + image.toString());
                                Picasso.get().load(image).placeholder(R.drawable.ic_downloading_24).into(holder.imgOtherProductItem);
                                return;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDataArrayList.size();
    }
}
