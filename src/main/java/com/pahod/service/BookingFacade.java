package com.pahod.service;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingFacade {

    User newUser(User user);

    User getUserById(Integer userId);

    List<User> allUsers();

    User updateUserDetails(User user);

    void deleteUser(Integer userId);


    Ticket buyTicket(Ticket ticket);

    Ticket changeTicket(Ticket ticket);

    void returnTicket(Integer ticketId, Integer userId);

    List<Ticket> getBookedTickets();

    List<Ticket> getAllSoldTicketsForEvent(Integer eventId);


    Event newEvent(Event event);

    List<Event> getAllEvents();

    List<Event> getAllEventsOfUser(Integer userId);

    long getTotalRevenueForEvent(Integer id);
}
