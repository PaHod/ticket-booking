package com.pahod;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.repository.storage.StorageInitializationPostProcessor;
import com.pahod.service.BookingFacade;
import com.pahod.service.EventService;
import com.pahod.service.TicketService;
import com.pahod.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IntegrationTest {

    @InjectMocks
    private ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");

    private BookingFacade facade;
    private User user0;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private Event event0;
    private Event event1;
    private Event event2;
    private Event event3;
    private Event event4;
    private Ticket ticket0;
    private Ticket ticket1;
    private Ticket ticket2;
    private Ticket ticket3;
    private Ticket ticket4;
    private Ticket ticket5;
    private Ticket ticket6;
    private Ticket ticket7;
    private Ticket ticket8;
    private Ticket ticket9;
    private Ticket ticket10;
    private Ticket ticket11;


    @BeforeEach
    void stUp() {
        facade = context.getBean(BookingFacade.class);
        user0 = facade.newUser(new User(null, "Peter"));
        user1 = facade.newUser(new User(null, "Johan"));
        user2 = facade.newUser(new User(null, "Paul"));
        user3 = facade.newUser(new User(null, "Viktor"));
        user4 = facade.newUser(new User(null, "Stepan"));

        event0 = facade.newEvent(new Event(null, "Scorpions", "Palac Sportu", 5000));
        event1 = facade.newEvent(new Event(null, "Mgzavrebi", "Palac Sportu", 5000));
        event2 = facade.newEvent(new Event(null, "Deep Purple", "Palac Sportu", 5000));
        event3 = facade.newEvent(new Event(null, "Jazz on the Roof", "The Roof", 250));
        event4 = facade.newEvent(new Event(null, "Skriabin", "The Roof", 250));

        ticket0 = facade.buyTicket(new Ticket(null, user0.getId(), event0.getId(), 101, 500L));
        ticket1 = facade.buyTicket(new Ticket(null, user1.getId(), event0.getId(), 102, 750L));
        ticket2 = facade.buyTicket(new Ticket(null, user2.getId(), event0.getId(), 103, 750L));

        ticket3 = facade.buyTicket(new Ticket(null, user3.getId(), event1.getId(), 503, 750L));

        ticket4 = facade.buyTicket(new Ticket(null, user1.getId(), event3.getId(), 71, 750L));
        ticket5 = facade.buyTicket(new Ticket(null, user4.getId(), event3.getId(), 72, 750L));

        ticket6 = facade.buyTicket(new Ticket(null, user1.getId(), event4.getId(), 98, 750L));
        ticket7 = facade.buyTicket(new Ticket(null, user4.getId(), event4.getId(), 99, 750L));
    }

    @Test
    void test_users_has_unique_id() {
        ArrayList<Integer> ids = new ArrayList<>();

        for (User user : facade.allUsers()) {
            Integer id = user.getId();
            assertNotNull(id);
            assertFalse(ids.contains(id));
            ids.add(id);
        }
    }

    @Test
    void test_event1_has_3_sold_tickets() {
        List<Ticket> allSoldTicketsForEvent = facade.getAllSoldTicketsForEvent(event0.getId());
        assertEquals(3, allSoldTicketsForEvent.size());
    }

    @Test
    void test_total_revenue() {
        long totalRevenueForEvent = facade.getTotalRevenueForEvent(event0.getId());

        assertEquals(2000, totalRevenueForEvent);
    }

}
