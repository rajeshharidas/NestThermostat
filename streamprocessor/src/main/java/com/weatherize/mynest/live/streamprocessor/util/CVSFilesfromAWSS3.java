package com.weatherize.mynest.live.streamprocessor.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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
import com.weatherize.mynest.live.streamprocessor.model.TemperatureData;

public class CVSFilesfromAWSS3 {

	Regions clientRegion;
	String bucketName;
	
	public CVSFilesfromAWSS3() {
		super();
		  this.clientRegion = Regions.US_EAST_1;
	      this.bucketName = "myrhnest";
	}

	public List<TemperatureData> GetTemperatureDatafromS3(){
		
		List<TemperatureData> sensorData = new ArrayList<TemperatureData>();
		
		try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(clientRegion)
                    .build();

            ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
            ListObjectsV2Result result;

            do {
                result = s3Client.listObjectsV2(req);

                for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                    System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());
                    
                    try {
                    	
                    	if (!objectSummary.getKey().endsWith(".csv")) continue;
                    	
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
                        
                        System.out.println(sw.toString());
                        
                        StringReader sr = new StringReader(sw.toString());
                        sw.close();
                        
                        BufferedReader bif = new BufferedReader(sr);
                                                
                        CsvReader csvReader = new CsvReader(",");
                        sensorData = csvReader.loadCsvContentToList(bif);
                        bif.close();
                        
                    } catch (AmazonServiceException e) {
                        System.err.println(e.getErrorMessage());
                        System.exit(1);
                    } catch (FileNotFoundException e) {
                        System.err.println(e.getMessage());
                        System.exit(1);
                    } 
                    catch (IOException e) {
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
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
		
		return sensorData;
	}
	
	
}
