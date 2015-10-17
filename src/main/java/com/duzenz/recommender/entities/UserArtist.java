package com.duzenz.recommender.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="user_artist")
public class UserArtist 
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private DataUser user;
	
	@ManyToOne
	@JoinColumn(name = "artist_id")  
	private Artist artist;
	
	@Column(name="listen_count")
	private int listenCount;
	
	@Override
	public String toString() {
		return "UserArtist [id=" + id + ", user=" + user + ", artist=" + artist
				+ ", listenCount=" + listenCount + "]";
	}
	
	@Transient
	private float recommendationValue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DataUser getUser() {
		return user;
	}

	public void setUser(DataUser user) {
		this.user = user;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public int getListenCount() {
		return listenCount;
	}

	public void setListenCount(int listenCount) {
		this.listenCount = listenCount;
	}

	public float getRecommendationValue() {
		return recommendationValue;
	}

	public void setRecommendationValue(float recommendationValue) {
		this.recommendationValue = recommendationValue;
	}
	
}
