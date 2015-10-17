package com.duzenz.recommender.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.dao.TrackFrekansDao;
import com.duzenz.recommender.entities.TrackFrekans;

@Controller
@RequestMapping("/rest/trackfrekans/")
public class TrackFrekansResource {

	@Autowired
	private TrackFrekansDao trackFrekansDao;

	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TrackFrekans findTrackFrekans(@PathVariable("id") int trackFrekansId) {
		return trackFrekansDao.findTrackFrekans(trackFrekansId);
	}
	
	@RequestMapping(value = "/track/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TrackFrekans findTrackFrekansWithTrackId(@PathVariable("id") int trackId) {
		return trackFrekansDao.findTrackFrekansWithTrackId(trackId);
	}
	
	@RequestMapping(value = "/track/top/{trackCount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<TrackFrekans> findTopTracks(@PathVariable("trackCount") int trackCount) {
		return trackFrekansDao.findTopTracks(trackCount);
	}
	
	@RequestMapping(value = "/interval/{min}/{max}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<TrackFrekans> filterTracksWithListenCount(@PathVariable("min") int min, @PathVariable("max") int max) {
		return trackFrekansDao.filterTracksWithListenCount(min, max);
	}
	
	@RequestMapping(value = "/interval/count/{min}/{max}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public long getCountOfFilteredTracks(@PathVariable("min") int min, @PathVariable("max") int max) {
		return trackFrekansDao.getCountOfFilteredTracks(min, max);
	}

}
