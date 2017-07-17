/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import org.springframework.data.repository.CrudRepository;

public interface IPhoto extends CrudRepository<Photo, Long> {
}
