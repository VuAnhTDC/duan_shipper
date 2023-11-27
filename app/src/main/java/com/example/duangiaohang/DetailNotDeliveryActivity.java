package com.example.duangiaohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duangiaohang.Class.ShowMessage;
import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.Models.ProductData;
import com.example.duangiaohang.Models.ShipperData;
import com.example.duangiaohang.Models.ShopData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailNotDeliveryActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    OrderData orderData = new OrderData();
    ImageView img_order;
    TextView tv_codeOder, tv_nameProduct, tv_priceOrder,tv_nameShop,tv_addressShop,tv_phoneShop, tv_emailShop,tv_nameCustomer,tv_phoneCustomer,tv_addressCustomer,tv_note;
    Button btn_giaoLai;
    Toolbar toolbarOrder;
    Context context;
    ShipperData shipperData = new ShipperData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_not_delivery);
        shipperData = new ShipperData(1,"0346008801","jdjjd","1234@gmail.com","0346008801","hdjkdjd","ndkdkd","HCM","https://firebasestorage.googleapis.com/v0/b/duandd2.appspot.com/o/Imageshipper%2F0346008801%2F1000024219?alt=media&token=55f7c84f-5e2f-4b23-b19a-4503885684fe","https://firebasestorage.googleapis.com/v0/b/duandd2.appspot.com/o/ImageShipper%2F0346008801%2F1000024219?alt=media&token=c00c6293-5186-4ee9-b4ea-fba074e7e006","123AnhEm@","https://firebasestorage.googleapis.com/v0/b/duandd2.appspot.com/o/ImagerUserShipper%2F0346008801%2F1000023785?alt=media&token=d20237da-9a3b-4db0-8507-3e82844c3d22");
        setControl();
        context = this;
        setIniazation();
        setEvent();
    }

    private void setEvent() {
        btn_giaoLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                if(orderData.getStatusOrder()==6) {
                    builder.setMessage("Bạn có muốn giao lại đơn hàng này không ?");
                }
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateOrderStatus(3);
                        ShowMessage.showMessage("đã xác nhận giao lại",context);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
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

    }

    private void setIniazation() {
        Intent intent = getIntent();
        orderData = (OrderData) intent.getSerializableExtra("notDeliveryOrderData");
//        System.out.println("nhận order tại chi tiết đơn hàng" + orderData.toString());
        getImageProduct(orderData.getIdProduct_Order());
        tv_note.setText("ghi chú:" + orderData.getNote_Order());
        tv_codeOder.setText("Mã Đơn: "+orderData.getIdOrder());
        tv_priceOrder.setText("Giá: "+orderData.getPrice_Order()+" VNĐ");
        getProduct(orderData.getIdShop_Order(),orderData.getIdProduct_Order());
        getShop(orderData.getIdShop_Order());
        setSupportActionBar(toolbarOrder);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    private void setControl() {
        btn_giaoLai = findViewById(R.id.btn_giaolaij_Not_Delivery_Order);
        img_order = findViewById(R.id.img_product_Not_Delivery_Order);
        tv_codeOder = findViewById(R.id.tv_codeorders_Not_Delivery_Order);
        tv_nameProduct = findViewById(R.id.tv_nameproduct_Not_Delivery_Order);
        tv_priceOrder = findViewById(R.id.tv_priceproduct_Not_Delivery_Order);
        tv_nameShop = findViewById(R.id.tv_nameshop_Not_Delivery_Order);
        tv_addressShop = findViewById(R.id.tv_addressshop_Not_Delivery_Order);
        tv_phoneShop = findViewById(R.id.tv_phoneshop_Not_Delivery_Order);
        tv_emailShop = findViewById(R.id.tv_emailshop_Not_Delivery_Order);
        tv_nameCustomer = findViewById(R.id.tv_nameCustomer_Not_Delivery_Order);
        tv_phoneCustomer = findViewById(R.id.tv_phoneCustomer_Not_Delivery_Order);
        tv_addressCustomer = findViewById(R.id.tv_addressCustomer_Not_Delivery_Order);
        tv_note = findViewById(R.id.tv_note_Not_Delivery_Order);
        toolbarOrder = findViewById(R.id.toolbar_Detail_Not_Delivery_Order);

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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateOrderStatus(int newStatus) {
        databaseReference = FirebaseDatabase.getInstance().getReference("OrderProduct/"+orderData.getIdOrder());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    OrderData orderData1 = snapshot.getValue(OrderData.class);
                    if (orderData1.getStatusOrder() == 6){
                        databaseReference.child("statusOrder").setValue(newStatus);
//                        databaseReference.child("idShiper_Order").setValue(shipperData.getIdShipper());
                        finish();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getProduct(String idShop,String idProduct){
        databaseReference = firebaseDatabase.getReference("Product/"+idShop+"/"+idProduct);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ProductData productData = snapshot.getValue(ProductData.class);
                    System.out.println("OrderDetailsNeedDelivereActivity: du lieu san pham : " + productData.toString());
                    tv_nameProduct.setText("Tên Sản Phẩm: " + productData.getNameProduct());
                }else {
                    System.out.println("OrderDetailsNeedDelivereActivity: khong tim thay san pham");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getShop(String idShop){
        databaseReference = firebaseDatabase.getReference("Shop/"+idShop);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ShopData shopData = snapshot.getValue(ShopData.class);
                    System.out.println("OrderDetailsNeedDelivereActivity: du lieu san pham : " + shopData.toString());
                    tv_addressShop.setText("địa chỉ: " + shopData.getShopAddress());
                    tv_emailShop.setText("Email: "+shopData.getShopEmail());
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
}