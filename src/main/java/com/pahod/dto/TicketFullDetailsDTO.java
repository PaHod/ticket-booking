package com.pahod.dto;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class TicketFullDetailsDTO {
    private long userId;
    private String userName;

    private long ticketId;
    private int ticketPlace;
    private long ticketPrice;

    private long eventId;
    private String eventTitle;
    private Date eventDate;
    private String eventLocation;

    public static TicketFullDetailsDTO build(User user, Ticket ticket, Event event) {
        return TicketFullDetailsDTO.builder()
                .userId(user.getId())
                .userName(user.getName())
                .ticketId(ticket.getId())
                .ticketPlace(ticket.getPlace())
                .ticketPrice(ticket.getPrice())
                .eventId(event.getId())
                .eventTitle(event.getTitle())
                .eventLocation(event.getLocation())
                .eventDate(event.getDate())
                .build();
    }
}
