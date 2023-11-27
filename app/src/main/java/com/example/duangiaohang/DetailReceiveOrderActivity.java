package com.example.duangiaohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.example.duangiaohang.Models.ShopData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailReceiveOrderActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    OrderData orderData = new OrderData();
    ImageView img_order;
    TextView tv_codeOder,tv_nameShop,tv_addressShop,tv_phoneShop;
    Button btn_xacnhan,btn_map,btn_phone;
    Toolbar toolbarOrder;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_manager_order);
        setControl();
        context = this;
        setIniazation();
        setEvent();
    }

    private void setEvent() {
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                if(orderData.getStatusOrder()==2) {
                    builder.setMessage("LƯU Ý: đã nhận đơn hàng từ cửa hàng mới được chọn Ok");
                }
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //cập nhật trạng thái đơn hàng thành chờ lấy hàng
                        updateOrderStatus(3);
                        //chuyển về màn hình OrderList
                        ShowMessage.showMessage("nhận đơn hàng thành công",context);

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
                openGoogleMaps(tv_addressShop.getText().toString().substring(9));
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
    private void updateOrderStatus(int newStatus) {
        databaseReference = FirebaseDatabase.getInstance().getReference("OrderProduct/"+orderData.getIdOrder());
        databaseReference.child("statusOrder").setValue(newStatus);
    }
    private void getShop(String idShopOrder){
        databaseReference = firebaseDatabase.getReference("Shop/"+idShopOrder);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ShopData shopData = snapshot.getValue(ShopData.class);
                    System.out.println("OrderDetailsNeedDelivereActivity: du lieu san pham : " + shopData.toString());
                    tv_addressShop.setText("địa chỉ: " + shopData.getShopAddress());
                    tv_phoneShop.setText("phone: " + shopData.getIdShop());
                    tv_nameShop.setText("tên cửa hàng: " + shopData.getShopName());
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
        databaseReference.addValueEventListener(new ValueEventListener() {
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setIniazation() {
        Intent intent = getIntent();
        orderData = (OrderData) intent.getSerializableExtra("managerOrderData");
        System.out.println("nhận order tại chi tiết đơn hàng" + orderData.toString());
        getImageProduct(orderData.getIdProduct_Order());
        tv_codeOder.setText("Mã Đơn: "+orderData.getIdOrder());
        getShop(orderData.getIdShop_Order());
        setSupportActionBar(toolbarOrder);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        
    }

    private void setControl() {
        btn_xacnhan = findViewById(R.id.btn_xacnhan_managerOrder);
        img_order = findViewById(R.id.img_product_ReceiveOrder);
        btn_map = findViewById(R.id.btn_map_managerOrder);
        tv_addressShop = findViewById(R.id.tv_address_managerOrder);
        btn_phone = findViewById(R.id.btn_phone_managerOrder);
        tv_phoneShop = findViewById(R.id.tv_phone_managerOrder);
        tv_codeOder = findViewById(R.id.tv_codeorders_managerOrder);
        tv_nameShop = findViewById(R.id.tv_nameShop_managerOrder);
        toolbarOrder = findViewById(R.id.toolbar_Detail_ManagerOrder);
    }
}