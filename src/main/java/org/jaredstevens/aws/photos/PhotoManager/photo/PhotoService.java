/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
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
}
