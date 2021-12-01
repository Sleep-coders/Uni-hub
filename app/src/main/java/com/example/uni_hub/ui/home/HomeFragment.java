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

import com.amplifyframework.datastore.generated.model.Car;
import com.example.uni_hub.R;
import com.example.uni_hub.databinding.FragmentHomeBinding;
import com.example.uni_hub.game.MainMenu;
import com.example.uni_hub.ui.book_donation.Book_Donation;
import com.example.uni_hub.ui.carpool.Carpool_Home;
import com.example.uni_hub.ui.roommates.Available_Rooms;
import com.example.uni_hub.ui.utilities.Student_Utilities;

public class HomeFragment extends Fragment {
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

    public void onStart() {
        super.onStart();
        CardView carpoolCard = (CardView) context.findViewById(R.id.card_carpool);
        CardView reqRoommateCard = (CardView) context.findViewById(R.id.req_roommate_card);
        CardView studentUtilitiesCard = (CardView) context.findViewById(R.id.card_student_utilities);
        CardView bookDonationCard = (CardView) context.findViewById(R.id.card_book_donation);
        CardView gamePlayCard = (CardView) context.findViewById(R.id.card_game);
        carpoolCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carpool_intent = new Intent(context, Carpool_Home.class);
                startActivity(carpool_intent);
            }
        });
        reqRoommateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent req_roommate_intent = new Intent(context, Available_Rooms.class);
                startActivity(req_roommate_intent);
            }
        });

        studentUtilitiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent student_utilities_intent = new Intent(context, Student_Utilities.class);
                startActivity(student_utilities_intent);
            }
        });

        bookDonationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent book_donation_intent = new Intent(context, Book_Donation.class);
                startActivity(book_donation_intent);
            }
        });

        gamePlayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game_start_intent = new Intent(context, MainMenu.class);
                startActivity(game_start_intent);
            }
        });
    }


}