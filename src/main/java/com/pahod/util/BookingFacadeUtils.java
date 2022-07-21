package com.pahod.util;

import com.pahod.dto.Tickets;
import com.pahod.exception.EmptyFileException;
import com.pahod.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BookingFacadeUtils {
    private static final Logger logger = LoggerFactory.getLogger(BookingFacadeUtils.class);

    public static List<Ticket> parseTicketsBatch(MultipartFile file) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Ticket.class, Tickets.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Tickets tickets = (Tickets) unmarshaller.unmarshal(new StreamSource(file.getInputStream()));
        return tickets.getTickets();
    }

    public static <T> List<T> getPageItems(List<T> entities, int pageSize, int pageNum) {
        int pageFirstItemIndex = (pageNum - 1) * pageSize;
        if (pageFirstItemIndex > entities.size()) {
            logger.warn("Exceeded last page, show empty.");
            return Collections.emptyList();
        }
        int pageLastItemIndex = pageFirstItemIndex + pageSize;

        return entities.subList(pageFirstItemIndex, Math.min(entities.size(), pageLastItemIndex));
    }
}
