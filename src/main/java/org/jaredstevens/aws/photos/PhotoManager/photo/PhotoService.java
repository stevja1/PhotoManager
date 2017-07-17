/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import org.jaredstevens.aws.photos.PhotoManager.utils.AWSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Properties;

@Service
public class PhotoService {
	private Properties properties;

	public PhotoService() {
		this.properties = new Properties();
	}

	@Value("${aws.accessKey}")
	private String accessKey;

	@Value("${aws.secretKey}")
	private String secretKey;

	@Value("${aws.bucketName}")
	private String bucketName;

	@Autowired
	private IPhoto photoDb;

	public Photo save(Photo photo) {
		return this.photoDb.save(photo);
	}

	public void delete(long photoId) {
		this.photoDb.delete(photoId);
	}

	public Photo get(long photoId) {
		return this.photoDb.findOne(photoId);
	}

	public InputStream getRawPhoto(long photoId) {
		final String key = "Photos/2009-10-28 19.13.32.jpg";
		return AWSUtils.getS3InputStream(this.accessKey, this.secretKey, this.bucketName, key);
	}
}
