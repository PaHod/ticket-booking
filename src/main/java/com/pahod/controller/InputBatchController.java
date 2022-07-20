package com.pahod.controller;

import com.pahod.service.BookingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller()
@RequestMapping("/upload-tickets")
public class InputBatchController {
    private static final Logger logger = LoggerFactory.getLogger(InputBatchController.class);

    @Autowired
    BookingFacade bookingFacade;

    @GetMapping()
    public String uploadForm() {
        return "upload-tickets";
    }

    @PostMapping()
    public String uploadTicketsBatch(@RequestParam("file") MultipartFile multipartFile, Model model) {

        bookingFacade.uploadTicketsBatch(multipartFile);

        String message = "File uploaded  " + multipartFile.getOriginalFilename();
        logger.info(message);
        model.addAttribute("message", message);
        return "tickets-loaded";
    }
}
