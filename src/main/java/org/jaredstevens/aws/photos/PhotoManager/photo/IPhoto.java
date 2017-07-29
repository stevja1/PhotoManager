/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface IPhoto extends PagingAndSortingRepository<Photo, Long> {
}
