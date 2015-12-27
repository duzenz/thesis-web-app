package com.duzenz.recommender.web.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.dao.ListeningDao;
import com.duzenz.recommender.entities.Listening;

@Controller
@RequestMapping("/rest/listening/")
public class ListeningResource {

    @Autowired
    private ListeningDao listeningDao;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Listening findListening(@PathVariable("id") int id) {
        return listeningDao.findListening(id);
    }

    @RequestMapping(value = "/listen/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Listening> findListeningOfTrack(@PathVariable("id") int trackId) {
        return listeningDao.findListeningOfTrack(trackId);
    }

    @RequestMapping(value = "/age/{ageMin}/{ageMax}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public long getTrackCountOgAgeInterval(@PathVariable("ageMin") int ageMin, @PathVariable("ageMax") int ageMax) {
        return listeningDao.getAgeIntervalListenCount(ageMin, ageMax);
    }

    @RequestMapping(value = "/listenUser/{min}/{max}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public long getUserCountWithListeningCount(@PathVariable("min") long min, @PathVariable("max") long max) {
        return listeningDao.getUserCountWithListeningCount(min, max);
    }

    @RequestMapping(value = "/interval/{minDate}/{maxDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public long getUserCountWithListeningCount(@PathVariable("minDate") @DateTimeFormat(pattern = "yyyyMMdd") Date minDate, @PathVariable("maxDate") @DateTimeFormat(pattern = "yyyyMMdd") Date maxDate) {
        return listeningDao.getTrackListeningCountBetweenDates(minDate, maxDate);
    }

    @RequestMapping(value = "/track/interval/count/{trackId}/{minDate}/{maxDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public long getListeningCountOfTrackBetweenDates(@PathVariable("trackId") int trackId, @PathVariable("minDate") @DateTimeFormat(pattern = "yyyyMMdd") Date minDate, @PathVariable("maxDate") @DateTimeFormat(pattern = "yyyyMMdd") Date maxDate) {
        return listeningDao.getListeningCountOfTrackBetweenDates(trackId, minDate, maxDate);
    }

    @RequestMapping(value = "/track/interval/{trackId}/{minDate}/{maxDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Listening> getListeningOfTrackBetweenDates(@PathVariable("trackId") int trackId, @PathVariable("minDate") @DateTimeFormat(pattern = "yyyyMMdd") Date minDate, @PathVariable("maxDate") @DateTimeFormat(pattern = "yyyyMMdd") Date maxDate) {
        return listeningDao.getListeningOfTrackBetweenDates(trackId, minDate, maxDate);
    }

    @RequestMapping(value = "/user/interval/count/{userId}/{minDate}/{maxDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public long getListeningCountOfUserBetweenDates(@PathVariable("userId") int userId, @PathVariable("minDate") @DateTimeFormat(pattern = "yyyyMMdd") Date minDate, @PathVariable("maxDate") @DateTimeFormat(pattern = "yyyyMMdd") Date maxDate) {
        return listeningDao.getListeningCountOfUserBetweenDates(userId, minDate, maxDate);
    }

    @RequestMapping(value = "/user/interval/{userId}/{minDate}/{maxDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Listening> getListeningOfUserBetweenDates(@PathVariable("userId") int userId, @PathVariable("minDate") @DateTimeFormat(pattern = "yyyyMMdd") Date minDate, @PathVariable("maxDate") @DateTimeFormat(pattern = "yyyyMMdd") Date maxDate) {
        return listeningDao.getListeningOfUserBetweenDates(userId, minDate, maxDate);
    }

}
