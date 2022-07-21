package com.pahod.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket {
    public enum Category {STANDARD, PREMIUM, BAR}

    @XmlAttribute
    private long id;
    @XmlAttribute
    private long userId;
    @XmlAttribute
    private long eventId;
    //    private Category category;
    @XmlAttribute
    private int place;
    @XmlAttribute
    private long price;

    public void updateFrom(Ticket ticketToSave) {
        this.setUserId(ticketToSave.getUserId());
        this.setEventId(ticketToSave.getEventId());
//        this.setCategory(ticketToSave.getCategory());
        this.setPlace(ticketToSave.getPlace());
        this.setPrice(ticketToSave.getPrice());
    }
}
