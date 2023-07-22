package com.example.radio_player;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RadioData {
    @JsonProperty("radio_list")
    public List<List<String>> radio_list;

    @JsonProperty("titles")
    public List<List<String>> titles;

    public List<List<String>> getRadio_list() {
        return radio_list;
    }

    public List<List<String>> getTitles() {
        return titles;
    }
}
