package com.example.doan4.Fragment;



import static com.example.doan4.Signin.UserID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan4.API.API;
import com.example.doan4.Activity.CheckOrderActivity;
import com.example.doan4.Adapter.OrderAdapter;
import com.example.doan4.Models.SOrder;
import com.example.doan4.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitForConfirmationFragment extends Fragment {
    public static RecyclerView RecyclerView_WFconfirmation;
    public static LinearLayout LinearLayout_WFconfirmation;
    public static CheckOrderActivity orderActivity;
    public WaitForConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.if4_cart, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iutuid(view);
        getdata_WCF();
    }

    public static void getdata_WCF() {
        API.API.Getorder(UserID,0).enqueue(new Callback<List<SOrder>>() {
            @Override
            public void onResponse(Call<List<SOrder>> call, Response<List<SOrder>> response) {
                if (response.isSuccessful()) {
                    RecyclerView_WFconfirmation.setVisibility(View.VISIBLE);
                    LinearLayout_WFconfirmation.setVisibility(View.GONE);
                    OrderAdapter order_adapterBUS = new OrderAdapter(response.body(),orderActivity);
                    RecyclerView_WFconfirmation.setLayoutManager(new LinearLayoutManager(orderActivity));
                    RecyclerView_WFconfirmation.setAdapter(order_adapterBUS);
                } else {
                    RecyclerView_WFconfirmation.setVisibility(View.GONE);
                    RecyclerView_WFconfirmation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<SOrder>> call, Throwable t) {

            }
        });
    }

    private void iutuid(View view) {
        orderActivity= (CheckOrderActivity) getActivity();
        RecyclerView_WFconfirmation=view.findViewById(R.id.RecyclerView_WFconfirmation);
        LinearLayout_WFconfirmation=view.findViewById(R.id.LinearLayout_WFconfirmation);
    }
}
