package com.pahod.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement
public class Ticket {
    public enum Category {STANDARD, PREMIUM, BAR}

    private long id;
    private long userId;
    private long eventId;
    //    private Category category;
    private int place;
    private long price;

    public void updateFrom(Ticket ticketToSave) {
        this.setUserId(ticketToSave.getUserId());
        this.setEventId(ticketToSave.getEventId());
//        this.setCategory(ticketToSave.getCategory());
        this.setPlace(ticketToSave.getPlace());
        this.setPrice(ticketToSave.getPrice());
    }
}
