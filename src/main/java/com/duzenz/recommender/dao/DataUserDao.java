package com.duzenz.recommender.dao;

import com.duzenz.recommender.entities.DataUser;


public interface DataUserDao {

	public DataUser findRandom();
	
	public DataUser find(int id);
	
	public DataUser findwithLastFmId(String lastFmId);
	
	public long getUserCount();
	
	public long getUserCountBetweenAgeInterval(int minAge, int maxAge);
	
	public DataUser createLastFmUser(DataUser user);
}
