package com.example.uni_hub.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.core.Amplify;
import com.example.uni_hub.databinding.FragmentSettingBinding;
import com.example.uni_hub.ui.auth.Login;

public class SettingFragment extends Fragment {

    private SettingViewModel notificationsViewModel;
    private FragmentSettingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void logout(View view) {
        Amplify.Auth.signOut(
                () -> {
                    Log.i("AuthQuickstart", "Signed out successfully");
                    Intent i = new Intent(requireContext(), Login.class);
                    startActivity(i);
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    Toast toast=Toast.makeText(getContext(),"error happened when trying to logout",Toast.LENGTH_LONG);
                    toast.show();
                }
        );
    }
}