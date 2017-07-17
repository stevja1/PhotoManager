/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author jared
 */
public class AWSUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(AWSUtils.class);
	public static InputStream getS3InputStream(final String accessKey, final String secretKey, final String bucket, final String key) {
		LOGGER.debug("Building the client");
		AmazonS3 s3Client = AWSUtils.getS3Client(accessKey, secretKey);
		LOGGER.debug("Opening stream to file");
		S3Object object = s3Client.getObject(
						new GetObjectRequest(bucket, key));
		InputStream objectData = object.getObjectContent();
		return objectData;
	}

	public static AmazonS3 getS3Client(final String accessKey, final String secretKey) {
		LOGGER.debug("Configuring credentials.");
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		LOGGER.debug("Building the client");
		AmazonS3ClientBuilder builder = AmazonS3Client.builder();
		builder.setCredentials(credentialsProvider);
		builder.setRegion(Regions.US_WEST_2.getName());
		return builder.build();
	}
}
