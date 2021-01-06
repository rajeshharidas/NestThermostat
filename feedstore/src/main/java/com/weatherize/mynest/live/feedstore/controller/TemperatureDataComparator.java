package com.weatherize.mynest.live.feedstore.controller;


import java.util.Comparator;

import com.weatherize.mynest.live.feedstore.model.TemperatureData;

public class TemperatureDataComparator implements Comparator<TemperatureData> {

    @Override
    public int compare(TemperatureData firstData, TemperatureData secondData) {          
       return firstData.getTimeofcapture().compareTo(secondData.getTimeofcapture());
    }

}