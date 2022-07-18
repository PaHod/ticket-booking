package com.pahod.service;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingFacadeImpl implements BookingFacade {

    private static final Logger logger = LoggerFactory.getLogger(BookingFacadeImpl.class);

    EventService eventService;
    TicketService ticketService;
    UserService userService;

    public BookingFacadeImpl(EventService eventService,
                             TicketService ticketService,
                             UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }


    @Override
    public User newUser(User user) {
        return userService.addUser(user);
    }

    @Override
    public User getUserById(Integer userId) {
        return userService.getUserById(userId);
    }

    @Override
    public User updateUserDetails(User user) {
        return userService.updateUser(user);
    }

    @Override
    public List<User> allUsers() {
        return userService.getAllUsers();
    }

    @Override
    public void deleteUser(Integer userId) {
        ticketService.getTicketsByUserId(userId)
                .forEach(ticket -> returnTicket(ticket.getId(), userId));
        userService.deleteUser(userId);
    }


    @Override
    public Ticket buyTicket(Ticket ticket) {
        Integer eventId = ticket.getEventId();
        if (eventService.checkAvailableSeats(eventId, 1)) {
            eventService.soldTicketsForEvent(eventId, 1);
            return ticketService.addTicket(ticket);
        }
        logger.warn("couldn't sell ticket for eventId: {} check Id or consider adding chairs.", eventId);
        return null;
    }

    @Override
    public Ticket changeTicket(Ticket ticket) {
        return ticketService.updateTicket(ticket);
    }

    @Override
    public void returnTicket(Integer ticketId, Integer userId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        if (ticket.getUserId().equals(userId)) {
            ticketService.returnTicket(ticketId, userId);
        }
    }

    @Override
    public List<Ticket> getBookedTickets() {
        return ticketService.getAllTickets();
    }

    @Override
    public List<Ticket> getAllSoldTicketsForEvent(Integer eventId) {
        return ticketService.getAllTicketsByEventId(eventId);
    }


    @Override
    public Event newEvent(Event event) {
        return eventService.addEvent(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Override
    public List<Event> getAllEventsOfUser(Integer userId) {
        return null;
    }

    @Override
    public long getTotalRevenueForEvent(Integer eventId) {
        return ticketService.getAllTicketsByEventId(eventId)
                .stream()
                .mapToLong(Ticket::getPrice)
                .sum();
    }
}
