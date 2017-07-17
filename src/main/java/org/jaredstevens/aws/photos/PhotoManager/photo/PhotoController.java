/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoController {
	@Autowired
	private PhotoService photoService;

	@RequestMapping("/photo/{id}")
	public byte[] getRawPhoto() {
		return null;
	}
}
