package com.example.duangiaohang;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.duangiaohang.Class.DialogForm;
import com.example.duangiaohang.Class.LoadingDialog;
import com.example.duangiaohang.Models.ShipperData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class AccountInformationActivity extends AppCompatActivity implements DialogForm.UpdateDataListener {
    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int REQUEST_IMAGE_PICKER = 123;
    ShipperData shipperData = new ShipperData();
    Uri UriStrImageAvater;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    ImageView imgAnhDaiDienShipper;
    TextView tvSoDiaThoaiShipper, tvEmailShipper, tvDiaChiShipper, tvChangePassWordShipper, tvNameShipper, tvDangXuatShipper;
    private LoadingDialog loadingDialog; // Thêm biến LoadingDialog
    Context context;
    private final int CHANGE_PASSWORD_REQUEST_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_information_layout);
        SharedPreferences sharedPreferences1 = getSharedPreferences("informationShop", Context.MODE_PRIVATE);
        String jsonShop = sharedPreferences1.getString("informationShop", "");

        Gson gson = new Gson();
        shipperData = gson.fromJson(jsonShop, ShipperData.class);
        loadingDialog = new LoadingDialog(AccountInformationActivity.this);
        context = this;
        Log.i("TAG", "hello" + shipperData.toString());
        setControl();
        getInformationShipper(shipperData.getIdShipper());
        getInfortionImageShipper(shipperData.getIdShipper());
        setEvent();

    }

    private void setEvent() {
        imgAnhDaiDienShipper.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            public void onClick(View view) {

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
//                }
                if (Build.VERSION.SDK_INT >= 25 && Build.VERSION.SDK_INT <= 30) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_PICKER);
                    } else if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_IMAGE_PICKER);
                    } else {
                        openGallery1();
                    }
                } else if (Build.VERSION.SDK_INT >= 33) {
                    if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_IMAGE_PICKER);
                    } else {
                        openGallery();
                    }
                }


            }
        });


        tvDangXuatShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                // Đăng xuất khỏi Firebase
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AccountInformationActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccountInformationActivity.this, DangNhapShipperActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                finish();
            }
        });
        tvChangePassWordShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AccountInformationActivity.this, NewChangePassWordShipperActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("idShipper", shipperData);
                startActivityForResult(intent, CHANGE_PASSWORD_REQUEST_CODE);
            }
        });

        tvNameShipper.setOnClickListener(v -> showCustomDialog("hoTenShipper"));
        tvSoDiaThoaiShipper.setOnClickListener(v -> showCustomDialog("sdtShipper"));
        tvEmailShipper.setOnClickListener(v -> showCustomDialog("emailShipper"));
        tvDiaChiShipper.setOnClickListener(v -> showCustomDialog("diaChiShipper"));

    }
    private void showCustomDialog(String fieldKey) {
        DialogForm dialogFragment = DialogForm.newInstance(fieldKey);
        dialogFragment.show(getSupportFragmentManager(), "CustomDialogFragment");
    }

    @Override
    public void onUpdateData(String fieldKey, String newData) {
        // Xử lý cập nhật dữ liệu lên Firebase

        String databasePath = "Shipper/" + shipperData.getIdShipper() + "/" + fieldKey;

        // Lấy DatabaseReference tới đường dẫn
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(databasePath);

        // Thực hiện cập nhật dữ liệu
        databaseReference.setValue(newData)
                .addOnSuccessListener(aVoid -> {
                    // Cập nhật thành công
                    Toast.makeText(AccountInformationActivity.this, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi cập nhật thất bại
                    Toast.makeText(AccountInformationActivity.this, "Lỗi khi cập nhật dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });


        // Cập nhật TextView tương ứng với fieldKey
        TextView textViewToUpdate = findTextViewByFieldKey(fieldKey);

        if (textViewToUpdate != null) {
            textViewToUpdate.setText(newData);
            updateSharedPreferences(fieldKey,newData);
        }
    }

    @Override
    public void onCancel() {

    }
    // Phương thức cập nhật SharedPreferences


    private TextView findTextViewByFieldKey(String fieldKey) {
        switch (fieldKey) {
            case "hoTenShipper":
                return findViewById(R.id.tvNameShipper);
            case "sdtShipper":
                return findViewById(R.id.tvPhoneNumberShipper);
            case "emailShipper":
                return findViewById(R.id.tvEmailShipper);
            case "diaChiShipper":
                return findViewById(R.id.tvDiaChiShipper);
            default:
                return null;
        }

    }
    private void updateSharedPreferences(String fieldKey, String newData) {
        SharedPreferences sharedPreferences = getSharedPreferences("informationShop", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonShop = sharedPreferences.getString("informationShop", "");
        ShipperData shipperData = gson.fromJson(jsonShop, ShipperData.class);
        editor.putString("informationShop", gson.toJson(shipperData));
        editor.apply();
        Toast.makeText(AccountInformationActivity.this, "Cập nhật dữ liệu share thành công", Toast.LENGTH_SHORT).show();
    }



    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    private void openGallery1() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imgAnhDaiDienShipper.setImageURI(selectedImageUri);

            // Gán giá trị cho UriStrImageAvater
            UriStrImageAvater = selectedImageUri;

            // Tải ảnh lên Firebase Storage và lưu URL vào Firebase Realtime Database
            uploadImageToFirebaseStorage();
        }
    }


    private void uploadImageToFirebaseStorage() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        String[] part = UriStrImageAvater.getLastPathSegment().split("/");
        Log.i("TAG", "IMG" + UriStrImageAvater);
        StorageReference imgRef = storageRef.child("ImagerUserShipper/" + shipperData.getSdtShipper() + "/" + (part[part.length - 1]));
        UploadTask uploadTask = imgRef.putFile(UriStrImageAvater);
        uploadTask.addOnCompleteListener(taskSnapshot -> {
            imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                shipperData.setUrlImgShopAvatar(uri.toString());
                updateUrlImageInDatabase(shipperData.getUrlImgShopAvatar());


            }).addOnFailureListener(e -> {
                Toast.makeText(context, "Lỗi khi tải ảnh lên" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void updateUrlImageInDatabase(String imageUrl) {
        // Kiểm tra xem shipperData và idShipper có giá trị không
        if (shipperData != null && !shipperData.getIdShipper().isEmpty()) {
            // Thực hiện cập nhật URL vào Firebase Realtime Database
            DatabaseReference shipperRef = firebaseDatabase.getReference("Shipper").child(shipperData.getIdShipper());
            shipperRef.child("urlImgShopAvatar").setValue(imageUrl)
                    .addOnSuccessListener(aVoid -> {
                        // Cập nhật thành công
                        Toast.makeText(AccountInformationActivity.this, "Cập nhật URL thành công", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý khi cập nhật thất bại
                        Toast.makeText(AccountInformationActivity.this, "Lỗi khi cập nhật URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Xử lý khi shipperData hoặc idShipper không hợp lệ
            Toast.makeText(AccountInformationActivity.this, "Không thể cập nhật URL, dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }


    private void setControl() {

        imgAnhDaiDienShipper = findViewById(R.id.imgAvartarShipper);
        tvSoDiaThoaiShipper = findViewById(R.id.tvPhoneNumberShipper);
        tvEmailShipper = findViewById(R.id.tvEmailShipper);
        tvDiaChiShipper = findViewById(R.id.tvDiaChiShipper);
        tvChangePassWordShipper = findViewById(R.id.tvChangePasswordShipper);
        tvDangXuatShipper = findViewById(R.id.tvLogOutShipper);
        tvNameShipper = findViewById(R.id.tvNameShipper);

    }

    public void getInformationShipper(String idShipper) {

        tvNameShipper.setText(shipperData.getHoTenShipper());
        tvEmailShipper.setText(shipperData.getEmailShipper());
        tvSoDiaThoaiShipper.setText(shipperData.getSdtShipper());
        tvDiaChiShipper.setText(shipperData.getDiaChiShipper());
    }

    public void getInfortionImageShipper(String idShop) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Shipper/" + idShop);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("TAG", "MESS" + snapshot);
                // Kiểm tra xem có dữ liệu và có các nút con không
                if (snapshot.exists() && snapshot.hasChildren()) {
                    shipperData = snapshot.getValue(ShipperData.class);

                    assert shipperData != null;
                    if (shipperData.getUrlImgShopAvatar().isEmpty()) {
                        imgAnhDaiDienShipper.setImageResource(R.drawable.user_shield_480px);
                    } else {
                        Picasso.get().load(shipperData.getUrlImgShopAvatar()).placeholder(R.drawable.ic_launcher_background).into(imgAnhDaiDienShipper);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountInformationActivity.this, "No Image Avatar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

