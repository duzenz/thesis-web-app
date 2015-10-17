package com.duzenz.recommender.dao;

import java.util.List;

import com.duzenz.recommender.entities.UserArtist;


public interface UserArtistDao {

	public UserArtist findUserArtist(int l);
	
	public List<UserArtist> findArtistsOfUser(int userId);
	
	public long findListenedArtistCountOfUser(int userId);
	
}
