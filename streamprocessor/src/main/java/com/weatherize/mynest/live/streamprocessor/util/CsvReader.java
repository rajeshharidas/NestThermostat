package com.weatherize.mynest.live.streamprocessor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.weatherize.mynest.live.streamprocessor.model.TemperatureData;

public class CsvReader {
	private final Pattern pattern;

	public CsvReader(final String separator) {
		this.pattern = Pattern.compile(separator);
	}

	static Date getTimeStamp(String dateCaptured, String timeCaptured) {

		String dateTime = dateCaptured + 'T' + timeCaptured + ":00Z";
		try {

			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date date = utcFormat.parse(dateTime);

			DateFormat cstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			cstFormat.setTimeZone(TimeZone.getTimeZone("CST"));

			return cstFormat.parse(cstFormat.format(date));

		} catch (ParseException pex) {
			System.out.println(pex.getMessage());
		}
		return null;
	}
	
	public List<TemperatureData> loadCsvContentToList(
            final BufferedReader bufferedReader) throws IOException {
        try {
        	       
            return bufferedReader.lines().skip(1).map( line -> {
                final String[] lineArray = pattern.split(line);
                if (lineArray.length > 3)
                {
                Date toc = CsvReader.getTimeStamp(lineArray[0],lineArray[1]);
                return new TemperatureData
                        .Builder()
                        .timeofcapture(toc)
                        .temperature(Float.parseFloat(lineArray[2]))
                        .humidity(Float.parseFloat(lineArray[3]))
                        .mode("Hvac")
                        .timetotarget(0)
                        .build();
                }   
                return null;
            }).collect(Collectors.toList());
        } finally {
            bufferedReader.close();
        }
    }
}