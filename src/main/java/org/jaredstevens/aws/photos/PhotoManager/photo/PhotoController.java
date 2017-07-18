/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

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

	@RequestMapping(value = "/photo/buildIndex")
	public void buildIndex() {
		this.photoService.buildIndex();
	}
}
