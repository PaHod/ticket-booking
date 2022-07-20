package com.pahod.service;

import com.pahod.dto.TicketFullDetailsDTO;
import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BookingFacade {

    User newUser(User user);

    User getUserById(long userId);

    List<User> allUsers();

    User updateUserDetails(User user);

    void deleteUser(long userId);


    Ticket buyTicket(Ticket ticket);

    Ticket changeTicket(Ticket ticket);

    boolean returnTicket(long ticketId, long userId);

    List<Ticket> getBookedTickets();

    List<Ticket> getAllSoldTicketsForEvent(long eventId);


    Event newEvent(Event event);

    List<Event> getAllEvents();

    List<Event> getAllEventsOfUser(long userId);

    long getTotalRevenueForEvent(long id);

    List<User> getAllUsers();

    /**
     * Get all booked tickets info for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param userId   UserId
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<TicketFullDetailsDTO> getBookedTickets(long userId, int pageSize, int pageNum);

    void uploadTicketsBatch(MultipartFile multipartFile);
}
