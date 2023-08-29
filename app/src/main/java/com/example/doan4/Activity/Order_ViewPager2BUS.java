package com.example.doan4.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doan4.Fragment.WaitForConfirmationFragment;

public class Order_ViewPager2BUS extends FragmentStateAdapter {
    public Order_ViewPager2BUS(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new WaitForConfirmationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
