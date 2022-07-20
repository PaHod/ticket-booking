package com.pahod.service;

import com.pahod.exception.UserNotFoundException;
import com.pahod.model.User;
import com.pahod.repository.UserDAO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    UserDAO repository;

    public User addUser(User user) {
        return repository.addUser(user);
    }

    public User getUserById(long userId) {
        return repository.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public User updateUser(User user) {
        return repository.updateUser(user);
    }

    public void deleteUser(long userId) {
        repository.deleteUser(userId);
    }
}
