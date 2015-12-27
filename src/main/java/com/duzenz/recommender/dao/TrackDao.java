package com.duzenz.recommender.dao;

import java.util.List;

import com.duzenz.recommender.entities.Track;

public interface TrackDao {

    public List<Track> findAll();

    public Track findTrack(int trackId);

    public Track findTrackWithLastFmId(String trackId);

    public long getTrackCount();

    public List<Track> searchTrack(String label);

    public Track updateTrack(Track track);

}
