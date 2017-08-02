/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.album;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.jaredstevens.aws.photos.PhotoManager.photo.Photo;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * An Album entity. This defines the structure of a photo album table and its relationship
 * to records in the photo table.
 * @author jared
 */
@Entity
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long albumId;
	private String name;
	private String description;
	private ZonedDateTime createDate;
//	@JsonManagedReference
	@ManyToMany(targetEntity = Photo.class, cascade = {CascadeType.ALL})
	@JoinTable(name = "album_photo", joinColumns = { @JoinColumn(name = "album_id") },
					inverseJoinColumns = { @JoinColumn(name = "photo_id") })
	private List<Photo> photoList;

	public Album() {
		this.albumId = 0;
		this.name = "";
		this.description = "";
		this.createDate = ZonedDateTime.now();
		this.photoList = new ArrayList<>();
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
	public ZonedDateTime getCreateDate() {
		return createDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
	public void setCreateDate(ZonedDateTime createDate) {
		if(createDate == null) {
			this.createDate = ZonedDateTime.now();
		} else {
			this.createDate = createDate;
		}
	}

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}
}
