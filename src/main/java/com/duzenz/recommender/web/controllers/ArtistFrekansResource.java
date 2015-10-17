package com.duzenz.recommender.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.dao.ArtistFrekansDao;
import com.duzenz.recommender.entities.ArtistFrekans;

@Controller
@RequestMapping("/rest/artistfrekans/")
public class ArtistFrekansResource {

	@Autowired
	private ArtistFrekansDao artistFrekansDao;

	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArtistFrekans findArtistFrekans(@PathVariable("id") int artistFrekansId) {
		return artistFrekansDao.findArtistFrekans(artistFrekansId);
	}
	
	@RequestMapping(value = "/artist/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArtistFrekans findArtistFrekansWithArtistId(@PathVariable("id") int artistId) {
		return artistFrekansDao.findArtistFrekansWithArtistId(artistId);
	}
	
	@RequestMapping(value = "/artist/top/{artistCount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ArtistFrekans> findTopArtists(@PathVariable("artistCount") int artistCount) {
		return artistFrekansDao.findTopArtists(artistCount);
	}
	
	@RequestMapping(value = "/interval/{min}/{max}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ArtistFrekans> filterTracksWithListenCount(@PathVariable("min") int min, @PathVariable("max") int max) {
		return artistFrekansDao.filterArtitstWithListenCount(min, max);
	}
	
	@RequestMapping(value = "/interval/count/{min}/{max}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public long getCountOfFilteredTracks(@PathVariable("min") int min, @PathVariable("max") int max) {
		return artistFrekansDao.getCountOfFilteredArtists(min, max);
	}

}
