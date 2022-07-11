package com.pahod.service;

import com.pahod.model.Ticket;
import com.pahod.repository.TicketDAO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class TicketService {

    @Autowired
    TicketDAO repository;

    public Ticket getTicket(Integer ticketId) {
        return repository.getTicket(ticketId);
    }

    public void returnTicket(Integer ticketId, Integer userId) {
        Ticket ticket = repository.getTicket(ticketId);
        if (ticket.getUserId().equals(userId)) {
            repository.deleteTicket(ticketId);
        }
    }

    public Ticket updateTicket(Ticket ticket) {
        return repository.saveTicket(ticket);
    }

    public Ticket addTicket(Ticket ticket) {
        return repository.saveTicket(ticket);
    }

    public List<Ticket> getTicketsByUserId(Integer userId) {
        return repository.getTicketsByUserId(userId);
    }

    public List<Ticket> getAllTickets() {
        return repository.getAllTickets();
    }

    public List<Ticket> getAllTicketsByEventId(Integer eventId) {
        return repository.getAllTicketsByEventId(eventId);
    }
}
