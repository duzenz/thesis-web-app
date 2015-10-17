package com.duzenz.recommender.dao;

import java.util.List;

import com.duzenz.recommender.entities.ArtistFrekans;

public interface ArtistFrekansDao {

	public ArtistFrekans findArtistFrekans(int artistFrekansId);
	
	public ArtistFrekans findArtistFrekansWithArtistId(int artistId);
	
	public List<ArtistFrekans> findTopArtists(int artistCount);
	
	public List<ArtistFrekans> filterArtitstWithListenCount(int minCount, int maxCount);

	public long getCountOfFilteredArtists(int minCount, int maxCount);

}
