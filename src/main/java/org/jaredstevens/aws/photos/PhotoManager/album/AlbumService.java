/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jared
 */
@Service
public class AlbumService {
	@Autowired
	private IAlbum albumDb;

	public Album save(Album album) {
		return this.albumDb.save(album);
	}

	public Album get(Long id) {
		return this.albumDb.findOne(id);
	}

	public List<Album> getAll(Pageable pageInfo) {
		List<Album> albumList = new ArrayList<>();
		this.albumDb.findAll(pageInfo).forEach(albumList::add);
		return albumList;
	}

	public void delete(Long id) {
		this.albumDb.delete(id);
	}
}
