package com.pahod.dto;

import com.pahod.model.Ticket;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Object to parse XML list of {@link Ticket} from batch file.
 */

@Data
@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tickets {

    @XmlElement(name = "ticket")
    List<Ticket> tickets;

}
