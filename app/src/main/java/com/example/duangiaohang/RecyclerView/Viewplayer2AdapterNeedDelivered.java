//package com.example.duangiaohang.RecyclerView;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.duangiaohang.Models.Image;
//import com.example.duangiaohang.R;
//
//import java.util.ArrayList;
//
//public class Viewplayer2AdapterNeedDelivered extends RecyclerView.Adapter<Viewplayer2NeedDelivereViewHolder> {
//    private ArrayList<Image> imageArrayList;
//    Context context;
//
//    public Viewplayer2AdapterNeedDelivered(ArrayList<Image> imageArrayList, Context context) {a
//        this.imageArrayList = imageArrayList;
//        context = context;
//    }
//
//    @NonNull
//    @Override
//    public Viewplayer2NeedDelivereViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new Viewplayer2NeedDelivereViewHolder(LayoutInflater.from(context).inflate(R.layout.cri_photo_item_need_delivered, parent, false));
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Viewplayer2NeedDelivereViewHolder holder, int position) {
//        Image imageList = imageArrayList.get(position);
//        if (imageList == null){
//            return;
//        }
//        holder.imgVpNeedDeli.setImageDrawable(imageList.getUrlImage());
//    }
//
//    @Override
//    public int getItemCount() {
//      if (imageArrayList != null){
//          return  imageArrayList.size();
//      }
//      return 0;
//    }
//
//    public class Viewplayer2NeedDelivereViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgVpNeedDeli;
//
//        public Viewplayer2NeedDelivereViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imgVpNeedDeli = itemView.findViewById(R.id.imgcrineeddelivered);
//
//        }
//    }
//}