package com.pahod.repository;

import com.pahod.model.User;

import java.util.List;

interface IUserDAO {


    List<User> getAllUsers();
}
