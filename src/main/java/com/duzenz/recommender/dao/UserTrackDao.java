package com.duzenz.recommender.dao;

import java.util.List;

import com.duzenz.recommender.entities.UserTrack;

public interface UserTrackDao {

    public UserTrack findUserTrack(int userTrackId);

    public List<UserTrack> findTracksOfUser(int userId);

    public UserTrack findRandomTrackOfUser(int userId);

    public int findListenedTrackCountOfUser(int userId);

    public List<UserTrack> findUserTrackRow(int userId, int trackId);

    public UserTrack insert(UserTrack userTrack);

    public void saveUserTracksAsCsv();

}
