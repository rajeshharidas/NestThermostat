package com.weatherize.mynest.live.feedstore.controller;

import java.util.Comparator;

import com.weatherize.mynest.live.feedstore.model.HvacData;

public class HvacDataComparator implements Comparator<HvacData> {

    @Override
    public int compare(HvacData firstData, HvacData secondData) {          
       return firstData.getTimeofevent().compareTo(secondData.getTimeofevent());
    }

}