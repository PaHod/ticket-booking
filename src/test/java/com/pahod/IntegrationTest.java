package com.pahod;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.repository.storage.StorageInitializationPostProcessor;
import com.pahod.service.BookingFacade;
import com.pahod.service.EventService;
import com.pahod.service.TicketService;
import com.pahod.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTest {

    @InjectMocks
    private ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");

    private BookingFacade facade;
    private Event event0;

    @BeforeAll
    void stUp() {
        facade = context.getBean(BookingFacade.class);
        User user0 = facade.newUser(new User(null, "Peter"));
        User user1 = facade.newUser(new User(null, "Johan"));
        User user2 = facade.newUser(new User(null, "Paul"));
        User user3 = facade.newUser(new User(null, "Viktor"));
        User user4 = facade.newUser(new User(null, "Stepan"));

        event0 = facade.newEvent(new Event(null, "Scorpions", "Palac Sportu", 5000));
        Event event1 = facade.newEvent(new Event(null, "Mgzavrebi", "Palac Sportu", 5000));
        Event event2 = facade.newEvent(new Event(null, "Deep Purple", "Palac Sportu", 5000));
        Event event3 = facade.newEvent(new Event(null, "Jazz on the Roof", "The Roof", 250));
        Event event4 = facade.newEvent(new Event(null, "Skriabin", "The Roof", 250));

        Ticket ticket0 = facade.buyTicket(new Ticket(null, user0.getId(), event0.getId(), 101, 500L));
        Ticket ticket1 = facade.buyTicket(new Ticket(null, user1.getId(), event0.getId(), 102, 750L));
        Ticket ticket2 = facade.buyTicket(new Ticket(null, user2.getId(), event0.getId(), 103, 750L));

        Ticket ticket3 = facade.buyTicket(new Ticket(null, user3.getId(), event1.getId(), 503, 750L));

        Ticket ticket4 = facade.buyTicket(new Ticket(null, user1.getId(), event3.getId(), 71, 750L));
        Ticket ticket5 = facade.buyTicket(new Ticket(null, user4.getId(), event3.getId(), 72, 750L));

        Ticket ticket6 = facade.buyTicket(new Ticket(null, user1.getId(), event4.getId(), 98, 750L));
        Ticket ticket7 = facade.buyTicket(new Ticket(null, user4.getId(), event4.getId(), 99, 750L));
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
