package com.pahod.controller;

import com.pahod.model.Event;
import com.pahod.service.BookingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    BookingFacade bookingFacade;

    @PostMapping()
    public Event newEvent(@RequestBody Event event) {
        return bookingFacade.createEvent(event);
    }

    @GetMapping()
    public String getAllEvents(Model model) {
        List<Event> events = bookingFacade.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }
}
