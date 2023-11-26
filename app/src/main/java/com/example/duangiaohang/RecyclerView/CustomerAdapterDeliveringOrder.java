package com.example.duangiaohang.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.DetailDeliveringOrderActivity;
import com.example.duangiaohang.DetailReceiveOrderActivity;
import com.example.duangiaohang.Models.Customer;
import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.Models.ShopData;
import com.example.duangiaohang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerAdapterDeliveringOrder extends RecyclerView.Adapter<ManagerOderViewHolder> {
    ArrayList<OrderData> list = new ArrayList<>();
    Context context;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public CustomerAdapterDeliveringOrder(ArrayList<OrderData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ManagerOderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ManagerOderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_oder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerOderViewHolder holder, int position) {
        OrderData orderData = list.get(position);
        if (orderData != null){
            holder.tvMaDonHangTC.setText("Mã Đơn: "+orderData.getIdOrder());
            getCustomerAddress(orderData.getIdCustomer_Order(), holder);
            getImageProduct(orderData.getIdProduct_Order(), holder);
            final int finalPosition = position;
            holder.card_Item_oder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailDeliveringOrderActivity.class);
                    OrderData orderData1 = list.get(finalPosition);
                    intent.putExtra("DeliveringOrderData", orderData1);
                    System.out.println("Dữ liệu truyền đi tại OrderItem: " + orderData1);
                    context.startActivity(intent);
                }
            });
        }

    }
    private void getImageProduct(String idProductOrder, ManagerOderViewHolder holder) {
        databaseReference = firebaseDatabase.getReference("ImageProducts/"+idProductOrder);
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
    private void getCustomerAddress(String idCustomerOrder, ManagerOderViewHolder holder) {
        databaseReference = firebaseDatabase.getReference("Customer/" + idCustomerOrder);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Customer customer = snapshot.getValue(Customer.class);
                    if (customer != null) {
                        System.out.println("ShopAddress: " + customer.getAddress());
                        holder.tvDiaChiNhanHangTC.setText("địa chỉ: "+customer.getAddress());
                    }
                }else {
                    System.out.println("không tìm lấy dữ liệu shop ");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error getting shop data: " + error.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
