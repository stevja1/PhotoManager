/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jared
 */
@RestController
public class ConfigController {
	@Autowired
	private ConfigService configService;

	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public void save(@RequestBody Config config) {
		this.configService.save(config);
	}

	@RequestMapping(value = "/config", method = RequestMethod.PUT)
	public void update(@RequestBody Config config) {
		this.configService.save(config);
	}

	@RequestMapping(value = "/config")
	public Config get() {
		return this.configService.get();
	}

	@RequestMapping(value = "/config", method = RequestMethod.DELETE)
	public void delete() {
		this.configService.delete();
	}
}
