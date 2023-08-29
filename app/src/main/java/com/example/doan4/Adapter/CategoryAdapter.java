package com.example.doan4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.doan4.Activity.ID_Product_Activity;
import com.example.doan4.Models.Category;
import com.example.doan4.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.theloai_viewholder> {
    List<Category> theLoaiList;
    Context context;
    public static int Id_Category = 0;
    public CategoryAdapter(ArrayList<Category> list, Context context) {
        this.theLoaiList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryAdapter.theloai_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent, false);
        return new theloai_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.theloai_viewholder holder, int position) {
        Category theLoai = theLoaiList.get(position);
        Glide.with(context.getApplicationContext()).load(theLoai.getPictureCategory()) .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).error(R.drawable.guccicloud).into(holder.imageView);
        holder.txt_name.setText(theLoai.getNameCategory());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_theloai(theLoai);
            }
        });
    }
    private void click_theloai(Category theLoai) {
        Intent intent = new Intent(context, ID_Product_Activity.class);
        Id_Category = theLoai.getId_Category();
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (theLoaiList != null){
            return theLoaiList.size();
        }
        return 0;
    }

    public class theloai_viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_name;
        public theloai_viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ImageView_Category);
            txt_name=itemView.findViewById(R.id.TextView_NameCategory);
        }
    }
}
