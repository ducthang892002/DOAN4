package com.example.doan4.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.doan4.My;
import com.example.doan4.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CheckOrderActivity extends AppCompatActivity {
    private ImageView ImageView_OnBack;
    private TabLayout TabLayout_Order;
    private ViewPager2 ViewPager_Order;
    private Order_ViewPager2BUS order_viewPager2BUS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        onclick();
    }

    private void onclick() {
        ImageView_OnBack = findViewById(R.id.ImageView_OnBack);
        TabLayout_Order = findViewById(R.id.TabLayout_Order);
        ViewPager_Order = findViewById(R.id.ViewPager_Order);
        ImageView_OnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckOrderActivity.this, My.class));
            }
        });
        order_viewPager2BUS = new Order_ViewPager2BUS(this);
        ViewPager_Order.setAdapter(order_viewPager2BUS);
        TabLayout_Order.setTabTextColors(this.getResources().getColor(R.color.black),this.getResources().getColor(R.color.purple_200));
        new TabLayoutMediator(TabLayout_Order, ViewPager_Order, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Chờ shop xác nhận ");
                        break;
                    case 1:
                        tab.setText("Đang giao");
                        break;
                    case 2:
                        tab.setText("Đã giao");
                        break;
                    case 3:
                        tab.setText("Đã hủy");
                        break;
                    default:
                        break;

                }
            }
        }).attach();
    }
}
