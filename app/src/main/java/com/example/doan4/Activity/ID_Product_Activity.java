package com.example.doan4.Activity;

import static com.example.doan4.Adapter.CategoryAdapter.Id_Category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan4.API.API;
import com.example.doan4.Adapter.ProductAdapter;
import com.example.doan4.Models.Product;
import com.example.doan4.R;
import com.example.doan4.Widgets;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ID_Product_Activity extends AppCompatActivity {
    ImageView image;
    ProductAdapter product_adapter;
    ArrayList<Product> productsArrayList = new ArrayList<>();
    RecyclerView recy_loaiSP;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_product);
        AnhXa();
        getData();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ID_Product_Activity.this, Widgets.class));
            }
        });
    }

    private void getData() {
        API.API.GetId_Product(Id_Category).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productsArrayList = (ArrayList<Product>) response.body();
                product_adapter =new ProductAdapter(productsArrayList,ID_Product_Activity.this);
                recy_loaiSP.setLayoutManager(new LinearLayoutManager(ID_Product_Activity.this,RecyclerView.VERTICAL,false));
                recy_loaiSP.setAdapter(product_adapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        image = findViewById(R.id.image);
        recy_loaiSP = findViewById(R.id.recy_loaiSP);
    }
}
