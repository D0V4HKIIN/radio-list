package com.example.radio_player.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.radio_player.R;
import com.example.radio_player.RadioData;
import com.example.radio_player.databinding.FragmentHomeBinding;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        download_radios();


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void download_radios() {
        final ObjectMapper mapper = new ObjectMapper();
        final Handler handler = new Handler();
        new Thread(() -> {
            try {
                Log.d("dl", "requesting...");
                RadioData rdata = mapper.readValue(new URL("http://4jmb.free.fr/radio/radio.json"), RadioData.class);                                 // read from url
                handler.post(() ->
                        {
                            RadioGroup radioGroup = this.binding.getRoot().findViewById(R.id.radioGroup);

                            List<List<String>> radio_list = rdata.getRadio_list();

                            for (int i = 0; i < radio_list.size(); i++) {
                                RadioButton radioButton = new RadioButton(getActivity());
                                radioButton.setText(radio_list.get(i).get(0));

                                LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                                radioButton.setLayoutParams(layout_params);
                                radioGroup.addView(radioButton);
                            }
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}