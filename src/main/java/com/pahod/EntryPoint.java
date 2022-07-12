package com.pahod;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.repository.storage.CommonInMemoryStorage;
import com.pahod.service.BookingFacadeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(EntryPoint.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
        BookingFacadeImpl facade = context.getBean(BookingFacadeImpl.class);

        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        Event event = facade.newEvent(new Event(null, "Rock Simphony", "Palac Ukraina", 5000));


        User johanUser = facade.newUser(new User(null, "Johan"));
        User peterUser = facade.newUser(new User(null, "Peter"));

        facade.buyTicket(new Ticket(null, johanUser.getId(), event.getId(), 202, 500L));
        facade.buyTicket(new Ticket(null, peterUser.getId(), event.getId(), 101, 750L));

        printAllUsers(facade);
        printAllTickets(facade);
        printAllEvents(facade);

        logger.info("Total revenue for event \"{}\" is: {}", event.getName(), facade.getTotalRevenueForEvent(event.getId()));

        CommonInMemoryStorage storage = context.getBean(CommonInMemoryStorage.class);
        logger.trace("print all the storage: {}", storage);
    }

    private static void printAllUsers(BookingFacadeImpl facade) {
        for (User user : facade.allUsers()) {
            logger.trace(user.toString());
        }
    }

    private static void printAllTickets(BookingFacadeImpl facade) {
        for (Ticket allSoldTicket : facade.getAllSoldTickets()) {
            logger.trace(allSoldTicket.toString());
        }
    }

    private static void printAllEvents(BookingFacadeImpl facade) {
        for (Event event : facade.getAllEvents()) {
            logger.trace(event.toString());
        }
    }
}
