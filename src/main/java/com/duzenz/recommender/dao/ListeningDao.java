package com.duzenz.recommender.dao;

import java.util.Date;
import java.util.List;

import com.duzenz.recommender.entities.Listening;

public interface ListeningDao {

    public Listening findListening(int id);

    public List<Listening> findListeningOfTrack(int trackId);

    public long getAgeIntervalListenCount(int ageMin, int ageMax);

    public long getUserCountWithListeningCount(long minListenCount, long maxListenCount);

    public long getTrackListeningCountBetweenDates(Date minDate, Date maxDate);

    public long getListeningCountOfTrackBetweenDates(int trackId, Date minDate, Date maxDate);

    public List<Listening> getListeningOfTrackBetweenDates(int trackId, Date minDate, Date maxDate);

    public long getListeningCountOfUserBetweenDates(int userId, Date minDate, Date maxDate);

    public List<Listening> getListeningOfUserBetweenDates(int userId, Date minDate, Date maxDate);

    public Listening insertListening(Listening listening);
}
