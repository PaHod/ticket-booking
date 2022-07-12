package com.pahod.service;

import com.pahod.model.Ticket;
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
        return repository.saveTicket(ticket);
    }

    public Ticket getTicket(Integer ticketId) {
        return repository.getTicket(ticketId);
    }

    public List<Ticket> getTicketsByUserId(Integer userId) {
        return repository.getTicketsByUserId(userId);
    }

    public List<Ticket> getAllTickets() {
        return repository.getAllTickets();
    }

    public Ticket updateTicket(Ticket ticket) {
        return repository.saveTicket(ticket);
    }

    public void returnTicket(Integer ticketId, Integer userId) {
        Ticket ticket = repository.getTicket(ticketId);
        if (ticket.getUserId().equals(userId)) {
            repository.deleteTicket(ticketId);
        }
    }

    public List<Ticket> getAllTicketsByEventId(Integer eventId) {
        logger.trace("getAllTicketsByEventId: " + eventId);

        return repository.getAllTicketsByEventId(eventId);
    }
}
