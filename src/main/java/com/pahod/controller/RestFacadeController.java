package com.pahod.controller;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.service.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestFacadeController {

    @Autowired
    BookingFacade bookingFacade;


    @PostMapping("/users")
    public User newUser(@RequestBody User user) {
        return bookingFacade.newUser(user);
    }


    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable("userId") Integer userId) {
        return bookingFacade.getUserById(userId);
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return bookingFacade.allUsers();
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {

    }


    @PostMapping("/tickets")
    public Ticket buyTicket(@RequestBody Ticket ticket) {
        return bookingFacade.buyTicket(ticket);
    }


    @GetMapping("/tickets")
    public List<Ticket> getBookedTickets(@RequestBody User user,
                                         @PathVariable("pageSize") int pageSize,
                                         @PathVariable("pageNum") int pageNum) {
        return bookingFacade.getBookedTickets();
    }

    @PostMapping("/events")
    public Event newEvent(@RequestBody Event event) {
        return bookingFacade.newEvent(event);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return bookingFacade.getAllEvents();
    }
}
