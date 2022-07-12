package com.pahod.service;

import com.pahod.model.User;
import com.pahod.repository.UserDAO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    UserDAO repository;

    public User addUser(User user) {
        return repository.saveUser(user);
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public User updateUser(User user) {
        return repository.saveUser(user);
    }

    public void deleteUser(Integer userId) {
        repository.deleteUser(userId);
    }
}
