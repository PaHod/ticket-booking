package com.pahod.service;

import com.pahod.dto.TicketDetailedInfoDTO;
import com.pahod.exception.EmptyFileException;
import com.pahod.exception.SomethingWentWrongException;
import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.util.BookingFacadeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingFacadeImpl implements BookingFacade {

    private static final Logger logger = LoggerFactory.getLogger(BookingFacadeImpl.class);

    EventService eventService;
    TicketService ticketService;
    UserService userService;

    public BookingFacadeImpl(EventService eventService, TicketService ticketService, UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    //region Users
    @Override
    public User createUser(User user) {
        return userService.addUser(user);
    }

    @Override
    public User getUserById(long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public User getUserByName(String userName, int pageSize, int pageNum) {
        // TODO: implement
        return null;
    }

    @Override
    public User getUserByEmail(String userEmail) {
        // TODO: implement
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        ticketService.getTicketsByUserId(userId)
                .forEach(ticket -> cancelTicket(ticket.getId(), userId));
        return userService.deleteUser(userId);
    }
    //endregion


    //region Tickets
    @Override
    public Ticket bookTicket(Ticket ticket) {
        long eventId = ticket.getEventId();
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
    public boolean cancelTicket(long ticketId, long userId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        if (ticket.getUserId() == userId) {
            return ticketService.returnTicket(ticketId, userId);
        }
        return false;
    }

    @Override
    public List<Ticket> getBookedTickets() {
        return ticketService.getAllTickets();
    }

    @Override
    public List<TicketDetailedInfoDTO> getBookedTickets(long userId, int pageSize, int pageNum) {
        List<Ticket> ticketsByUserId = ticketService.getTicketsByUserId(userId);
        User user = userService.getUserById(userId);
        Map<Long, Event> tempEventsMap = new HashMap<>();

        List<TicketDetailedInfoDTO> sortedTickets = ticketsByUserId.stream()
                .map(ticket -> {
                    long eventId = ticket.getEventId();
                    Event event = tempEventsMap.get(eventId);
                    if (event == null) {
                        event = eventService.getEvent(eventId);
                        tempEventsMap.put(eventId, event);
                    }
                    return TicketDetailedInfoDTO.build(user, ticket, event);
                })
                .sorted(Comparator.comparing(TicketDetailedInfoDTO::getEventDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return BookingFacadeUtils.getPageItems(sortedTickets, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getAllSoldTicketsForEvent(long eventId) {
        return ticketService.getAllTicketsByEventId(eventId);
    }

    @Override
    public void uploadTicketsBatch(MultipartFile file) {
        logger.debug("Try to process the tickets batch");
        try {
            if (file.isEmpty()) {
                throw new EmptyFileException(file.getOriginalFilename());
            }

            List<Ticket> tickets = BookingFacadeUtils.parseTicketsBatch(file);
            tickets.forEach(this::bookTicket);
            logger.info("Successfully parsed Tickets batch");
        } catch (Exception e) {
            String m = "Could not process the batch";
            e.printStackTrace();
            logger.error(m);
            throw new SomethingWentWrongException(m);
        }
    }
    //endregion

    //region Events
    @Override
    public Event createEvent(Event event) {
        return eventService.addEvent(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Override
    public List<Event> getEventsById(long eventId) {
        // TODO: implement
        return null;
    }

    @Override
    public List<Event> getEventsByUserId(long userId) {
        // TODO: implement
        return null;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        // TODO: implement
        return null;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        // TODO: implement
        return null;
    }

    @Override
    public Event updateEvent(Event event) {
        // TODO: implement
        return null;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        // TODO: implement
        return false;
    }

    @Override
    public long getTotalRevenueForEvent(long eventId) {
        return ticketService.getAllTicketsByEventId(eventId)
                .stream()
                .mapToLong(Ticket::getPrice)
                .sum();
    }
    //endregion
}
