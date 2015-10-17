package com.duzenz.recommender.dao;

import java.util.List;

import com.duzenz.recommender.entities.Artist;


public interface ArtistDao {

	public List<Artist> findAll();
	
	public Artist findArtist(int artistId);
	
	public Artist findArtistWithLastFmId(String artistId);
	
	public long getArtistCount();
	
	public List<Artist> searchArtist(String label);
}
