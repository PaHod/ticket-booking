package com.pahod.service;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BookingFacadeImplTest {

    @Mock
    EventService eventService;

    @Mock
    TicketService ticketService;

    @Mock
    UserService userService;

    @InjectMocks
    BookingFacadeImpl bookingFacade;

    @BeforeEach
    void reset() {
        Mockito.reset(eventService);
        Mockito.reset(ticketService);
        Mockito.reset(userService);
    }


    @Test
    void newUser() {
        //given
        User user = new User(0, "Johan", "Johan@mail.test");

        Mockito.when(userService.addUser(any())).thenAnswer(invocation -> user);

        //when
        User returnedUser = bookingFacade.newUser(user);

        //then
        Mockito.verify(userService, Mockito.times(1)).addUser(any());
        assertEquals(user, returnedUser);
    }


    @Test
    void buyTicket_seats_checked() {
        //given
        long eventId = 23;
        int expectedSeats = 1;
        Ticket ticket = Mockito.spy(new Ticket(0, 12, eventId, 202, 500L));

        Mockito
                .when(eventService
                        .checkAvailableSeats(any(Long.class), any(Integer.class)))
                .then(invocation -> {
            long passedEventId = invocation.getArgument(0);
            int passedRequiredSeats = invocation.getArgument(1);
            assertEquals(eventId, passedEventId);
            assertEquals(expectedSeats, passedRequiredSeats);
            return false;
        });

        //when
        bookingFacade.buyTicket(ticket);

        //then
        Mockito.verify(ticket, Mockito.times(1)).getEventId();
        Mockito.verify(eventService, Mockito.times(1)).checkAvailableSeats(any(Long.class), any(Integer.class));
    }

    @Test
    void buyTicket_sold_tickets_notification() {
        //given
        long eventId = 23;
        int expectedSeats = 1;
        Ticket ticket = Mockito.spy(new Ticket(0, 12, eventId, 202, 500L));

        Mockito.when(eventService.checkAvailableSeats(any(Long.class), any(Integer.class))).thenAnswer(invocation -> true);
        Mockito.doAnswer(invocation -> {
            long passedEventId = invocation.getArgument(0);
            int passedRequiredSeats = invocation.getArgument(1);

            assertEquals(eventId, passedEventId);
            assertEquals(expectedSeats, passedRequiredSeats);
            return null;
        }).when(eventService).soldTicketsForEvent(any(Long.class), any(Integer.class));


        //when
        bookingFacade.buyTicket(ticket);

        //then
        Mockito.verify(ticket, Mockito.times(1)).getEventId();
        Mockito.verify(eventService, Mockito.times(1)).soldTicketsForEvent(any(Long.class), any(Integer.class));
    }

    @Test
    void buyTicket_call_repository_add_ticket() {
        //given
        Ticket ticket = Mockito.spy(new Ticket(0, 12, 23, 202, 500L));

        Mockito.when(eventService.checkAvailableSeats(any(Long.class), any(Integer.class))).thenAnswer(invocation -> true);
        Mockito.when(ticketService.addTicket(any())).thenAnswer(invocation -> ticket);

        //when
        Ticket returnedTicket = bookingFacade.buyTicket(ticket);

        //then
        Mockito.verify(ticketService, Mockito.times(1)).addTicket(any());
        assertEquals(ticket, returnedTicket);
    }

    @Test
    void buyTicket_no_available_seats() {
        //given
        Ticket ticket = Mockito.spy(new Ticket(0, 12, 23, 202, 500L));

        Mockito.when(eventService.checkAvailableSeats(any(Long.class), any(Integer.class))).thenAnswer(invocation -> false);

        //when
        Ticket returnedTicket = bookingFacade.buyTicket(ticket);

        //then
        Mockito.verify(eventService, Mockito.times(1)).checkAvailableSeats(any(Long.class), any(Integer.class));
        Mockito.verify(eventService, Mockito.times(0)).soldTicketsForEvent(any(Long.class), any(Integer.class));
        Mockito.verify(ticketService, Mockito.times(0)).addTicket(any());
        assertNull(returnedTicket);
    }

    @Test
    void returnTicket_success() {
        //given
        long userId = 12;
        long ticketId = 125;
        Ticket ticket = Mockito.spy(new Ticket(ticketId, userId, 23, 202, 500L));

        Mockito.when(ticketService.getTicket(any(Long.class))).thenAnswer(invocation -> ticket);
        Mockito.when(ticketService.returnTicket(any(Long.class), any(Long.class))).thenAnswer(invocation -> true);

        //when
        bookingFacade.returnTicket(ticketId, userId);

        //then
        Mockito.verify(ticketService, Mockito.times(1)).getTicket(any(Long.class));
        Mockito.verify(ticket, Mockito.times(1)).getUserId();
        Mockito.verify(ticketService, Mockito.times(1)).returnTicket(any(Long.class), any(Long.class));
    }

    @Test
    void returnTicket_wrong_userid() {
        //given
        long userId = 12;
        long ticketId = 125;
        Ticket ticket = Mockito.spy(new Ticket(ticketId, userId, 23, 202, 500L));

        Mockito.when(ticketService.getTicket(any(Long.class))).thenAnswer(invocation -> ticket);

        //when
        bookingFacade.returnTicket(ticketId, userId + 1);

        //then
        Mockito.verify(ticketService, Mockito.times(1)).getTicket(any(Long.class));
        Mockito.verify(ticket, Mockito.times(1)).getUserId();
        Mockito.verify(ticketService, Mockito.times(0)).returnTicket(any(Long.class), any(Long.class));
    }

    @Test
    void getAllSoldTickets() {
    }

    @Test
    void getAllSoldTicketsForEvent() {
    }


    @Test
    void newEvent() {
        //given
        Event event = Event.builder().title("Nevidialna Vystava").location("Komora").availableSeats(1000).build();

        Mockito.when(eventService.addEvent(any())).thenAnswer(invocation -> event);

        //when
        Event returnedEvent = bookingFacade.newEvent(event);

        //then
        Mockito.verify(eventService, Mockito.times(1)).addEvent(any());
        assertEquals(event, returnedEvent);
    }

    @Test
    void getAllEvents() {
    }

    @Test
    void getAllEventsOfUser() {
    }

    @Test
    void allUsers() {
    }

    @Test
    void getTotalRevenueForEvent() {
    }
}