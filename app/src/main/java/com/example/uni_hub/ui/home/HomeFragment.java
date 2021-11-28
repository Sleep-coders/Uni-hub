package com.example.uni_hub.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.uni_hub.R;
import com.example.uni_hub.databinding.FragmentHomeBinding;
import com.example.uni_hub.ui.carpool.Carpool_Home;

public class HomeFragment extends Fragment{
    Activity context;

    private HomeViewModel dashboardViewModel;
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        context = getActivity();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
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