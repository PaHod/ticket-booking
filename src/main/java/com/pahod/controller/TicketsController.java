package com.pahod.controller;

import com.pahod.dto.TicketDetailedInfoDTO;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import com.pahod.service.BookingFacade;
import com.pahod.util.GeneratePdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketsController {

    private static final Logger logger = LoggerFactory.getLogger(TicketsController.class);

    @Autowired
    BookingFacade bookingFacade;


    @PostMapping
    public Ticket buyTicket(@RequestBody Ticket ticket) {
        return bookingFacade.bookTicket(ticket);
    }

    @GetMapping("/{pageSize}/{pageNum}")
    public List<Ticket> getBookedTickets(@RequestBody User user,
                                         @PathVariable("pageSize") int pageSize,
                                         @PathVariable("pageNum") int pageNum) {
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets();

        return bookedTickets;
    }

    /**
     * example: http://localhost:8084/ticketsPDF?displayType=view&userId=5&pageSize=4&pageNum=2
     */
    @GetMapping(value = "/generate-PDF", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getBookedTickets(@PathParam("displayType") String displayType,
                                                                @PathParam("userId") long userId,
                                                                @PathParam("pageSize") int pageSize,
                                                                @PathParam("pageNum") int pageNum) {

        List<TicketDetailedInfoDTO> bookedTickets = bookingFacade.getBookedTickets(userId, pageSize, pageNum);
        logger.info("Found {} tickets.", bookedTickets.size());

        ByteArrayInputStream bis = GeneratePdf.ticketsDetailsPdf(bookedTickets);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(GeneratePdf.getDisposition(displayType));

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
