package com.example.doan4.Activity;

import static com.example.doan4.Adapter.ProductAdapter.Id_product;
import static com.example.doan4.Signin.UserID;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan4.API.API;
import com.example.doan4.API.GetMoney;
import com.example.doan4.Adapter.SizeAdapter;
import com.example.doan4.Home;
import com.example.doan4.Models.Carts;
import com.example.doan4.Models.Product;
import com.example.doan4.Models.ProductDetails;
import com.example.doan4.Models.Size_Adapter;
import com.example.doan4.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
//    new Locale("vi", "VN")
    public static NumberFormat currencyVN = NumberFormat.getCurrencyInstance(Locale.US);
    TextView tensp,giatiensp,luotban,luotxem,TextView_Content,giachinh,sl;
    ImageView img_back;
    LinearLayout themvaogiohang,mua;

    GetMoney get_money;
    public static final int ACTION_DOWN = 0;

    private int tong2 ;
    Context context;
    private int tong3 = 1;
    private int tong4 ;
    RadioButton textView_Size;

    private int Id_productdetails_;
    ImageView imageView1,imageView2,imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_product_detail);
        AnhXa();
        getdata();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, Home.class));
            }
        });
        themvaogiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomThem();
            }
        });
    }
    private void bottomThem(){
        View view = getLayoutInflater().inflate(R.layout.dialog_addcart1,null);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        RecyclerView phanloai = view.findViewById(R.id.phanloai);
        ImageView hinhanhloai = view.findViewById(R.id.hinhanhloai);
        TextView tien = view.findViewById(R.id.tien);
        ImageView tru=  view.findViewById(R.id.tru);
        ImageView cong = view.findViewById(R.id.cong);
        TextView sl = view.findViewById(R.id.sl);
        Button Button_addCart = view.findViewById(R.id.Button_addCart);
        TextView tongtien = view.findViewById(R.id.tongtien);
        Button_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carts cart=new Carts(0,UserID,Id_productdetails_,tong3,tong3*tong2,"");
                API.API.PostCart(cart).enqueue(new Callback<List<Carts>>() {
                    @Override
                    public void onResponse(Call<List<Carts>> call, Response<List<Carts>> response) {
                        if (response.isSuccessful()) {
                            Carts cart1 = response.body().get(0);
                            Carts cart_ = new Carts(cart1.getId_Cart(),
                                    cart1.getId_Account(), cart1.getId_productdetails(),
                                    cart1.getQuantity() + cart.getQuantity(),
                                    cart1.getTotalMoney() + cart.getTotalMoney(), cart1.getNotes());
                            API.API.PutCart(response.body().get(0).getId_Cart(),cart_).enqueue(new Callback<Carts>() {
                                @Override
                                public void onResponse(Call<Carts> call, Response<Carts> response) {

                                }

                                @Override
                                public void onFailure(Call<Carts> call, Throwable t) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Carts>> call, Throwable t) {

                    }

                });
                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getdata();
                            Toast.makeText(ProductDetailActivity.this, "Đã thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                    },2000);
            }
        });
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tong3 = tong3 + 1;
                sl.setText(tong3 + "");
            }
        });
            tong4 = tong2 * tong3;
        tongtien.setText(currencyVN.format(tong4));
        tru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tong3 != 1) {
                        tong3 = tong3 - 1;
                        sl.setText(tong3 + "");
                        tongtien.setText(""+tong3*tong2);
                        tongtien.setText(currencyVN.format(tong4));
                    }
                }
            });
        API.API.GetProductDetails(Id_product).enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {
                if (response.isSuccessful()) {
                    int Position = response.body().size()-1;
                    String aaa = currencyVN.format(response.body().get(0).getPromotionalprice()) + " - " + currencyVN.format(response.body().get(Position).getPromotionalprice());
                    Glide.with(getApplicationContext()).load(response.body().get(0).getPicture1()).into(hinhanhloai);
                    tien.setText(aaa);
                    SizeAdapter size_adapterBUS = new SizeAdapter(response.body(), context, get_money);
                    phanloai.setLayoutManager(new GridLayoutManager(ProductDetailActivity.this,5));
                    phanloai.setAdapter(size_adapterBUS);
                }
            }
            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
            }
        });
        get_money = new GetMoney() {
            @Override
            public void Get_Money(int Id_productdetails, int Price, int Promotionalprice) {
                Id_productdetails_ = Id_productdetails;
                if (Promotionalprice == 0) {
                    tong2 = Price;
                    tong4 = tong2 * tong3;
                    tongtien.setText(currencyVN.format(tong4));
                } else {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(currencyVN.format(Price));
                    StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                    spannableStringBuilder.setSpan(strikethroughSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tong2 = Promotionalprice;
                    tong4 = tong2 * tong3;
                    tongtien.setText(currencyVN.format(tong4));
                }
            }
        };
    }
    private void AnhXa() {
        tensp = findViewById(R.id.tensp);
        giatiensp = findViewById(R.id.giatiensp);
        luotban = findViewById(R.id.luotban);
        imageView1 = findViewById(R.id.imageview1);
        luotxem = findViewById(R.id.luotxem);
        TextView_Content = findViewById(R.id.TextView_Content);
        giachinh = findViewById(R.id.giachinh);
        themvaogiohang = findViewById(R.id.themvaogiohang);
        img_back = findViewById(R.id.img_back);
        textView_Size = findViewById(R.id.textView_Size);
    }

    private void getdata() {
        API.API.getchitiet(Id_product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    tensp.setText(product.getNameProduct());
                    TextView_Content.setText(product.getContent());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
        API.API.GetProductDetails(Id_product).enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {
                if (response.isSuccessful()) {
                    int Position = response.body().size() - 1;
                    Glide.with(getApplicationContext())
                            .load(response.body().get(0).getPicture1())
                            .error(R.drawable.gucciblnk)
                            .into(imageView1);
                    if (response.body().get(0).getPromotionalprice() == 0 || response.body().get(Position).getPromotionalprice() == 0) {
                        giatiensp.setText(currencyVN.format(response.body().get(0).getPrice()) + "-" + currencyVN.format(response.body().get(Position).getPrice()));
                        giachinh.setVisibility(View.INVISIBLE);
                    } else {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(currencyVN.format(response.body().get(0).getPrice()) + "-" + currencyVN.format(response.body().get(Position).getPrice()));
                        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();//Kỹ thuật tạo dấu gạch ngang
                        spannableStringBuilder.setSpan(strikethroughSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        giatiensp.setText(spannableStringBuilder);
                        giachinh.setText(currencyVN.format(response.body().get(0).getPromotionalprice()) + "-" + currencyVN.format(response.body().get(Position).getPromotionalprice()));
                    }
                }

            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
            }
        });

    }


}
