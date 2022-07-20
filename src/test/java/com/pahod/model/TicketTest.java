package com.pahod.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TicketTest {

    @Test
    void updateFrom_all_field_updated() {
        //given
       long id = 11;
        Ticket ticketTemplate = new Ticket(id, 9, 12, 201, 550L);
        Ticket ticketToUpdate = new Ticket();
        ticketToUpdate.setId(id);
        //when
        ticketToUpdate.updateFrom(ticketTemplate);

        //then
        assertEquals(ticketToUpdate, ticketTemplate);
    }

    @Test
    void updateFrom_all_id_not_updated() {
        //given
        Ticket ticketTemplate = new Ticket(11, 9, 12, 201, 550L);
        Ticket ticketToUpdate = new Ticket();

        //when
        ticketToUpdate.updateFrom(ticketTemplate);

        //then
        assertNotEquals(ticketToUpdate.getId(), ticketTemplate.getId());
    }
}