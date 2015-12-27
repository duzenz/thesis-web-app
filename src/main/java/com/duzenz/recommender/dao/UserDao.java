package com.duzenz.recommender.dao;

import java.util.List;

import com.duzenz.recommender.entities.User;

public interface UserDao {

    public List<User> findAll();

    public User create(User user);

    public User findUserById(int id);

    public User login(String email, String password);

    public boolean isOldPasswordCorrect(String password, int userId);

    public boolean changePassword(String password, int userId);

}
