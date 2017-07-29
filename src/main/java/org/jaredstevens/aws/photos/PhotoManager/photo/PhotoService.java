/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.jaredstevens.aws.photos.PhotoManager.utils.AWSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {
	private static Logger LOGGER = LoggerFactory.getLogger(PhotoService.class);

	public PhotoService() {
	}

	@Value("${aws.accessKey}")
	private String accessKey;

	@Value("${aws.secretKey}")
	private String secretKey;

	@Value("${aws.sourceBucketName}")
	private String sourceBucketName;

	@Value("${aws.sourcePrefix}")
	private String sourcePrefix;

	@Value("${aws.targetBucketName}")
	private String targetBucketName;

	@Value("${aws.targetPrefix}")
	private String targetPrefix;

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

	public List<Photo> getAllPhotos(Pageable pageInfo) {
		List<Photo> photos = new ArrayList<>();
		this.photoDb.findAll(pageInfo).forEach(photos::add);
//		this.photoDb.findAll().forEach(photos::add);
		return photos;
	}

	public InputStream getRawPhoto(long photoId) {
		final Photo photo = this.get(photoId);
		final String key = photo.getUri();
		return AWSUtils.getS3InputStream(this.accessKey, this.secretKey, this.sourceBucketName, key);
	}

	public InputStream getRawThumbnail(long photoId) {
		final Photo photo = this.get(photoId);
		final String key = photo.getThumbnailUri();
		return AWSUtils.getS3InputStream(this.accessKey, this.secretKey, this.sourceBucketName, key);
	}

	public void buildIndex() {
		LOGGER.info("Listing objects...");
		final AmazonS3 client = AWSUtils.getS3Client(this.accessKey, this.secretKey);
		final String delimiter = "";
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.sourceBucketName, this.sourcePrefix, "", delimiter, 5);
		ObjectListing listing = client.listObjects(listObjectsRequest);

		boolean loop = false;
		if(listing.getObjectSummaries().size() > 0) {
			loop = true;
		}

		// Iterate over pages of keys
		while(loop) {
			LOGGER.info("Got object list. Listing {} objects.", listing.getObjectSummaries().size());
			// Process each key
			for(S3ObjectSummary prefix : listing.getObjectSummaries()) {
				LOGGER.info("Key: {}", prefix.getKey());
				// This is where the processor needs to go
				try {
					// Skip directories
					ObjectMetadata metaData = client.getObjectMetadata(this.sourceBucketName, prefix.getKey());
					if(metaData.getContentType().equals("application/x-directory")) continue;
					// Process files
					Photo photo = PhotoProcessing.processPhoto(client, this.sourceBucketName, prefix.getKey(), this.targetBucketName, this.targetPrefix);
					this.save(photo);
				} catch(IOException e) {
					LOGGER.warn("There was a problem processing file: {}", prefix.getKey(), e);
				}
			}
			if(listing.isTruncated()) {
				LOGGER.info("Getting the next page of keys...");
				listing = client.listNextBatchOfObjects(listing);
			} else {
				loop = false;
			}
		}
		LOGGER.info("Done.");
	}
}
