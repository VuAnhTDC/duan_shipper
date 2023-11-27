package com.example.duangiaohang;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duangiaohang.Class.ShowMessage;
import com.example.duangiaohang.Models.Customer;
import com.example.duangiaohang.Models.OrderData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DetailDeliveringOrderActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    OrderData orderData = new OrderData();
    ImageView img_order;
    TextView tv_codeOder,tv_nameCustomer,tv_addressCustomer,tv_phoneCustomer;
    Button btn_xacnhan,btn_map,btn_phone,btn_giaoSau;
    Toolbar toolbarOrder;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_delivering_order);
        setControl();
        context = this;
        setIniazation();
        setEvent();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateOrderStatus(int newStatus) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        databaseReference = FirebaseDatabase.getInstance().getReference("OrderProduct/"+orderData.getIdOrder());
        databaseReference.child("statusOrder").setValue(newStatus);
        databaseReference.child("orderTimeComplete").setValue(formattedDateTime);
    }
    private void updateOrderStatusDeliveredLater(int newStatus) {
        databaseReference = FirebaseDatabase.getInstance().getReference("OrderProduct/"+orderData.getIdOrder());
        databaseReference.child("statusOrder").setValue(newStatus);
    }
    private void getCustomer(String idCustomerOrder){
        databaseReference = firebaseDatabase.getReference("Customer/"+idCustomerOrder);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Customer customer = snapshot.getValue(Customer.class);
                    System.out.println("OrderDetailsNeedDelivereActivity: du lieu san pham : " + customer.toString());
                    tv_addressCustomer.setText("địa chỉ: " + customer.getAddress());
                    tv_phoneCustomer.setText("phone: " + customer.getId());
                    tv_nameCustomer.setText("tên khách hàng: " + customer.getName());
                }else {
                    System.out.println("OrderDetailsNeedDelivereActivity: khong tim thay san pham");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getImageProduct(String idProduct) {
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
                                Picasso.get().load(image).placeholder(R.drawable.ic_downloading_24).into(img_order);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                if(orderData.getStatusOrder()==3) {
                    builder.setMessage(" xác nhận giao hàng thành công");
                }
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateOrderStatus(4);
                        finish();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_giaoSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                if(orderData.getStatusOrder()==3) {
                    builder.setMessage(" đơn hàng này sẽ giao lại sau");
                }
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateOrderStatusDeliveredLater(6);
                        finish();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMaps(tv_addressCustomer.getText().toString().substring(9));
            }
        });
    }
    private void openGoogleMaps(String address) {
        Log.d("YourTag", "Opening Google Maps with address: " + address);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void setIniazation() {
        Intent intent = getIntent();
        orderData = (OrderData) intent.getSerializableExtra("DeliveringOrderData");
        System.out.println("nhận order tại chi tiết đơn hàng" + orderData.toString());
        getImageProduct(orderData.getIdProduct_Order());
        tv_codeOder.setText("Mã Đơn: "+orderData.getIdOrder());
        getCustomer(orderData.getIdCustomer_Order());
        setSupportActionBar(toolbarOrder);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    private void setControl() {
        btn_xacnhan = findViewById(R.id.btn_xacnhan_DeliveringOrder);
        img_order = findViewById(R.id.img_product_DeliveringOrder);
        btn_map = findViewById(R.id.btn_map_DeliveringOrder);
        btn_giaoSau = findViewById(R.id.btn_giaoSau_DeliveringOrder);
        tv_addressCustomer = findViewById(R.id.tv_address_DeliveringOrder);
        btn_phone = findViewById(R.id.btn_phone_DeliveringOrder);
        tv_phoneCustomer = findViewById(R.id.tv_phone_DeliveringOrder);
        tv_codeOder = findViewById(R.id.tv_codeorders_DeliveringOrder);
        tv_nameCustomer = findViewById(R.id.tv_nameCustomer_DeliveringOrder);
        toolbarOrder = findViewById(R.id.toolbar_Detail_DeliveringOrder);

    }
}