package com.pahod;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.service.BookingFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTest {

    @InjectMocks
    private ApplicationContext context = SpringApplication.run(TicketBookingApplication.class);

    private BookingFacade facade;
    private Event event0;

    @BeforeAll
    void stUp() {
        facade = context.getBean(BookingFacade.class);
        User user0 = facade.newUser(new User(0, "Peter", "Peter@mail.test"));
        User user1 = facade.newUser(new User(0, "Johan", "Johan@mail.test"));
        User user2 = facade.newUser(new User(0, "Paul", "Paul@mail.test"));
        User user3 = facade.newUser(new User(0, "Viktor", "Viktor@mail.test"));
        User user4 = facade.newUser(new User(0, "Stepan", "Stepan@mail.test"));

        event0 = facade.newEvent(Event.builder()
                .title("Rock Simphony").location("Palac Ukraina").availableSeats(5000)
                .build());

        Event event1 = Event.builder()
                .title("Mgzavrebi").location("Palac Sportu").availableSeats(5000)
                .build();

        Event event2 = Event.builder()
                .title("Deep Purple").location("Palac Sportu").availableSeats(5000)
                .build();

        Event event3 = Event.builder()
                .title("Jazz on the Roof").location("The Roof").availableSeats(250)
                .build();

        Event event4 = Event.builder()
                .title("Skriabin").location("The Roof").availableSeats(250)
                .build();


        Ticket ticket0 = facade.buyTicket(new Ticket(0, user0.getId(), event0.getId(), 101, 500L));
        Ticket ticket1 = facade.buyTicket(new Ticket(0, user1.getId(), event0.getId(), 102, 750L));
        Ticket ticket2 = facade.buyTicket(new Ticket(0, user2.getId(), event0.getId(), 103, 750L));

        Ticket ticket3 = facade.buyTicket(new Ticket(0, user3.getId(), event1.getId(), 503, 750L));

        Ticket ticket4 = facade.buyTicket(new Ticket(0, user1.getId(), event3.getId(), 71, 750L));
        Ticket ticket5 = facade.buyTicket(new Ticket(0, user4.getId(), event3.getId(), 72, 750L));

        Ticket ticket6 = facade.buyTicket(new Ticket(0, user1.getId(), event4.getId(), 98, 750L));
        Ticket ticket7 = facade.buyTicket(new Ticket(0, user4.getId(), event4.getId(), 99, 750L));
    }

    @Test
    void test_users_has_positive_unique_id() {
        ArrayList<Long> ids = new ArrayList<>();

        for (User user : facade.allUsers()) {
            long id = user.getId();

            assertTrue(id >= 0);
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
