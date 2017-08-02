/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.album;

import org.jaredstevens.aws.photos.PhotoManager.photo.Photo;
import org.jaredstevens.aws.photos.PhotoManager.photo.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author jared
 */
@RestController
public class AlbumController {
	private static Logger LOGGER = LoggerFactory.getLogger(AlbumController.class);
	@Autowired
	private AlbumService albumService;

	@Autowired
	private PhotoService photoService;

	@RequestMapping(value = "/album/{id}")
	public Album get(@PathVariable Long id){
		return this.albumService.get(id);
	}

	@RequestMapping(value = "/album")
	public List<Album> get(Pageable pageInfo){
		return this.albumService.getAll(pageInfo);
	}

	@RequestMapping(value = "/album", method = RequestMethod.POST)
	public Album save(@RequestBody Album album){
		return this.albumService.save(album);
	}

	@RequestMapping(value = "/album", method = RequestMethod.PUT)
	public Album update(@RequestBody Album album){
		return this.albumService.save(album);
	}

	@RequestMapping(value = "/album/addPhoto/{albumId}/{photoId}", method = RequestMethod.PUT)
	public void addPhoto(@PathVariable Long albumId, @PathVariable Long photoId) {
		LOGGER.info("Adding photo {} to album {}", photoId, albumId);
		Photo photo = this.photoService.get(photoId);
		Album album = this.albumService.get(albumId);
		album.getPhotoList().add(photo);
		this.albumService.save(album);
	}

	@RequestMapping(value = "/album/addPhotos/{albumId}", method = RequestMethod.PUT)
	public void addPhotos(@PathVariable Long albumId, @RequestBody Long[] photoIds) {
		LOGGER.info("Adding photos {} to album {}", photoIds, albumId);
		List<Photo> photos = this.photoService.getPhotosInList(Arrays.asList(photoIds));
		Album album = this.albumService.get(albumId);
		album.getPhotoList().addAll(photos);
		this.albumService.save(album);
	}

	@RequestMapping(value = "/album/removePhoto/{albumId}/{photoId}")
	public void removePhoto(@PathVariable Long albumId, @PathVariable Long photoId) {
		Album album = this.albumService.get(albumId);
		Photo photoToRemove = null;
		for(Photo photo : album.getPhotoList()) {
			if(photo.getPhotoId() == photoId) {
				photoToRemove = photo;
				break;
			}
		}
		if(photoToRemove != null) {
			album.getPhotoList().remove(photoToRemove);
			this.albumService.save(album);
		}
	}

	@RequestMapping(value = "/album/{albumId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		this.albumService.delete(id);
	}
}
