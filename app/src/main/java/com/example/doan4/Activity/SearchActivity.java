package com.example.doan4.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan4.API.API;
import com.example.doan4.Adapter.ProductAdapter;
import com.example.doan4.Home;
import com.example.doan4.Models.Product;
import com.example.doan4.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    ImageView ImageView_OnBack;
    RecyclerView recy_1;
    Button bt_timkiem;
    Button locsp;
    ImageButton up,down;
    EditText edt_timkiem;
    TextView tb;
    List<Product> arrayList;
    ProductAdapter arrayListAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AnhXa();
        getDta();
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Product> old1= new ArrayList<Product>();
                old1=arrayList;
                Comparator<Product> sanphamComparator=new Comparator<Product>() {
                    @Override
                    public int compare(Product sanpham, Product t1) {
                        int g1= sanpham.getPdPrice();
                        int g2= t1.getPdPrice();
//                        int g1=Integer.parseInt(a[0]);
//                        int g2=Integer.parseInt(b[0]);
                        if (g1< g2) {
                            return 1;
                        } else if (g1> g2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                };
                Collections.sort(old1,sanphamComparator);
                arrayListAdapter=new ProductAdapter(old1,SearchActivity.this);
                recy_1.setAdapter(arrayListAdapter);
            }
        });
        // sap xep theo giagiamdan
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Product> old1= new ArrayList<Product>();
                old1=arrayList;
                Comparator<Product> sanphamComparator=new Comparator<Product>() {
                    @Override
                    public int compare(Product sanpham, Product t1) {
                        int g1= sanpham.getPdPrice();
                        int g2= t1.getPdPrice();
//                        int g1=Integer.parseInt(a[0]);
//                        int g2=Integer.parseInt(b[0]);
                        if (g1> g2) {
                            return 1;
                        } else if (g1< g2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                };
                Collections.sort(old1,sanphamComparator);
                arrayListAdapter=new ProductAdapter(old1,SearchActivity.this);
                recy_1.setAdapter(arrayListAdapter);
//                recy_1.setData(old1);
//                arrayListAdapter.setAdapter(recy_1);
//                recy_1.notifyDataSetChanged();
            }
        });
        locsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timkiemkhoanggia();
            }
        });
        ImageView_OnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, Home.class));
            }
        });
        edt_timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getdata(edt_timkiem.getText().toString().trim());
            }


        });
    }
    private void getdata(String tikiem) {
        API.API.GetSearchProduct(tikiem).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    tb.setVisibility(View.GONE);
                    arrayList.clear();
                    arrayList= (ArrayList<Product>) response.body();
                    recy_1.setLayoutManager(new GridLayoutManager(SearchActivity.this,2,RecyclerView.HORIZONTAL,false));
                    arrayListAdapter =new ProductAdapter(arrayList,SearchActivity.this);
                    recy_1.setAdapter(arrayListAdapter);
                    recy_1.post(new Runnable() {
                        @Override
                        public void run() {
                            arrayListAdapter.notifyDataSetChanged();
                        }
                    });
                }else {
                    arrayList.clear();
                    arrayListAdapter=new ProductAdapter(arrayList,SearchActivity.this);
                    recy_1.setAdapter(arrayListAdapter);
                    recy_1.post(new Runnable() {
                        @Override
                        public void run() {
                            arrayListAdapter.notifyDataSetChanged();
                        }
                    });

                    tb.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }
    private void timkiemkhoanggia() {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.timkiemkhoanggia);
        Window window= dialog.getWindow();
        if(window== null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes= window.getAttributes();
        windowAttributes.gravity= Gravity.BOTTOM;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);
        EditText min = dialog.findViewById(R.id.giamin);
        EditText max = dialog.findViewById(R.id.giamax);
        Button timkiemkhgia = dialog.findViewById(R.id.timkiemkhoanggia);
        timkiemkhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int minn = Integer.parseInt(min.getText().toString().trim());
                int maxx = Integer.parseInt(max.getText().toString().trim());
                List<Product> oldlist=new ArrayList<Product>();
                for (int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i).getPdPrice()>=minn&& arrayList.get(i).getPdPrice()<=maxx){
                        oldlist.add(arrayList.get(i));
                    }
                }
                arrayListAdapter=new ProductAdapter(oldlist,SearchActivity.this);
                recy_1.setAdapter(arrayListAdapter);
                dialog.dismiss();
           }
        });
        dialog.show();
    }

    private void AnhXa() {
        ImageView_OnBack = findViewById(R.id.image);
        recy_1 = findViewById(R.id.recy_1);
        edt_timkiem= findViewById(R.id.timkiem);
        tb= findViewById(R.id.tb);
        arrayList = new ArrayList<>();
        locsp = findViewById(R.id.locsp);
        up = findViewById(R.id.giaup);
        down = findViewById(R.id.giadown);
    }
    private void getDta() {
        API.API.Get_Product().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    arrayList = (ArrayList<Product>) response.body();
                    arrayListAdapter=new ProductAdapter(arrayList,SearchActivity.this);
                    recy_1.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL,false));
                    recy_1.setAdapter(arrayListAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}
