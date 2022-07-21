package com.pahod;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.service.BookingFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.matches;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    MockMvc mockMvc;

    private BookingFacade facade;
    private Event event0;

    @BeforeAll
    void stUp() throws Exception {
        facade = context.getBean(BookingFacade.class);
        User user0 = facade.createUser(new User(0, "Peter", "Peter@mail.test"));
        User user1 = facade.createUser(new User(0, "Johan", "Johan@mail.test"));
        User user2 = facade.createUser(new User(0, "Paul", "Paul@mail.test"));
        User user3 = facade.createUser(new User(0, "Viktor", "Viktor@mail.test"));
        User user4 = facade.createUser(new User(0, "Stepan", "Stepan@mail.test"));

        event0 = facade.createEvent(Event.builder().title("Rock Simphony").location("Palac Ukraina").availableSeats(5000).build());

        Event event1 = Event.builder().title("Mgzavrebi").location("Palac Sportu").availableSeats(5000).build();
        Event event2 = Event.builder().title("Deep Purple").location("Palac Sportu").availableSeats(5000).build();
        Event event3 = Event.builder().title("Jazz on the Roof").location("The Roof").availableSeats(250).build();
        Event event4 = Event.builder().title("Skriabin").location("The Roof").availableSeats(250).build();

        Ticket ticket0 = facade.bookTicket(new Ticket(0, user0.getId(), event0.getId(), 101, 500L));
        Ticket ticket1 = facade.bookTicket(new Ticket(0, user1.getId(), event0.getId(), 102, 750L));
        Ticket ticket2 = facade.bookTicket(new Ticket(0, user2.getId(), event0.getId(), 103, 750L));

        Ticket ticket3 = facade.bookTicket(new Ticket(0, user3.getId(), event1.getId(), 503, 750L));

        Ticket ticket4 = facade.bookTicket(new Ticket(0, user1.getId(), event3.getId(), 71, 750L));
        Ticket ticket5 = facade.bookTicket(new Ticket(0, user4.getId(), event3.getId(), 72, 750L));

        Ticket ticket6 = facade.bookTicket(new Ticket(0, user1.getId(), event4.getId(), 98, 750L));
        Ticket ticket7 = facade.bookTicket(new Ticket(0, user4.getId(), event4.getId(), 99, 750L));
    }

    @Test
    void test_get_all_users_success() throws Exception {
        List<User> allUsers = facade.getAllUsers();

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", allUsers))
                .andExpect(model().attribute("message", "Users list"))
                .andDo(print());
    }

    @Test
    void test_get_all_users_fail() throws Exception {
        long id = 5599;
        mockMvc.perform(get("/users/byId/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("error/error-500"))
                .andExpect(model().attribute("message", "Not found user with id: " + id))
                .andDo(print());
    }
}
