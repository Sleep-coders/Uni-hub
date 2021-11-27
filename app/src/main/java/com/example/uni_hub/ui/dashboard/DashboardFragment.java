package com.example.uni_hub.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uni_hub.R;
import com.example.uni_hub.databinding.FragmentDashboardBinding;
import com.example.uni_hub.ui.carpool.Carpool_Home;

public class DashboardFragment extends Fragment{
    Activity context;

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        context = getActivity();

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onStart(){
        super.onStart();
        CardView carpoolCard = (CardView) context.findViewById(R.id.card_carpool);
        carpoolCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carpool_intent = new Intent(context,Carpool_Home.class);
                startActivity(carpool_intent);
            }
        });
    }

}