package com.duzenz.recommender.web.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.duzenz.recommender.dao.DataUserDao;
import com.duzenz.recommender.entities.DataUser;
import com.duzenz.recommender.entities.Role;
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
            boolean isAdmin = isUserAdmin();
            if (userId != 0 && user.getRoles().size() > 0 && !isAdmin) {
                DataUser dataUser = dataUserDao.findwithLastFmId("" + user.getId());
                if (dataUser != null) {
                    return "{'userId':" + user.getId() + ",'age':" + dataUser.getAge() + ",'register':" + dataUser.getRegisterCol() + ",'country':'" + dataUser.getCountry() + "', 'gender':'" + dataUser.getGender() + "'}";
                }
            }
            if (isAdmin) {
                return "{'userId':" + user.getId() + "}";
            }
        }
        return "{}";
    }

    public static boolean isUserAdmin() {
        User user = getCurrentUser();
        Set<Role> userRoles = user.getRoles();

        if (userRoles != null) {
            for (Role role : userRoles) {
                if (role.getRoleName().equals("ROLE_ADMIN")) {
                    return true;
                }
            }
        }
        return false;
    }

}
