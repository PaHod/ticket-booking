package com.pahod.service;

import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.repository.TicketDAO;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    TicketDAO repository;

    public Ticket addTicket(Ticket ticket) {
        logger.info("Buy ticket: " + ticket);
        return repository.addTicket(ticket);
    }

    public Ticket getTicket(long ticketId) {
        return repository.getTicket(ticketId);
    }

    public List<Ticket> getTicketsByUserId(long userId) {
        return repository.getTicketsByUserId(userId);
    }

    public List<Ticket> getAllTickets() {
        return repository.getAllTickets();
    }

    public Ticket updateTicket(Ticket ticket) {
        return repository.updateTicket(ticket);
    }

    public boolean returnTicket(long ticketId, long userId) {
        Ticket ticket = repository.getTicket(ticketId);
        if (ticket.getUserId() == userId) {
           return repository.deleteTicket(ticketId);
        }
        return false;
    }

    public List<Ticket> getAllTicketsByEventId(Long eventId) {
        logger.trace("getAllTicketsByEventId: " + eventId);

        return repository.getAllTicketsByEventId(eventId);
    }
}
