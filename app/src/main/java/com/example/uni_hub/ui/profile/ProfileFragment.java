package com.example.uni_hub.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.uni_hub.R;
import com.example.uni_hub.databinding.FragmentProfileBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileFragment extends Fragment {
    private androidx.appcompat.app.AlertDialog.Builder dialogBuilder;
    private androidx.appcompat.app.AlertDialog dialog;

    private ProfileViewModel homeViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.profile_username_edit_btn)
                .setOnClickListener(v -> textHandler("Username", "text"));
        root.findViewById(R.id.profile_password_edit_btn)
                .setOnClickListener(v -> textHandler("Password", "password"));
        root.findViewById(R.id.profile_school_edit_btn)
                .setOnClickListener(v -> textHandler("School", "text"));
        root.findViewById(R.id.profile_phone_edit_btn)
                .setOnClickListener(v -> textHandler("Phone Number", "phone"));
        root.findViewById(R.id.profile_location_edit_btn)
                .setOnClickListener(v -> textHandler("Location", "text"));

        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void textHandler(String hint, String fieldType){

    }

    public void editInfo(String type){
        // Make them globally


        dialogBuilder = new AlertDialog.Builder(requireContext());
        View editUserInfo = getLayoutInflater().inflate(R.layout.profile_edit_dialog, null);

        TextInputLayout textContainer = (TextInputLayout) editUserInfo.findViewById(R.id.edit_text_container);
        TextInputEditText editInfoText = (TextInputEditText) editUserInfo.findViewById(R.id.edit_text);
        Button saveBtn = (Button) editUserInfo.findViewById(R.id.edit_btn);
        Button cancelBtn = (Button) editUserInfo.findViewById(R.id.edit_cancel_btn);

//        editInfoText.setInputType();

        dialogBuilder.setView(editUserInfo);
        dialog = dialogBuilder.create();
        dialog.show();

        saveBtn.setOnClickListener(view -> {
            String editedInfo = editInfoText.getText().toString();
            Amplify.API.
        });


        cancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });

    }
}