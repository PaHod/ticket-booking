package com.pahod;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.repository.storage.CommonInMemoryStorage;
import com.pahod.service.BookingFacadeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class EntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(EntryPoint.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TicketBookingApplication.class, args);

        BookingFacadeImpl facade = context.getBean(BookingFacadeImpl.class);

        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        Event event = facade.createEvent(Event.builder()
                .title("Rock Simphony").location("Palac Ukraina").availableSeats(5000)
                .build());



        User johanUser = facade.createUser(new User(0, "Johan", "Johan@mail.ep"));
        User peterUser = facade.createUser(new User(0, "Peter", "Peter@mail.ep"));

        facade.bookTicket(Ticket.builder()
                .userId(johanUser.getId())
                .eventId(event.getId())
                .place(202)
                .price(500L)
                .build());

        facade.bookTicket(Ticket.builder()
                .userId(peterUser.getId())
                .eventId(event.getId())
                .place(101)
                .price(750L)
                .build());

        printAllUsers(facade);
        printAllTickets(facade);
        printAllEvents(facade);

        logger.info("Total revenue for event \"{}\" is: {}", event.getTitle(), facade.getTotalRevenueForEvent(event.getId()));

        CommonInMemoryStorage storage = context.getBean(CommonInMemoryStorage.class);
        logger.trace("print all the storage: {}", storage);
    }

    private static void printAllUsers(BookingFacadeImpl facade) {
        for (User user : facade.getAllUsers()) {
            logger.trace(user.toString());
        }
    }

    private static void printAllTickets(BookingFacadeImpl facade) {
        for (Ticket allSoldTicket : facade.getBookedTickets()) {
            logger.trace(allSoldTicket.toString());
        }
    }

    private static void printAllEvents(BookingFacadeImpl facade) {
        for (Event event : facade.getAllEvents()) {
            logger.trace(event.toString());
        }
    }
}
