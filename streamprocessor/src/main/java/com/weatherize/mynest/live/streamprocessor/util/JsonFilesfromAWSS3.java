package com.weatherize.mynest.live.streamprocessor.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.weatherize.mynest.live.streamprocessor.model.DeviceEvent;

public class JsonFilesfromAWSS3 {

	Regions clientRegion;
	String bucketName;

	public JsonFilesfromAWSS3() {
		super();
		this.clientRegion = Regions.US_EAST_1;
		this.bucketName = "myrhnest";
	}

	public String fixDate(String dateStr) {
		try {
			dateStr = dateStr.replace("[UTC]", "");
			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date date = utcFormat.parse(dateStr);

			DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			return timeFormat.format(date);

		} catch (ParseException pex) {
			pex.printStackTrace();
		}

		return dateStr;
	}

	public List<DeviceEvent> GetDeviceEventDatafromS3() {

		List<DeviceEvent> eventData = new ArrayList<DeviceEvent>();

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider())
					.withRegion(clientRegion).build();

			ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
			ListObjectsV2Result result;
			FileWriter fw = new FileWriter("/tmp/keys.txt");

			do {
				result = s3Client.listObjectsV2(req);

				for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
					System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());

					try {

						if (!objectSummary.getKey().endsWith(".json"))
							continue;

						fw.write(objectSummary.getKey() + " \n");

						S3Object o = s3Client.getObject(bucketName, objectSummary.getKey());
						S3ObjectInputStream s3is = o.getObjectContent();

						StringWriter sw = new StringWriter();
						OutputStream opstream = new ByteArrayOutputStream();
						byte[] read_buf = new byte[1024];
						int read_len = 0;
						while ((read_len = s3is.read(read_buf)) > 0) {
							ByteArrayInputStream bs = new ByteArrayInputStream(read_buf);
							InputStreamReader sr = new InputStreamReader(bs);
							char[] read_char = new char[read_len];
							sr.read(read_char, 0, read_len);
							sw.write(read_char);
							sr.close();
							bs.close();
						}
						s3is.close();

						opstream.close();

						String resp = sw.toString();

						System.out.println(resp);

						if (resp == null || resp.isEmpty())
							continue;

						JsonObject convertedObject = new Gson().fromJson(resp, JsonObject.class);

						System.out.println("Start Here!!!");
						System.out.println(convertedObject == null ? "Not Json" : "Its a Json!");

						List<String> keys = new ArrayList<String>();
						for (Entry<String, JsonElement> e : convertedObject.entrySet()) {
							keys.add(e.getKey());
							JsonElement entry = e.getValue();
							if (entry.getAsJsonObject().get("cycles") != null) {
								JsonArray cycles = entry.getAsJsonObject().get("cycles").getAsJsonArray();
								cycles.forEach(cycle -> {
									DeviceEvent deviceEventStart = new DeviceEvent();
									deviceEventStart.setEventId(UUID.randomUUID().toString());
									deviceEventStart.setTimeStamp(fixDate(cycle.getAsJsonObject().get("startTs").getAsString()));

									Boolean isHeat = cycle.getAsJsonObject().get("heat1").getAsBoolean();
									Boolean isCool = cycle.getAsJsonObject().get("cool1").getAsBoolean();
									Boolean isfan = cycle.getAsJsonObject().get("fan").getAsBoolean();
									isfan = isfan || cycle.getAsJsonObject().get("fanCooling").getAsBoolean();

									Map<String, String> eventTraits = new HashMap<String, String>();
									if ((isHeat == true) || (isCool == true) || (isfan == true))
										eventTraits.put("hvacStatus", "ON");
									deviceEventStart.setEventTraits(eventTraits);

									DeviceEvent deviceEventHvac = new DeviceEvent();
									deviceEventHvac.setEventId(UUID.randomUUID().toString());
									deviceEventHvac.setTimeStamp(fixDate(cycle.getAsJsonObject().get("startTs").getAsString()));

									Map<String, String> eventTraitsHvac = new HashMap<String, String>();
									if (isHeat == true)
										eventTraitsHvac.put("hvacStatus", "HEATING");
									else if (isCool == true)
										eventTraitsHvac.put("hvacStatus", "COOLING");
									else if (isfan == true)
										eventTraitsHvac.put("hvacStatus", "FAN");
									deviceEventHvac.setEventTraits(eventTraitsHvac);

									DeviceEvent deviceEventEnd = new DeviceEvent();
									deviceEventEnd.setEventId(UUID.randomUUID().toString());

									String endTime = cycle.getAsJsonObject().get("caption").getAsJsonObject()
											.get("parameters").getAsJsonObject().get("endTime").getAsString();
									deviceEventEnd.setTimeStamp(fixDate(endTime));

									Map<String, String> eventTraitsEnd = new HashMap<String, String>();
									if ((isHeat == true) || (isCool == true) || (isfan == true))
										eventTraitsEnd.put("hvacStatus", "OFF");
									deviceEventEnd.setEventTraits(eventTraitsEnd);

									eventData.add(deviceEventStart);
									eventData.add(deviceEventHvac);
									eventData.add(deviceEventEnd);
								});
							}
						}
						System.out.println(keys);

					} catch (AmazonServiceException e) {
						System.err.println(e.getErrorMessage());
						System.exit(1);
					} catch (FileNotFoundException e) {
						System.err.println(e.getMessage());
						System.exit(1);
					} catch (IOException e) {
						System.err.println(e.getMessage());
						System.exit(1);
					}
				}
				// If there are more than maxKeys keys in the bucket, get a continuation token
				// and list the next objects.
				String token = result.getNextContinuationToken();
				System.out.println("Next Continuation Token: " + token);
				req.setContinuationToken(token);
			} while (result.isTruncated());

			fw.close();

		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return eventData;
	}

}
