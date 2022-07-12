package com.pahod.repository;

import com.pahod.model.User;
import com.pahod.repository.storage.CommonInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// TODO: YANGI KISS SOLID DRY
@Component
public class UserDAO implements IUserDAO {

    @Autowired
    CommonInMemoryStorage storage;

    public User saveUser(User userToSave) {
        User savedUser = storage.getUsers().get(userToSave.getId());

        if (userToSave.getId() == null || savedUser == null) {
            savedUser = new User();
            savedUser.setId(storage.generateNextIdForClass(User.class));
            storage.getUsers().put(savedUser.getId(), savedUser);
        }
        savedUser.updateFrom(userToSave);
        return savedUser;
    }

    public void deleteUser(Integer userId) {
        storage.getUsers().remove(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(storage.getUsers().values());
    }
}
