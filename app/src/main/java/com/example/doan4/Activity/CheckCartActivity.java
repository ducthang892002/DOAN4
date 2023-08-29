package com.example.doan4.Activity;


import static com.example.doan4.Activity.ProductDetailActivity.currencyVN;
import static com.example.doan4.Signin.UserID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.doan4.API.API;
import com.example.doan4.Adapter.Check_cart_Adapter;
import com.example.doan4.Models.AppUser;
import com.example.doan4.Models.Carts;
import com.example.doan4.Models.Notify;
import com.example.doan4.Models.SOrder;
import com.example.doan4.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckCartActivity extends AppCompatActivity {
    private ImageView ImageView_OnBack;
    private TextView TextView_Amount, TextView_Name, TextView_Number, TextView_Address, TextView_Edit, TextView_PaymentMethod, TextView_elect, TextView_TotalMoney;
    private RecyclerView RecyclerView_Cart;
    private EditText EditText_Message;
    private Button Button_Pay;
    private BottomSheetDialog bottomSheetDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_cart);
        AnhXa();
        getdata_();
        onclick();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("1","My channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1").setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("Buy Success").setContentText("Please come to the store in 5 days");
        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    private void onclick() {
        ImageView_OnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckCartActivity.this,SpCartActivity.class));
            }
        });
        TextView_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckCartActivity.this,EditAccountActivity.class));
            }
        });
        TextView_elect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.setContentView(R.layout.thanhtoan);
                CheckBox checkBox1 = bottomSheetDialog.findViewById(R.id.checkBox1);
                CheckBox checkBox2 = bottomSheetDialog.findViewById(R.id.checkBox2);
                if (checkBox2.getText().toString().equals(TextView_PaymentMethod.getText().toString())) {
                    checkBox2.setChecked(true);
                } else {
                    checkBox1.setChecked(true);
                }
                checkBox2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkBox1.setChecked(false);
                        TextView_PaymentMethod.setText(checkBox2.getText().toString());
                    }
                });
                checkBox1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkBox2.setChecked(false);
                        TextView_PaymentMethod.setText(checkBox1.getText().toString());
                    }
                });
                bottomSheetDialog.show();

            }
        });
        Button_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationManagerCompat.notify(1,notification);
                API.API.GetCartAccpunt(UserID).enqueue(new Callback<List<Carts>>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<List<Carts>> call, Response<List<Carts>> response) {
                        if (response.isSuccessful()) {
                            for (Carts cart : response.body()) {
                                SOrder order = new SOrder(0,cart.getId_Account(),cart.getId_productdetails(),cart.getQuantity(),cart.getTotalMoney()
                                        ,0,TextView_PaymentMethod.getText().toString().trim(),java.time.LocalDateTime.now()+"","Không" );

                                API.API.Postorder_(order).enqueue(new Callback<SOrder>() {
                                    @Override
                                    public void onResponse(Call<SOrder> call, Response<SOrder> response) {
                                        Toast.makeText(CheckCartActivity.this, "Đã mua sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CheckCartActivity.this,SpCartActivity.class));
                                        API.API.DeleteCart(cart.getId_Cart()).enqueue(new Callback<Carts>() {
                                            @Override
                                            public void onResponse(Call<Carts> call, Response<Carts> response) {
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
                                                API.API.GetCartAccpunt(UserID).enqueue(new Callback<List<Carts>>() {
                                                    @Override
                                                    public void onResponse(Call<List<Carts>> call, Response<List<Carts>> response) {
                                                        if (response.isSuccessful()) {
                                                            RecyclerView_Cart.setLayoutManager(new LinearLayoutManager(CheckCartActivity.this));
                                                            Check_cart_Adapter cart_adapterBUS = new Check_cart_Adapter(response.body(), CheckCartActivity.this);
                                                            RecyclerView_Cart.setAdapter(cart_adapterBUS);
                                                            TextView_Amount.setText(response.body().size() + "");
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<Carts>> call, Throwable t) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(Call<Carts> call, Throwable t) {

                                            }
                                        });
                                    }
                                    @Override
                                    public void onFailure(Call<SOrder> call, Throwable t) {

                                    }
                                });
                                Notify notify=new Notify(0,cart.getId_Account(),cart.getId_productdetails(),0,0);
                                API.API.PostNotify(notify).enqueue(new Callback<Notify>() {
                                    @Override
                                    public void onResponse(Call<Notify> call, Response<Notify> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Notify> call, Throwable t) {

                                    }
                                });
                                API.API.DeleteCart(cart.getId_Cart()).enqueue(new Callback<Carts>() {
                                    @Override
                                    public void onResponse(Call<Carts> call, Response<Carts> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Carts> call, Throwable t) {

                                    }
                                });

                            }

                            CheckCartActivity.this.onBackPressed();
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
                            API.API.GetCartAccpunt(UserID).enqueue(new Callback<List<Carts>>() {
                                @Override
                                public void onResponse(Call<List<Carts>> call, Response<List<Carts>> response) {
                                    if (response.isSuccessful()) {
                                        RecyclerView_Cart.setLayoutManager(new LinearLayoutManager(CheckCartActivity.this));
                                        Check_cart_Adapter cart_adapterBUS = new Check_cart_Adapter(response.body(), CheckCartActivity.this);
                                        RecyclerView_Cart.setAdapter(cart_adapterBUS);
                                        TextView_Amount.setText(response.body().size() + "");
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Carts>> call, Throwable t) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Carts>> call, Throwable t) {

                    }
                });
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata_();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void getdata_() {
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
        API.API.GetAccount(UserID).enqueue(new Callback<AppUser>() {
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                if (response.isSuccessful()) {
                    TextView_Name.setText(response.body().getUserName());
                    TextView_Number.setText(response.body().getUserPhone());
                    TextView_Address.setText(response.body().getUserAddress());
                    } else {
                        TextView_Number.setText("0949181356");
                        TextView_Address.setText("Nhân Hòa - Yên Mỹ");
                    }

                }
            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {

            }
        });
        API.API.GetCartAccpunt(UserID).enqueue(new Callback<List<Carts>>() {
            @Override
            public void onResponse(Call<List<Carts>> call, Response<List<Carts>> response) {
                if (response.isSuccessful()) {
                    RecyclerView_Cart.setLayoutManager(new LinearLayoutManager(CheckCartActivity.this));
                    Check_cart_Adapter cart_adapterBUS = new Check_cart_Adapter(response.body(), CheckCartActivity.this);
                    RecyclerView_Cart.setAdapter(cart_adapterBUS);
                    TextView_Amount.setText(response.body().size() + "");
                }
            }

            @Override
            public void onFailure(Call<List<Carts>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        ImageView_OnBack = findViewById(R.id.ImageView_OnBack);
        TextView_Amount = findViewById(R.id.TextView_Amount);
        TextView_Name = findViewById(R.id.TextView_Name);
        TextView_Number = findViewById(R.id.TextView_Number);
        TextView_Address = findViewById(R.id.TextView_Address);
        TextView_Edit = findViewById(R.id.TextView_Edit);
        TextView_PaymentMethod = findViewById(R.id.TextView_PaymentMethod);
        TextView_elect = findViewById(R.id.TextView_elect);
        TextView_TotalMoney = findViewById(R.id.TextView_TotalMoney);
        RecyclerView_Cart = findViewById(R.id.RecyclerView_Cart);
        EditText_Message = findViewById(R.id.EditText_Message);
        Button_Pay = findViewById(R.id.Button_Pay);
        swipeRefreshLayout = findViewById(R.id.SwipeRef);
        bottomSheetDialog = new BottomSheetDialog(this);
        
    }
}
