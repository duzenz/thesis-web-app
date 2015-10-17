package com.duzenz.recommender.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.dao.CbrDao;
import com.duzenz.recommender.dao.DataUserDao;
import com.duzenz.recommender.dao.UserTrackDao;
import com.duzenz.recommender.entities.Cbr;
import com.duzenz.recommender.entities.DataUser;
import com.duzenz.recommender.entities.UserTrack;

@Controller
@RequestMapping("/rest/cbr/")
public class CbrResource {

	@Autowired
	private DataUserDao dataUserDao;
	@Autowired
	private UserTrackDao userTrackDao;
	@Autowired
	private CbrDao cbrDao;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void randomCbrCreate() {
		for (int i = 1; i <= 100; i++) {
			DataUser selectedUser = dataUserDao.findRandom();
			UserTrack userTrack = userTrackDao.findRandomTrackOfUser(selectedUser.getId());
			System.out.println(selectedUser.getUserId());
			System.out.println(userTrack.getTrack().getTrackId());
			Cbr cbr = new Cbr();
			cbr.setCaseId("case" + i);
			cbr.setAge(selectedUser.getAge());
			cbr.setCountry(selectedUser.getCountry());
			cbr.setGender(selectedUser.getGender());
			cbr.setSolutionId("case" + i);
			cbr.setTrackId(userTrack.getTrack().getTrackId());
			System.out.println(cbr.toString());
			cbrDao.insertCbr(cbr);
		}
	}

}
