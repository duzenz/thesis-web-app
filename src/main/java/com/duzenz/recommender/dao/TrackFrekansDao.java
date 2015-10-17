package com.duzenz.recommender.dao;

import java.util.List;

import com.duzenz.recommender.entities.TrackFrekans;

public interface TrackFrekansDao {

	public TrackFrekans findTrackFrekans(int trackFrekansId);
	
	public TrackFrekans findTrackFrekansWithTrackId(int trackId);
	
	public List<TrackFrekans> findTopTracks(int trackCount);
	
	public List<TrackFrekans> filterTracksWithListenCount(int minCount, int maxCount);

	public long getCountOfFilteredTracks(int minCount, int maxCount);
}
