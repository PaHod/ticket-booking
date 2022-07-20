package com.pahod.service;

import com.pahod.dto.TicketFullDetailsDTO;
import com.pahod.exception.EmptyFileException;
import com.pahod.exception.SomethingWentWrongException;
import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

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
    public User getUserById(long userId) {
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
    public void deleteUser(long userId) {
        ticketService.getTicketsByUserId(userId)
                .forEach(ticket -> returnTicket(ticket.getId(), userId));
        userService.deleteUser(userId);
    }


    @Override
    public Ticket buyTicket(Ticket ticket) {
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
    public boolean returnTicket(long ticketId, long userId) {
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
    public List<Ticket> getAllSoldTicketsForEvent(long eventId) {
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
    public List<Event> getAllEventsOfUser(long userId) {
        return null;
    }

    @Override
    public long getTotalRevenueForEvent(long eventId) {
        return ticketService.getAllTicketsByEventId(eventId)
                .stream()
                .mapToLong(Ticket::getPrice)
                .sum();
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public List<TicketFullDetailsDTO> getBookedTickets(long userId, int pageSize, int pageNum) {
        List<Ticket> ticketsByUserId = ticketService.getTicketsByUserId(userId);
        User user = userService.getUserById(userId);
        Map<Long, Event> tempEventsMap = new HashMap<>();

        List<TicketFullDetailsDTO> sortedTickets = ticketsByUserId.stream()
                .map(ticket -> {
                    long eventId = ticket.getEventId();
                    Event event = tempEventsMap.get(eventId);
                    if (event == null) {
                        event = eventService.getEvent(eventId);
                        tempEventsMap.put(eventId, event);
                    }
                    return TicketFullDetailsDTO.build(user, ticket, event);
                })
                .sorted(Comparator.comparing(TicketFullDetailsDTO::getEventDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        int pageStartIndex = (pageNum - 1) * pageSize;
        if (pageStartIndex > sortedTickets.size()) {
            return Collections.emptyList();
        }

        int pageLastIndex = pageStartIndex + pageSize;

        sortedTickets.subList(pageStartIndex, Math.min(sortedTickets.size(), pageLastIndex));

        return sortedTickets;
    }

    @Override
    public void uploadTicketsBatch(MultipartFile file) {
        logger.debug("Try to process the tickets batch");
        try {

            if (file.isEmpty()) {
                throw new EmptyFileException(file.getOriginalFilename());
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(Ticket.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Ticket unmarshal = (Ticket) unmarshaller.unmarshal(new StreamSource(file.getInputStream()));

            logger.info(">>>>> " + unmarshal);
            List<Ticket> ticketsBatch = new ArrayList<>();
            ticketsBatch.add(unmarshal);

            ticketsBatch.forEach(this::buyTicket);
        } catch (Exception e) {
            logger.error("Could not process the batch");
            e.printStackTrace();
            throw new SomethingWentWrongException("Could not process the batch");
        }
    }

    private void printUploadedFile(MultipartFile file) {
        try {
            Scanner scanner = new Scanner(file.getInputStream());
            while (scanner.hasNext()) {
                logger.info(scanner.next());
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
