/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.configuration;

import org.springframework.data.repository.CrudRepository;

/**
 * @author jared
 */
public interface IConfig extends CrudRepository<Config, Long> {
}
