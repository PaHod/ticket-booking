package com.pahod.repository;

import com.pahod.model.Ticket;
import com.pahod.repository.storage.CommonInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketDAO implements ITicketDAO {

    @Autowired
    CommonInMemoryStorage storage;

    public Ticket saveTicket(Ticket ticketToSave) {
        Ticket savedTicket = storage.getTickets().get(ticketToSave.getId());

        if (ticketToSave.getId() == null || savedTicket == null) {
            savedTicket = new Ticket();
            savedTicket.setId(storage.generateNextIdForClass(Ticket.class));
            storage.getTickets().put(savedTicket.getId(), savedTicket);
        }
        savedTicket.updateFrom(ticketToSave);
        return savedTicket;
    }

    public Ticket getTicket(Integer ticketId) {
        return storage.getTickets().get(ticketId);
    }

    public List<Ticket> getTicketsByUserId(Integer userId) {
        return storage.getTickets().values().stream()
                .filter(ticket -> ticket.getUserId().equals(userId))
                .collect(Collectors.toList());
    }


    public boolean deleteTicket(Integer ticketId) {
        return storage.getTickets().remove(ticketId) != null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return new ArrayList<>(storage.getTickets().values());
    }

    @Override
    public List<Ticket> getAllTicketsByEventId(Integer eventId) {
        return storage.getTickets().values()
                .stream()
                .filter(ticket -> ticket.getEventId().equals(eventId))
                .collect(Collectors.toList());
    }
}
