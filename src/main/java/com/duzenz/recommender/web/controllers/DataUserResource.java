package com.duzenz.recommender.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.dao.DataUserDao;
import com.duzenz.recommender.entities.DataUser;

@Controller
@RequestMapping("/rest/datauser/")
public class DataUserResource {

	@Autowired
	private DataUserDao dataUserDao;

	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataUser find(@PathVariable("id") int id) {
		return dataUserDao.find(id);
	}
	
	@RequestMapping(value = "/lastfm/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataUser findWithLastFmUserId(@PathVariable("id") String id) {
		return dataUserDao.findwithLastFmId(id);
	}
	
	@RequestMapping(value = "/userCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public long getUserCount() {
		return dataUserDao.getUserCount();
	}
	
	@RequestMapping(value = "/userCount/{ageMin}/{ageMax}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public long getUserCountBetweenAgeInterval(@PathVariable("ageMin") int ageMin, @PathVariable("ageMax") int ageMax) {
		return dataUserDao.getUserCountBetweenAgeInterval(ageMin, ageMax);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<DataUser> createDataUser(@RequestBody DataUser user) {
		DataUser savedUser = dataUserDao.createLastFmUser(user);
		return new ResponseEntity<DataUser>(savedUser, HttpStatus.CREATED);
	}

}
