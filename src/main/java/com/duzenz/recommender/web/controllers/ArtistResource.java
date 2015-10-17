package com.duzenz.recommender.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.dao.ArtistDao;
import com.duzenz.recommender.entities.Artist;

@Controller
@RequestMapping("/rest/artist/")
public class ArtistResource {

	@Autowired
	private ArtistDao artistDao;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Artist> findAll() {
		return artistDao.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Artist findArtist(@PathVariable("id") int artistId) {
		return artistDao.findArtist(artistId);
	}
	
	@RequestMapping(value = "lastfm/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Artist findWithArtistId(@PathVariable("id") String artistId) {
		return artistDao.findArtistWithLastFmId(artistId);
	}
	
	@RequestMapping(value = "/getArtistCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public long getArtistCount() {
		return artistDao.getArtistCount();
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Artist> searchArtist(@RequestParam String query) {
        return artistDao.searchArtist(query);
    }


}
