/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class PhotoController {
	private static Logger LOGGER = LoggerFactory.getLogger(PhotoController.class);

	@Autowired
	private PhotoService photoService;

	@RequestMapping(value = "/photo", method = RequestMethod.POST)
	public void save(@RequestBody Photo photo) {
		this.photoService.save(photo);
	}

	@RequestMapping(value = "/photo", method = RequestMethod.PUT)
	public void update(@RequestBody Photo photo) {
		this.photoService.save(photo);
	}

	@RequestMapping(value = "/photo")
	public List<Photo> getAllPhotos(Pageable pageInfo) {
		return this.photoService.getAllPhotos(pageInfo);
	}

	@RequestMapping(value = "/photo/withoutAlbum")
	public List<Photo> getPhotosWithoutAlbum(Pageable pageInfo) {
		return this.photoService.getPhotosWithoutAlbum(pageInfo);
	}

	@RequestMapping(value = "/photo/{photoId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getRawPhoto(@PathVariable long photoId) {
		final InputStream inStream = this.photoService.getRawPhoto(photoId);
		try {
			byte[] rawImage = IOUtils.toByteArray(inStream);
			inStream.close();
			return rawImage;
		} catch(IOException e) {
			LOGGER.warn("IOException thrown while writing image to output stream.", e);
			return null;
		}
	}

	@RequestMapping(value = "/thumbnail/{photoId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getRawThumbnail(@PathVariable long photoId) {
		final InputStream inStream = this.photoService.getRawThumbnail(photoId);
		try {
			byte[] rawImage = IOUtils.toByteArray(inStream);
			inStream.close();
			return rawImage;
		} catch(IOException e) {
			LOGGER.warn("IOException thrown while writing image to output stream.", e);
			return null;
		}
	}

	@RequestMapping(value = "/photo/buildIndex")
	public void buildIndex() {
		this.photoService.buildIndex();
	}
}
