/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IPhoto extends PagingAndSortingRepository<Photo, Long> {
	List<Photo> findByAlbumListIsNull(Pageable pageInfo);
	List<Photo> findByOriginalFilename(String originalFilename);
}
