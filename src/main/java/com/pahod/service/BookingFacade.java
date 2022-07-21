package com.pahod.service;

import com.pahod.dto.TicketDetailedInfoDTO;
import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public interface BookingFacade {

    User createUser(User user);

    User getUserById(long userId);

    User getUserByName(String userName, int pageSize, int pageNum);

    User getUserByEmail(String userEmail);

    List<User> getAllUsers();

    User updateUser(User user);

    boolean deleteUser(long userId);


    Ticket bookTicket(Ticket ticket);

    Ticket changeTicket(Ticket ticket);

    boolean cancelTicket(long ticketId, long userId);

    List<Ticket> getBookedTickets();

    List<Ticket> getAllSoldTicketsForEvent(long eventId);

    List<TicketDetailedInfoDTO> getBookedTickets(long userId, int pageSize, int pageNum);

    void uploadTicketsBatch(MultipartFile multipartFile);


    Event createEvent(Event event);

    List<Event> getAllEvents();

    List<Event> getEventsById(long eventId);

    List<Event> getEventsByUserId(long userId);

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     */
    List<Event> getEventsForDay(Date day, int pageSize, int pageNum);

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     */
    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    Event updateEvent(Event event);

    boolean deleteEvent(long eventId);

    long getTotalRevenueForEvent(long id);
}
