package com.example.doan4.Adapter;

import static com.example.doan4.Activity.ProductDetailActivity.currencyVN;
import static com.example.doan4.Fragment.WaitForConfirmationFragment.getdata_WCF;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.doan4.API.API;
import com.example.doan4.Activity.ProductDetailActivity;
import com.example.doan4.Models.Product;
import com.example.doan4.Models.ProductDetails;
import com.example.doan4.Models.SOrder;
import com.example.doan4.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Orderr_Viewholder>{
    List<SOrder> orders;

    public static int IDCATEGORY;
    public static int IDPRODUCT;
    Activity activity;
    public OrderAdapter(List<SOrder> orders, Activity activity) {
        this.orders = orders;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrderAdapter.Orderr_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new Orderr_Viewholder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.Orderr_Viewholder holder, int position) {
        SOrder order_ = orders.get(position);
        API.API.GetProductDetail(order_.getId_productdetails()).enqueue(new Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                if (response.isSuccessful()) {
                    ProductDetails productDetails = response.body();
                    if (productDetails.getPromotionalprice() == 0) {
                        holder.TextView_Price.setText(currencyVN.format(productDetails.getPrice()));
                        holder.TextView_Promotionalprice.setVisibility(View.GONE);
                    } else {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(currencyVN.format(productDetails.getPrice()));
                        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                        spannableStringBuilder.setSpan(strikethroughSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.TextView_Price.setText(spannableStringBuilder);
                        holder.TextView_Promotionalprice.setText(currencyVN.format(productDetails.getPromotionalprice()));
                    }
                    holder.TextView_Size.setText("Khích cỡ: " + productDetails.getSize());
                    API.API.getchitiet(productDetails.getId_product()).enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            Glide.with(activity).load(response.body().getPictureProduct()).apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15))).error(R.drawable.gucci1).into(holder.ImageView_Product);
                            holder.TextView_NameProduct.setText(response.body().getNameProduct());
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProductDetails> call, Throwable t) {

            }
        });
        holder.TextView_Amount.setText("Số lượng: " + order_.getQuantity());
        holder.TextView_TotalMoney.setText("Tổng tiền: " + currencyVN.format(order_.getTotalMoney()));
        holder.TextView_Datetime.setText(order_.getDateTime());
        switch (order_.getStatus()) {
            case 0:
                holder.Button_CancelOrder.setText("Hủy đơn hàng");
                CanelOrder(order_,holder,3);
                break;
            case 1:
                holder.Button_CancelOrder.setVisibility(View.GONE);
                break;
            case 2:
                holder.Button_CancelOrder.setText("Mua lại sản phẩm");
                BuyBackProduct(holder,order_);
                break;
            case 3:
                holder.Button_CancelOrder.setText("Mua lại sản phẩm");
                BuyBackProduct(holder,order_);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CanelOrder(SOrder order, Orderr_Viewholder holder, int status) {
        SOrder order1=new SOrder(order.getId_Order(),order.getId_Account(),order.getId_productdetails(),order.getQuantity(),order.getTotalMoney(),status,order.getPaymentMethod(),java.time.LocalDateTime.now() + "",order.getNotes());
        holder.Button_CancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Bạn có muốn hủy đơn này không")
                        .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                API.API.Putorder_(order.getId_Order()).enqueue(new Callback<SOrder>() {
                                    @Override
                                    public void onResponse(Call<SOrder> call, Response<SOrder> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(activity, "Bạn đã hủy đơn hàng ", Toast.LENGTH_SHORT).show();
                                            getdata_WCF();
//                                        getdata_CF();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SOrder> call, Throwable t) {

                                    }
                                });
                            }

                        });

                builder.create();
                builder.show();
            }
        });
    }
    public void BuyBackProduct(Orderr_Viewholder holder,SOrder order){
        holder.Button_CancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.API.GetProductDetail(order.getId_productdetails()).enqueue(new Callback<ProductDetails>() {
                    @Override
                    public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                        IDPRODUCT = response.body().getId_product();
                        API.API.getchitiet(response.body().getId_product()).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                IDCATEGORY = response.body().getId_danhmuc();
                                activity.startActivity(new Intent(activity, ProductDetailActivity.class));
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {

                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<ProductDetails> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orders != null) {
            return orders.size();
        }
        return 0;
    }

    public class Orderr_Viewholder extends RecyclerView.ViewHolder {
        ImageView ImageView_Product;
        TextView TextView_NameProduct, TextView_Size, TextView_Price, TextView_Promotionalprice, TextView_Amount, TextView_TotalMoney, TextView_Datetime;
        Button Button_CancelOrder;
        public Orderr_Viewholder(@NonNull View itemView) {
            super(itemView);
            ImageView_Product = itemView.findViewById(R.id.ImageView_Product);
            TextView_NameProduct = itemView.findViewById(R.id.TextView_NameProduct);
            TextView_Size = itemView.findViewById(R.id.TextView_Size);
            TextView_Price = itemView.findViewById(R.id.TextView_Price);
            TextView_Promotionalprice = itemView.findViewById(R.id.TextView_Promotionalprice);
            TextView_Amount = itemView.findViewById(R.id.TextView_Amount);
            TextView_TotalMoney = itemView.findViewById(R.id.TextView_TotalMoney);
            TextView_Datetime = itemView.findViewById(R.id.TextView_Datetime);
            Button_CancelOrder = itemView.findViewById(R.id.Button_CancelOrder);
        }
    }
}
