package com.pahod.repository;

import com.pahod.model.Ticket;
import com.pahod.repository.storage.CommonInMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TicketDAOTest {

    @Spy
    CommonInMemoryStorage storage;

    @Spy
    Map<Long, Ticket> ticketsMapSpy = new HashMap<>();

    @InjectMocks
    TicketDAO ticketDAO;

    @BeforeEach
    void reset() {
        Mockito.reset(storage);
        ticketsMapSpy.clear();
    }

    @Test
    void saveTicket_new_ticket_retrieved_from_storage() {
        //given
       long presetId = 151;
        Ticket ticketMock = Mockito.spy(new Ticket(presetId, 9, 12, 201, 550L));

        Mockito.when(storage.getTickets()).then(invocation -> ticketsMapSpy);
        Mockito.when(ticketsMapSpy.get(presetId)).then(invocation -> ticketMock);

        //when
        Ticket savedTicket = ticketDAO.updateTicket(ticketMock);

        //then
        verify(storage, times(1)).getTickets();
        assertSame(savedTicket, ticketMock);
    }

    @Test
    void addTicket_add_new_id_generated() {
        //given
        Ticket ticket = new Ticket(0, 9, 12, 201, 550L);
       long generatedId = 151;

        Mockito.when(storage.getTickets()).then(invocation -> ticketsMapSpy);

        Mockito.when(storage.generateNextIdForClass(any())).then(invocation -> generatedId);

        //when
        Ticket savedTicket = ticketDAO.addTicket(ticket);


        //then
        verify(storage, times(1)).generateNextIdForClass(Ticket.class);
        assertEquals(savedTicket.getId(), generatedId);
    }

    @Test
    void saveTicket_add_new_ticket_is_copy_saved() {
        //given
       long id = 151;
        Ticket ticket = new Ticket(id, 9, 12, 201, 550L);
        Mockito.when(storage.getTickets()).then(invocation -> ticketsMapSpy);
        Mockito.when(storage.generateNextIdForClass(any())).then(invocation -> id);
        ticketDAO.addTicket(ticket);

        //when
        Ticket savedTicket = ticketDAO.updateTicket(ticket);


        //then
        assertNotSame(ticket, savedTicket);
        assertEquals(ticket, savedTicket);
    }

    @Test
    void saveTicket_add_new_ticket_copy_put_to_storage() {
        //given
       long id = 151;
        Ticket ticket = new Ticket(id, 9, 12, 201, 550L);

        Mockito.when(storage.getTickets()).then(invocation -> ticketsMapSpy);
        Mockito.when(storage.generateNextIdForClass(any())).then(invocation -> id);
        ticketDAO.addTicket(ticket);

        //when
        Ticket savedTicket = ticketDAO.updateTicket(ticket);

        //then
        Map<Long, Ticket> tickets = storage.getTickets();
        assertEquals(1, tickets.size());
        assertEquals(tickets.get(id), savedTicket);

    }

    @Test
    void getTicketsByUserId() {
        //given
        long id = 111;
        Mockito.when(storage.getTickets()).then(invocation -> ticketsMapSpy);

        //when
        ticketDAO.getTicket(id);

        //then

        verify(storage, times(1)).getTickets();
        verify(ticketsMapSpy, times(1)).get(id);
    }

    @Test
    void deleteTicket() {
        //given
        long id = 111;
        Ticket ticket = new Ticket(id, 9, 12, 201, 550L);
        ticketsMapSpy.put(id, ticket);

        Mockito.when(storage.getTickets()).then(invocation -> ticketsMapSpy);

        //when
        boolean wasTickedDeleted = ticketDAO.deleteTicket(id);

        //then
        verify(storage, times(1)).getTickets();
        verify(ticketsMapSpy, times(1)).remove(id);
        assertEquals(0, ticketsMapSpy.size());
        assertTrue(wasTickedDeleted);

    }

    @Test
    void getAllTickets() {
        //given
       long userId = 9;
        Ticket ticket1 = new Ticket(1, userId, 12, 201, 550L);
        Ticket ticket2 = new Ticket(2, userId, 12, 201, 550L);
        Ticket ticket3 = new Ticket(3, 5, 12, 201, 550L);
        Ticket ticket4 = new Ticket(4, 7, 12, 201, 550L);
        ticketsMapSpy.put(ticket1.getId(), ticket1);
        ticketsMapSpy.put(ticket2.getId(), ticket2);
        ticketsMapSpy.put(ticket3.getId(), ticket3);
        ticketsMapSpy.put(ticket4.getId(), ticket4);

        Mockito.when(storage.getTickets()).then(invocation -> ticketsMapSpy);

        //when
        List<Ticket> ticketsByUserId = ticketDAO.getTicketsByUserId(userId);

        //then
        verify(storage, times(1)).getTickets();
        verify(ticketsMapSpy, times(1)).values();
        assertEquals(2, ticketsByUserId.size());
    }

    @Test
    void getAllTicketsByEventId() {
        //given
       long eventId = 12;
        Ticket ticket1 = new Ticket(1, 9, 5, 201, 550L);
        Ticket ticket2 = new Ticket(2, 9, 10, 201, 550L);
        Ticket ticket3 = new Ticket(3, 5, eventId, 201, 550L);
        Ticket ticket4 = new Ticket(4, 7, eventId, 201, 550L);
        ticketsMapSpy.put(ticket1.getId(), ticket1);
        ticketsMapSpy.put(ticket2.getId(), ticket2);
        ticketsMapSpy.put(ticket3.getId(), ticket3);
        ticketsMapSpy.put(ticket4.getId(), ticket4);

        Mockito.when(storage.getTickets()).then(invocation -> ticketsMapSpy);

        //when
        List<Ticket> ticketsByEventId = ticketDAO.getAllTicketsByEventId(eventId);

        //then
        verify(storage, times(1)).getTickets();
        verify(ticketsMapSpy, times(1)).values();
        assertEquals(2, ticketsByEventId.size());
    }
}