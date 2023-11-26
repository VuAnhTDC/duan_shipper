package com.example.duangiaohang.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.duangiaohang.Models.Image;
import com.example.duangiaohang.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VPOrderDetailsNeedAdapter /*extends PagerAdapter*/ {
//    Context context;
//    // ArrayList <Image> imageArrayList = new ArrayList<>();
//    ArrayList<String> imgArrayListURL = new ArrayList<>();
//
//
//    public VPOrderDetailsNeedAdapter(ArrayList<String> imgArrayListURL, Context context) {
//        this.context = context;
//        this.imgArrayListURL = imgArrayListURL;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//
//        // View  view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_order_details_need_delivere,container,false);
//        ImageView imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Picasso.get().load(imgArrayListURL.get(position)).into(imageView);
//        container.addView(imageView);
//        return imageView;
//    }
//
//
//    @Override
//    public int getCount() {
//        return imgArrayListURL.size();
//
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView((ImageView) object);
////        super.destroyItem(container, position, object);
//    }
}
