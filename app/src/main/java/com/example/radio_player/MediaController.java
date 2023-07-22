package com.example.radio_player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MediaController {

    static private MediaPlayer mediaPlayer;
    static private Boolean mediaPlayerIsSet = false;
    static public RadioData rdata;
    static public int selectedIndex;


    static public void play(int index) {
        selectedIndex = index;
        try {
            if (mediaPlayerIsSet) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            mediaPlayerIsSet = true;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.setDataSource(rdata.getRadio_list().get(index).get(1));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public void show_radios(RadioGroup radioGroup) {
        final ObjectMapper mapper = new ObjectMapper();
        final Handler handler = new Handler();
        new Thread(() -> {
            try {
                rdata = mapper.readValue(new URL("http://4jmb.free.fr/radio/radio.json"), RadioData.class);                                 // read from url
                handler.post(() -> {

                    List<List<String>> radio_list = rdata.getRadio_list();

                    for (int i = 0; i < radio_list.size(); i++) {
                        RadioButton radioButton = new RadioButton(radioGroup.getContext());
                        radioButton.setText(radio_list.get(i).get(0));

                        LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                        radioButton.setLayoutParams(layout_params);
                        radioGroup.addView(radioButton);
                    }

                    ((RadioButton) radioGroup.getChildAt(selectedIndex)).setChecked(true);

                    radioGroup.setOnCheckedChangeListener((radioGrp, i) -> {
                        View radioButton = radioGrp.findViewById(i);
                        int index = radioGrp.indexOfChild(radioButton);

                        MediaController.play(index);
                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
