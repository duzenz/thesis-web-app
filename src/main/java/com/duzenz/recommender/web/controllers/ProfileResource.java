package com.duzenz.recommender.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.dao.UserDao;

@Controller
@RequestMapping("/rest/profile/")
public class ProfileResource {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/controlPassword", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean controlPassword(@RequestParam String password, @RequestParam int userId) {
        return userDao.isOldPasswordCorrect(password, userId);
    }

    @RequestMapping(value = "/savePassword", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean savePassword(@RequestParam String password, @RequestParam int userId) {
        return userDao.changePassword(password, userId);
    }
}
