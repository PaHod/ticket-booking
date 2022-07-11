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

    private static Logger logger = LoggerFactory.getLogger(EntryPoint.class);


    public static void main(String[] args) {
        logger.info("main method started");
        logger.debug("You should do it using Unit tests");

        ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
        BookingFacadeImpl facade = context.getBean(BookingFacadeImpl.class);
        Event event = facade.newEvent(
                new Event(null, "Nevidialna Vystava", "Komora", 15));


        User johanUser = facade.newUser(new User(null, "Johan"));
        User peterUser = facade.newUser(new User(null, "Peter"));

        facade.buyTicket(new Ticket(null, johanUser.getId(), event.getId(), 202, 500L));
        facade.buyTicket(new Ticket(null, peterUser.getId(), event.getId(), 101, 750L));

        printUsers(facade);
        printTickets(facade);
        printEvents(facade);

        System.out.println("");
        System.out.println("total revenue for event \"" + event.getName() + "\" is: " + facade.getTotalRevenueForEvent(event.getId()));

        CommonInMemoryStorage bean = context.getBean(CommonInMemoryStorage.class);
        System.out.println(bean);

    }

    private static void printUsers(BookingFacadeImpl facade) {
        System.out.println(">>>> printUsers");
        for (User user : facade.allUsers()) {
            System.out.println(user);
        }
    }

    private static void printTickets(BookingFacadeImpl facade) {
        System.out.println(">>>> printTickets");
        for (Ticket allSoldTicket : facade.getAllSoldTickets()) {
            System.out.println(allSoldTicket);
        }
    }

    private static void printEvents(BookingFacadeImpl facade) {
        System.out.println(">>>> printEvents");
        for (Event event : facade.getAllEvents()) {
            System.out.println(event);
        }
    }
}
