package com.example.doan4.Activity;


import static com.example.doan4.Activity.ProductDetailActivity.currencyVN;
import static com.example.doan4.Signin.UserID;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.doan4.API.API;
import com.example.doan4.Adapter.CartAdapter;
import com.example.doan4.Adapter.ProductAdapter;
import com.example.doan4.Home;
import com.example.doan4.Models.Carts;
import com.example.doan4.Models.Product;
import com.example.doan4.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SpCartActivity extends AppCompatActivity {
    LinearLayout homeBtnn,homeBtn1,homeBtn3,homeBtn4;
    RecyclerView RecyclerView_Cart,RecyclerView_ProductLike;
    TextView sl, tongtien,demso,TextView_TotalMoney;
    public static LinearLayout LinearLayout_BuyProduct, LinearLayout_Cart;
    private Button Button_BuyProduct, Button_Order;
    private SwipeRefreshLayout SwipeRefreshLayoutCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spcart_activity);
        AnhXa();
        getdata();
        getcart_GetTotalMoney();
        onClik();
        Button_BuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpCartActivity.this, Home.class));
            }
        });
        Button_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpCartActivity.this,CheckCartActivity.class));
            }
        });
    }

    private void onClik() {
        SwipeRefreshLayoutCart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
                getcart_GetTotalMoney();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SwipeRefreshLayoutCart.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    private void getcart_GetTotalMoney() {
        API.API.GetTotalMoney(UserID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    TextView_TotalMoney.setText(currencyVN.format(Integer.parseInt(response.body())));
                } else {
                    TextView_TotalMoney.setText(currencyVN.format(0));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void getdata() {
        API.API.GetCartAccpunt(UserID).enqueue(new Callback<List<Carts>>() {
            @Override
            public void onResponse(Call<List<Carts>> call, Response<List<Carts>> response) {
                if (response.isSuccessful()) {
                    LinearLayout_Cart.setVisibility(View.VISIBLE);
                    LinearLayout_BuyProduct.setVisibility(View.GONE);
                    demso.setText(response.body().size() + "");
                    RecyclerView_Cart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    CartAdapter cart_adapterBUS = new CartAdapter(response.body(),SpCartActivity.this );
                    RecyclerView_Cart.setAdapter(cart_adapterBUS);
                } else {
                    LinearLayout_BuyProduct.setVisibility(View.VISIBLE);
                    LinearLayout_Cart.setVisibility(View.GONE);
                    demso.setText("0");
                }
            }
            @Override
            public void onFailure(Call<List<Carts>> call, Throwable t) {
            }
        });
        API.API.Get_Product().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    ProductAdapter product_adapter =new ProductAdapter(response.body(),SpCartActivity.this);
                    RecyclerView_ProductLike.setLayoutManager(new LinearLayoutManager(SpCartActivity.this,RecyclerView.VERTICAL,false));
                    RecyclerView_ProductLike.setAdapter(product_adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

    private void AnhXa() {
        RecyclerView_Cart = findViewById(R.id.RecyclerView_Cart);
        sl = findViewById(R.id.sl);
        tongtien = findViewById(R.id.tongtien);
        demso = findViewById(R.id.TextView_Amount);
        TextView_TotalMoney = findViewById(R.id.TextView_TotalMoney);
        SwipeRefreshLayoutCart = findViewById(R.id.SwipeRefreshLayoutCart);
        LinearLayout_BuyProduct = findViewById(R.id.LinearLayout_BuyProduct);
        LinearLayout_Cart = findViewById(R.id.LinearLayout_Cart);
        Button_BuyProduct = findViewById(R.id.Button_BuyProduct);
        Button_Order = findViewById(R.id.Button_Order);
        RecyclerView_ProductLike = findViewById(R.id.RecyclerView_ProductLike);
    }
}
