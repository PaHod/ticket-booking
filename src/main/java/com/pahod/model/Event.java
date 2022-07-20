package com.pahod.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    private long id;
    private String title;
    private String location;
    private Date date;
    private int availableSeats;

    public void updateFrom(Event eventToSave) {
        this.setTitle(eventToSave.getTitle());
        this.setLocation(eventToSave.getLocation());
        this.setDate(eventToSave.getDate());
        this.setAvailableSeats(eventToSave.getAvailableSeats());
    }
}
