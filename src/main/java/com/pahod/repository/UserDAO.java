package com.pahod.repository;

import com.pahod.model.User;
import com.pahod.repository.storage.CommonInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    @Autowired
    CommonInMemoryStorage storage;

    public User addUser(User userToSave) {
        User savedUser = new User();
        savedUser.setId(storage.generateNextIdForClass(User.class));
        storage.getUsers().put(savedUser.getId(), savedUser);
        savedUser.updateFrom(userToSave);
        return savedUser;
    }

    public User updateUser(User userToUpdate) {
        User savedUser = storage.getUsers().get(userToUpdate.getId());
        if (savedUser != null) {
            savedUser.updateFrom(userToUpdate);
        }
        return savedUser;
    }

    public User getUserById(Long userId) {
        return storage.getUsers().get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(storage.getUsers().values());
    }

    public boolean deleteUser(Long userId) {
        return storage.getUsers().remove(userId) != null;
    }
}
