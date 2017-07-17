/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.configuration;

import org.jaredstevens.aws.photos.PhotoManager.PhotoManagerApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Properties;

/**
 * @author jared
 */
@Service
public class ConfigService {
	@Autowired
	private IConfig configDb;

	public ConfigService() {
	}

	public void save(Config config) {
		this.configDb.save(config);
	}

	public Config get() {
		Iterator<Config> it = this.configDb
						.findAll()
						.iterator();
		if(it.hasNext()) {
			return it.next();
		} else return null;
	}

	public void delete() {
		Config config = this.configDb
						.findAll()
						.iterator()
						.next();
		this.configDb.delete(config.getId());
	}
}
