package com.pahod.controller;

import com.pahod.exception.UserNotFoundException;
import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.service.BookingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FacadeController {

    private static final Logger logger = LoggerFactory.getLogger(FacadeController.class);

    @Autowired
    BookingFacade bookingFacade;

    @PostMapping("/users")
    public User newUser(@RequestBody User user) {
        return bookingFacade.newUser(user);
    }

    @ResponseStatus
    @GetMapping("/users/{userId}")
    public String getUserById(Model model, @PathVariable("userId") Long userId) {
        User userById = bookingFacade.getUserById(userId);
        if (userById == null) {
            throw new UserNotFoundException(userId);
        }
        logger.info(userById.toString());
        model.addAttribute("user", userById);
        return "user";
    }

    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        List<User> allUsers = bookingFacade.getAllUsers();
        logger.info(allUsers.toString());
        model.addAttribute("users", allUsers);
        return "users";
    }

    @PostMapping("/tickets")
    public Ticket buyTicket(@RequestBody Ticket ticket) {
        return bookingFacade.buyTicket(ticket);
    }


    @PostMapping("/events")
    public Event newEvent(@RequestBody Event event) {
        return bookingFacade.newEvent(event);
    }

    @GetMapping("/events")
    public String getAllEvents(Model model) {
        List<Event> events = bookingFacade.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

    @GetMapping("/tickets/{pageSize}/{pageNum}")
    public List<Ticket> getBookedTickets(@RequestBody User user,
                                         @PathVariable("pageSize") int pageSize,
                                         @PathVariable("pageNum") int pageNum) {
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets();

        return bookedTickets;
    }
}
