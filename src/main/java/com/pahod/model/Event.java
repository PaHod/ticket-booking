package com.pahod.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Integer id;
    private String name;
    private String concertHallName;
    private Integer availableSeats;

    public void updateFrom(Event eventToSave) {
        this.setName(eventToSave.getName());
        this.setConcertHallName(eventToSave.getConcertHallName());
        this.setAvailableSeats(eventToSave.getAvailableSeats());
    }
}
