package com.pahod.repository;

import com.pahod.model.Ticket;

import java.util.List;

interface ITicketDAO {


    List<Ticket> getAllTickets();

    List<Ticket> getAllTicketsByEventId(Integer eventId);
}
