package com.example.radio_player.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.radio_player.MediaController;
import com.example.radio_player.R;
import com.example.radio_player.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ScrollView scrollView;
    private int scrollY = 0;

    @Override
    public void onPause() {
        scrollY = scrollView.getScrollY();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RadioGroup radioGroup = root.findViewById(R.id.radioGroup);
        scrollView = root.findViewById(R.id.scrollView);

        MediaController.show_radios(radioGroup);

        scrollView.post(() -> scrollView.scrollTo(0, scrollY));


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}