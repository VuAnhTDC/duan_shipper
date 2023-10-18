package com.example.duangiaohang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DangKiShipperActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICKER = 1;
    ImageView imgLoadMatTruoc;
    ImageView imgLoadMatSau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_ki_shipper);
        TextView tvDiaChi = findViewById(R.id.tvDiaChi);
        Spinner spinner = findViewById(R.id.spDiaChi);

        // Khởi tạo danh sách các lựa chọn
        List<String> options = new ArrayList<>();
        options.add("HCM");
        options.add("Hà Nội");
        options.add("Hải Phòng");

        // Khởi tạo Adapter và thiết lập cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        imgLoadMatTruoc = findViewById(R.id.imgloadMatTruoc);
        imgLoadMatSau = findViewById(R.id.imgloadMatSau);

        imgLoadMatTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImagePicker();
            }
        });
        imgLoadMatSau.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 openImagePicker();
                                             }
                                         }
        );
    }

    private void openImagePicker() {
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ;
        startActivityForResult(intent, REQUEST_IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICKER)
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    imgLoadMatTruoc.setImageBitmap(selectedImage);
                    imgLoadMatSau.setImageBitmap(selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}









