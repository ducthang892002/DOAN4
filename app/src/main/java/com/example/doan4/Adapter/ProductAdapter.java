package com.example.doan4.Adapter;

import static com.example.doan4.Activity.ProductDetailActivity.currencyVN;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan4.API.API;
import com.example.doan4.Activity.ProductDetailActivity;
import com.example.doan4.Models.Product;
import com.example.doan4.Models.ProductDetails;
import com.example.doan4.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.productdetail_viewholder> {
    List<Product> list;

    Context context;
    public static int Id_product = 0;

    public ProductAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;

    }
    @NonNull
    @Override
    public productdetail_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new productdetail_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.productdetail_viewholder holder, int position) {
        Product product =list.get(position);
        holder.TextView_NameProduct.setText(product.getNameProduct());
        Glide.with(context.getApplicationContext()).load(product.getPictureProduct()).error(R.drawable.guccicloud).into(holder.ImageView_Product);
        //holder.TextView_Price.setText(String.valueOf(product.getPdPrice()));
        holder.TextView_Content.setText(product.getContent());
        API.API.GetProductDetails(product.getId_product()).enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {
                if (response.isSuccessful()) {
                    int Position = response.body().size() - 1;
                    if (response.body().get(0).getPromotionalprice() == 0 || response.body().get(Position).getPromotionalprice() == 0) {
                        holder.TextView_Price.setText(currencyVN.format(response.body().get(0).getPrice()) + "-" + currencyVN.format(response.body().get(Position).getPrice()));
                        holder.TextView_Promotionalprice.setVisibility(View.INVISIBLE);
                    } else {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(currencyVN.format(response.body().get(0).getPrice()) + "-" + currencyVN.format(response.body().get(Position).getPrice()));
                        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                        spannableStringBuilder.setSpan(strikethroughSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.TextView_Price.setText(spannableStringBuilder);
                        holder.TextView_Promotionalprice.setText(currencyVN.format(response.body().get(0).getPromotionalprice()) + "-" + currencyVN.format(response.body().get(Position).getPromotionalprice()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {

            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_ProductDetail(product);
            }
        });

    }
    private void click_ProductDetail(Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        Id_product = product.getId_product();
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    public class productdetail_viewholder extends RecyclerView.ViewHolder {
        TextView TextView_NameProduct,TextView_Price,TextView_Promotionalprice,TextView_Content,TextView_Scale,TextView_View;
        ImageView ImageView_Product;
        CardView layout;
        public productdetail_viewholder(@NonNull View itemView) {
            super(itemView);
            TextView_Promotionalprice = itemView.findViewById(R.id.TextView_Promotionalprice);
            TextView_Price = itemView.findViewById(R.id.TextView_Price);
            TextView_Scale =itemView.findViewById(R.id.TextView_Scale);
            layout =itemView.findViewById(R.id.layout_item_product);
            TextView_NameProduct = itemView.findViewById(R.id.TextView_NameProduct);
            TextView_View =itemView.findViewById(R.id.TextView_View);
            ImageView_Product =itemView.findViewById(R.id.ImageView_Product);
            TextView_Content = itemView.findViewById((R.id.TextView_Content));
        }
    }
}
