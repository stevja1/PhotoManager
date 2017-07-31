/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.album;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface IAlbum extends PagingAndSortingRepository<Album, Long> {
}
