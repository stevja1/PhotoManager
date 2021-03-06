/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager.photo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jaredstevens.aws.photos.PhotoManager.album.Album;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * A Photo Entity. This defines the table in the database that stores photo information.
 * The actual photo data is stored in AWS S3. Information needed for displaying a gallery
 * is available here.
 * @author jared
 */
@Entity
public class Photo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long photoId;
	@JsonBackReference
	@ManyToMany(mappedBy = "photoList", cascade = CascadeType.ALL)
	private List<Album> albumList;
	private String name;
	private String description;
	private String originalFilename;
	private String thumbnailUri;
	@Column(unique = true)
	private String uri;
	private int width;
	private int height;
	private long size;
	private ZonedDateTime dateTaken;
	private ZonedDateTime updated;

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public List<Album> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(List<Album> albumList) {
		this.albumList = albumList;
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

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getThumbnailUri() {
		return thumbnailUri;
	}

	public void setThumbnailUri(String thumbnailUri) {
		this.thumbnailUri = thumbnailUri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
	public ZonedDateTime getDateTaken() {
		return dateTaken;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
	public void setDateTaken(ZonedDateTime dateTaken) {
		this.dateTaken = dateTaken;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
	public ZonedDateTime getUpdated() {
		return updated;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
	public void setUpdated(ZonedDateTime updated) {
		this.updated = updated;
	}
}
