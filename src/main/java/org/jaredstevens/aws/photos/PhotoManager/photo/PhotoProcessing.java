/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import org.imgscalr.Scalr;
import org.jaredstevens.aws.photos.PhotoManager.utils.AWSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;

/**
 * @author jared
 */
public class PhotoProcessing {
	private static Logger LOGGER = LoggerFactory.getLogger(PhotoProcessing.class);
	/**
	 * This method does the following:
	 * 1. Copy image to target bucket/prefix with new target name
	 * 	a. Need a hash to base new filename off off
	 * 	b. Need way of calculating new key based on prefix
	 * 2. Create a thumbnail
	 * 	a. Need thumbnail hash
	 * 3. Build a Photo object and return it with the relevant information
	 */
	public static Photo processPhoto(final AmazonS3 client, final String sourceBucketName, final String sourceKey, final String targetBucketName, final String targetPrefix) throws IOException {
		final Photo photo = new Photo();
		LOGGER.info("Gathering info about file: {}", sourceKey);
		S3Object object = client.getObject(sourceBucketName, sourceKey);

		InputStream inStream = object.getObjectContent();
		byte[] rawImageData = IOUtils.toByteArray(inStream);
		final String targetKey = PhotoProcessing.getHash(ByteSource.wrap(rawImageData));
		final String targetUri = String.format("%s/%s", targetPrefix, targetKey);
		LOGGER.info("Building photo object");
		BufferedImage src = ImageIO.read(new ByteArrayInputStream(rawImageData));
		photo.setHeight(src.getHeight());
		photo.setWidth(src.getWidth());
		photo.setOriginalFilename(sourceKey);
		photo.setUri(targetUri);
		photo.setUpdated(ZonedDateTime.now());
		photo.setSize(client.getObjectMetadata(sourceBucketName, sourceKey)
						.getContentLength());
		inStream.close();

		client.copyObject(sourceBucketName, sourceKey, targetBucketName, targetUri);
		// See: https://stackoverflow.com/a/4528136/772430
		BufferedImage thumbnail = Scalr.resize(src, 256);
		LOGGER.info("Writing temp data to disk because this SDK sucks.");
		final File thumbnailFile = new File("TempThumbnailFile");
		ImageIO.write(thumbnail, "jpg", thumbnailFile);
		LOGGER.info("Uploading thumbnail.");
		final String thumbnailKey = PhotoProcessing.getHash(thumbnailFile);
		photo.setThumbnailUri(thumbnailKey);
		PutObjectResult result = client.putObject(targetBucketName, targetPrefix+"/"+thumbnailKey, thumbnailFile);
		LOGGER.info("Wrote thumbnail.");
		return photo;
	}

	public static String getHash(File file) throws IOException {
		HashCode hash = com.google.common.io.Files.asByteSource(file).hash(Hashing.sha256());
		return hash.toString()
						.toUpperCase();
	}

	public static String getHash(ByteSource byteSource) throws IOException {
		HashCode hash = byteSource.hash(Hashing.sha256());
		return hash.toString()
						.toUpperCase();
	}

	public static String getHash(InputStream inStream) throws IOException {
		ByteSource byteSource = ByteSource.wrap(IOUtils.toByteArray(inStream));
		HashCode hash = byteSource.hash(Hashing.sha256());
		return hash.toString()
						.toUpperCase();
	}
}
