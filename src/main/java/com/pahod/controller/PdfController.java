package com.pahod.controller;

import com.pahod.dto.TicketFullDetailsDTO;
import com.pahod.service.BookingFacade;
import com.pahod.util.GeneratePdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.PathParam;
import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
public class PdfController {
    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    @Autowired
    BookingFacade bookingFacade;


    /**
     * example: http://localhost:8084/ticketsPDF?displayType=view&userId=5&pageSize=4&pageNum=2
     */
    @GetMapping(value = "/ticketsPDF", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getBookedTickets(@PathParam("displayType") String displayType,
                                                                @PathParam("userId") long userId,
                                                                @PathParam("pageSize") int pageSize,
                                                                @PathParam("pageNum") int pageNum
    ) {

        List<TicketFullDetailsDTO> bookedTickets = bookingFacade.getBookedTickets(userId, pageSize, pageNum);
        logger.info("Found {} tickets.", bookedTickets.size());

        ByteArrayInputStream bis = GeneratePdf.ticketsDetailsPdf(bookedTickets);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(getDisposition(displayType));

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    private ContentDisposition getDisposition(String displayType) {
        ContentDisposition.Builder builder;
        switch (displayType) {
            case "download":
                builder = ContentDisposition.attachment();
                break;
            case "view":
            default:
                builder = ContentDisposition.inline();
        }
        return builder.filename("tickets.pdf").build();
    }
}
