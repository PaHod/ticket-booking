package com.pahod.controller;

import com.pahod.exception.UserNotFoundException;
import com.pahod.model.User;
import com.pahod.service.BookingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    BookingFacade bookingFacade;

    @PostMapping
    public String createUser(@RequestBody User user, Model model) {
        User savedUser = bookingFacade.createUser(user);

        logger.info("Created new user with id: {}", savedUser.getId());
        model.addAttribute("message", "User");
        model.addAttribute("users", List.of(savedUser));
        return "users";
    }

    @GetMapping("/byId/{userId}")
    public String getUserById(Model model, @PathVariable("userId") Long userId) {
        User user = bookingFacade.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("id: " + userId);
        }
        logger.info(user.toString());
        model.addAttribute("message", "User");
        model.addAttribute("users", List.of(user));
        return "users";
    }

    /**
     * example: http://localhost:8084/users/byId?userName=Paul&pageSize=4&pageNum=2
     */
    @GetMapping("/byName")
    public String getUserByName(Model model,
                                @PathParam("userName") String userName,
                                @PathParam("pageSize") int pageSize,
                                @PathParam("pageNum") int pageNum) {
        User user = bookingFacade.getUserByName(userName, pageSize, pageNum);
        if (user == null) {
            throw new UserNotFoundException("Name: " + userName);
        }
        logger.info(user.toString());
        model.addAttribute("message", "User");
        model.addAttribute("users", List.of(user));
        return "users";
    }

    @GetMapping("/byEmail/{userEmail}")
    public String getUserByEmail(Model model, @PathParam("userEmail") String userEmail) {
        User user = bookingFacade.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("Email: " + userEmail);
        }
        logger.info(user.toString());
        model.addAttribute("message", "User");
        model.addAttribute("users", List.of(user));
        return "users";
    }

    @GetMapping
    public String allUsers(Model model) {
        List<User> allUsers = bookingFacade.getAllUsers();
        logger.info("Retrieved {} users.", allUsers.size());

        model.addAttribute("message", "Users list");
        model.addAttribute("users", allUsers);
        return "users";
    }

    @PatchMapping
    String updateUser(User user, Model model) {
        User updatedUser = bookingFacade.updateUser(user);
        logger.info("Updated users with id: {}.", updatedUser.getId());

        model.addAttribute("message", "User updated");
        model.addAttribute("users", List.of(updatedUser));
        return "users";
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") long userId, Model model) {
        boolean isDeleted = bookingFacade.deleteUser(userId);
        String message = String.format("User with id: %d is %b deleted.", userId, (isDeleted ? "" : "NOT"));

        logger.info(message);
        model.addAttribute("message", message);
        return "users";
    }
}
