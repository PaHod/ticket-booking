package com.pahod.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private Integer id;
    private Integer userId;
    private Integer eventId;
    private Integer seat;
    private Long price;

    public void updateFrom(Ticket ticketToSave) {
        this.setUserId(ticketToSave.getUserId());
        this.setEventId(ticketToSave.getEventId());
        this.setSeat(ticketToSave.getSeat());
        this.setPrice(ticketToSave.getPrice());
    }
}
