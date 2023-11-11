//package com.example.duangiaohang.RecyclerView;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.duangiaohang.Models.Image;
//import com.example.duangiaohang.Models.ListItemUserTC;
//import com.example.duangiaohang.Models.OrderData;
//import com.example.duangiaohang.R;
//
//import java.util.ArrayList;
//
//
//public class TrangChuShipperAdapter extends RecyclerView.Adapter<HomeShipperViewHolder> {
//    Context context;
//    private ArrayList<ListItemUserTC> itemUserTCArrayList;
//    OrderData orderData = new OrderData();
//
//    public TrangChuShipperAdapter(ArrayList<ListItemUserTC> itemUserTCArrayList, Context context) {
//        this.itemUserTCArrayList = itemUserTCArrayList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public HomeShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ArrayList<Image> arrImage = new ArrayList<>();
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mh_trangchu_shipper, parent, false);
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HomeShipperViewHolder holder, int position) {
//        ListItemUserTC listItemUserTC = itemUserTCArrayList.get(position);
//        if (listItemUserTC == null) {
//            Log.e("Adapter", "listItemUserTC is null at position " + position);
//            return;
//        }
//        holder.tvMaDonHangTC.setText(orderData.getIdOrder());
//        holder.tvDiaChiNhanHangTC.setText(orderData.getDeliveryAddress());
//        holder.tvDiaChiGiaoHangTC.setText(listItemUserTC.getDiaChiNhanHangTC());
//        holder.tvGiaTC.setText(orderData.()));
////
//////        String maDonHang = orderData.getIdOrder();
//////        String diaChiNhanHang = orderData.getDeliveryAddress();
//////        String diaChiGiaoHang =orderData.getDeliveryAddress(); // Bạn cần xác định trường cần lấy từ Firebase
//////        String giaCa = String.valueOf(orderData.getPrice_Order());
//////
//////        // Gán dữ liệu vào TextViews và ImageView
//////        maDonHang.setText(maDonHang);
//////
//////        maDonHang.setText(maDonHang);
//////        tvDiaChiNhanHangTC.setText(diaChiNhanHang);
//////        tvDiaChiGiaoHangTC.setText(diaChiGiaoHang);
//////        tvGiaTC.setText(giaCa);
//////        // Ánh xạ dữ liệu từ Firebase Realtime Database vào ViewHolder
//////        public void bindData (OrderProduct orderProduct){
//////            // Lấy dữ liệu từ đối tượng OrderProduct
//////            String maDonHang = orderProduct.getIdOrder();
//////            String diaChiNhanHang = orderProduct.getDeliveryAddress();
//////            String diaChiGiaoHang = orderProduct.getDeliveryAddress(); // Bạn cần xác định trường cần lấy từ Firebase
//////            String giaCa = String.valueOf(orderProduct.getPriceOrder());
//////
////////            // Gán dữ liệu vào TextViews và ImageView
////////            tvMaDonHangTC.setText(maDonHang);
////////            tvDiaChiNhanHangTC.setText(diaChiNhanHang);
////////            tvDiaChiGiaoHangTC.setText(diaChiGiaoHang);
////////            tvGiaTC.setText(giaCa);
//////
////
////            public class MHTrangChuShipperAdapter extends RecyclerView.Adapter<MHTrangChuShipperViewHolder> {
////                private List<OrderProduct> orderList;
////
////                public MHTrangChuShipperAdapter(List<OrderProduct> orderList) {
////                    this.orderList = orderList;
////                }
////
////                @NonNull
////                @Override
////                public MHTrangChuShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_item_layout, parent, false);
////                    return new MHTrangChuShipperViewHolder(view);
////                }
////
////                @Override
////                public void onBindViewHolder(@NonNull MHTrangChuShipperViewHolder holder, int position) {
////                    OrderProduct orderProduct = orderList.get(position);
////                    holder.bindData(orderProduct);
////                }
////
////                @Override
////                public int getItemCount() {
////                    return orderList.size();
////                }
////            }
////            public class MHTrangChuShipperViewHolder extends RecyclerView.ViewHolder {
////                ImageView imgOtherProductItem;
////                TextView tvMaDonHangTC, tvDiaChiNhanHangTC, tvDiaChiGiaoHangTC, tvGiaTC;
////
////                public MHTrangChuShipperViewHolder(@NonNull View itemView) {
////                    super(itemView);
////                    tvMaDonHangTC = itemView.findViewById(R.id.tvItemMaDonHang);
////                    tvGiaTC = itemView.findViewById(R.id.tvItemGiaCaTC);
////                    tvDiaChiGiaoHangTC = itemView.findViewById(R.id.tvItemDiaChiGiaoHangTC);
////                    tvDiaChiNhanHangTC = itemView.findViewById(R.id.tvItemDiaChiNhanHangTC);
////                    imgOtherProductItem = itemView.findViewById(R.id.ivItemImageSPTC);
////                }
////
////                // Ánh xạ dữ liệu từ Firebase Realtime Database vào ViewHolder
////                public void bindData(OrderProduct orderProduct) {
////                    String maDonHang = orderProduct.getIdOrder();
////                    String diaChiNhanHang = orderProduct.getDeliveryAddress();
////                    String diaChiGiaoHang = orderProduct.getDeliveryAddress(); // Bạn cần xác định trường cần lấy từ Firebase
////                    String giaCa = String.valueOf(orderProduct.getPriceOrder());
////
////                    tvMaDonHangTC.setText(maDonHang);
////                    tvDiaChiNhanHangTC.setText(diaChiNhanHang);
////                    tvDiaChiGiaoHangTC.setText(diaChiGiaoHang);
////                    tvGiaTC.setText(giaCa);
////
////                    // Thêm xử lý cho ảnh nếu có
////                    // Glide.with(itemView.getContext()).load(orderProduct.getImageUrl()).into(imgOtherProductItem);
////                }
////            }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if (itemUserTCArrayList != null) {
//            return itemUserTCArrayList.size();
//        }
//        return 0;
//    }
//
//}
