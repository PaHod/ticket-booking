package com.pahod.repository;

import com.pahod.model.Ticket;
import com.pahod.repository.storage.CommonInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketDAO {

    @Autowired
    CommonInMemoryStorage storage;

    public Ticket addTicket(Ticket ticketToAdd) {
        Ticket savedTicket = new Ticket();
        savedTicket.setId(storage.generateNextIdForClass(Ticket.class));
        storage.getTickets().put(savedTicket.getId(), savedTicket);
        savedTicket.updateFrom(ticketToAdd);
        return savedTicket;
    }

    public Ticket updateTicket(Ticket ticketToUpdate) {
        Ticket savedTicket = storage.getTickets().get(ticketToUpdate.getId());

        if (savedTicket != null) {
            savedTicket.updateFrom(ticketToUpdate);
        }
        return savedTicket;
    }

    public Ticket getTicket(Long ticketId) {
        return storage.getTickets().get(ticketId);
    }

    public List<Ticket> getTicketsByUserId(long userId) {
        return storage.getTickets().values().stream()
                .filter(ticket -> ticket.getUserId() == userId)
                .collect(Collectors.toList());
    }


    public boolean deleteTicket(long ticketId) {
        return storage.getTickets().remove(ticketId) != null;
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(storage.getTickets().values());
    }

    public List<Ticket> getAllTicketsByEventId(long eventId) {
        return storage.getTickets().values()
                .stream()
                .filter(ticket -> ticket.getEventId() == eventId)
                .collect(Collectors.toList());
    }
}
