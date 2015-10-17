package com.duzenz.recommender.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "track")
public class Track {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false, unique = true, name = "track_id")
	private String trackId;

	@Column(name = "track_name")
	private String trackName;

	@Column(name = "is_requested_blob", columnDefinition = "int default '0'")
	private int isRequestedBlob;

	@Column(name = "blob_content", columnDefinition = "blob")
	private String blobContent;

	@Column(name = "artist_mbid")
	private String artistMbid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsRequestedBlob() {
		return isRequestedBlob;
	}

	public void setIsRequestedBlob(int isRequestedBlob) {
		this.isRequestedBlob = isRequestedBlob;
	}

	public String getBlobContent() {
		return blobContent;
	}

	public void setBlobContent(String blobContent) {
		this.blobContent = blobContent;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getArtistMbid() {
		return artistMbid;
	}

	public void setArtistMbid(String artistMbid) {
		this.artistMbid = artistMbid;
	}

}
