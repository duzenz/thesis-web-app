package com.duzenz.recommender.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.duzenz.recommender.dao.DataUserDao;
import com.duzenz.recommender.entities.DataUser;
import com.duzenz.recommender.entities.User;
import com.duzenz.recommender.services.UserService;
import com.duzenz.recommender.web.config.SecurityUser;

@Controller
public class UserController {
    private static UserService userService;

    private static DataUserDao dataUserDao;

    @Autowired
    public void setUserService(UserService userService) {
        UserController.userService = userService;
    }

    @Autowired
    public void setDataUserDao(DataUserDao dataUserDao) {
        UserController.dataUserDao = dataUserDao;
    }

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            User loginUser = userService.findUserByEmail(email);
            return new SecurityUser(loginUser);
        }
        return null;
    }

    public static String getCurrentUserJson() {
        User user = getCurrentUser();
        if (user != null) {
            System.out.println(user.getId());
            int userId = user.getId();
            if (userId != 0) {
                DataUser dataUser = dataUserDao.findwithLastFmId("" + user.getId());
                if (dataUser != null) {
                    return "{'userId':" + user.getId() + ",'age':" + dataUser.getAge() + ",'country':'" + dataUser.getCountry() + "', 'gender':'" + dataUser.getGender() + "'}";
                }
            }
        }
        return "{}";
    }

}
