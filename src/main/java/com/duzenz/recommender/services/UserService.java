package com.duzenz.recommender.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.repositories.UserRepository;
//import com.duzenz.recommender.dao.UserDao;
import com.duzenz.recommender.entities.User;

@Service
@Transactional
public class UserService {
    // private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // public UserService(UserDao userDao) {
    // this.userDao = userDao;
    // }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findUserById(int id) {
        return userRepository.findOne(id);
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}
