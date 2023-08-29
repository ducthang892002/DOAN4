package com.example.doan4.Adapter;

import static com.example.doan4.Activity.ProductDetailActivity.currencyVN;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan4.API.API;
import com.example.doan4.Models.Carts;
import com.example.doan4.Models.Product;
import com.example.doan4.Models.ProductDetails;
import com.example.doan4.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Check_cart_Adapter extends RecyclerView.Adapter<Check_cart_Adapter.View_Holder>{
    List<Carts> carts;
    Context context;
    public Check_cart_Adapter(List<Carts> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }
    @NonNull
    @Override
    public Check_cart_Adapter.View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check, parent, false);
        return new View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Check_cart_Adapter.View_Holder holder, int position) {
        Carts cart = carts.get(position);
        holder.TextView_Amount.setText("Số lượng: "+cart.getQuantity());
        API.API.GetProductDetail(cart.getId_productdetails()).enqueue(new Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                ProductDetails productDetails = response.body();
                holder.TextView_Price.setText(currencyVN.format(productDetails.getPrice()));
                holder.TextView_Promotionalprice.setText(currencyVN.format(productDetails.getPromotionalprice()));
                holder.TextView_Size.setText("Khích cỡ: " + productDetails.getSize());
                API.API.getchitiet(productDetails.getId_product()).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Glide.with(context.getApplicationContext()).load(productDetails.getPicture1()).error(R.drawable.gucci1).into(holder.ImageView_Product);
                        holder.TextView_NameProduct.setText(response.body().getNameProduct());
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

    @Override
    public int getItemCount() {
        if (carts != null) {
            return carts.size();
        }
        return 0;
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        ImageView ImageView_Product, ImageView_MinusQuantity, ImageView_AddAmount;
        TextView TextView_NameProduct, TextView_Size, TextView_Price, TextView_Promotionalprice, TextView_Amount;
        public View_Holder(@NonNull View itemView) {
            super(itemView);
            ImageView_Product = itemView.findViewById(R.id.ImageView_Product);
            ImageView_MinusQuantity = itemView.findViewById(R.id.ImageView_MinusQuantity);
            ImageView_AddAmount = itemView.findViewById(R.id.ImageView_AddAmount);
            TextView_NameProduct = itemView.findViewById(R.id.TextView_NameProduct);
            TextView_Size = itemView.findViewById(R.id.TextView_Size);
            TextView_Price = itemView.findViewById(R.id.TextView_Price);
            TextView_Promotionalprice = itemView.findViewById(R.id.TextView_Promotionalprice);
            TextView_Amount = itemView.findViewById(R.id.TextView_Amount);
        }
    }
}
