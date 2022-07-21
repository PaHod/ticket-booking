package com.pahod.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pahod.dto.TicketDetailedInfoDTO;
import com.pahod.exception.SomethingWentWrongException;
import org.springframework.http.ContentDisposition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneratePdf {
    public static ByteArrayInputStream ticketsDetailsPdf(List<TicketDetailedInfoDTO> bookedTickets) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        try {
            PdfPTable table = new PdfPTable(7);


            table.setWidthPercentage(90);
            table.setWidths(new int[]{3, 1, 3, 3, 1, 1, 1});
            Font hFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            table.addCell(getCell("Event Title", hFont));
            table.addCell(getCell("Event Id", hFont));
            table.addCell(getCell("Event Date", hFont));
            table.addCell(getCell("Event Location", hFont));

            table.addCell(getCell("Ticket Id", hFont));
            table.addCell(getCell("Ticket Place", hFont));
            table.addCell(getCell("Ticket Price", hFont));
            long userId = -1;
            String userName = null;

            for (TicketDetailedInfoDTO ticket : bookedTickets) {
                if (userName == null) {
                    userId = ticket.getUserId();
                    userName = ticket.getUserName();
                }
                table.addCell(getCell(ticket.getEventTitle()));
                table.addCell(getCell(ticket.getEventId()));
                String formattedDate = new SimpleDateFormat("HH:mm dd-MM-yyyy").format(ticket.getEventDate());
                table.addCell(getCell(formattedDate));
                table.addCell(getCell(ticket.getEventLocation()));


                table.addCell(getCell(ticket.getTicketId()));
                table.addCell(getCell(ticket.getTicketPlace()));
                table.addCell(getCell(ticket.getTicketPrice()));
            }
            String string = String.format("User Id: %d User Name: %s", userId, userName);
            Phrase phrase = new Phrase(string, hFont);

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(phrase);
            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
            throw new SomethingWentWrongException("PDF is not generated.");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static PdfPCell getCell(Object s) {
        return getCell(s, new Font());
    }

    private static PdfPCell getCell(Object s, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(s.toString(), font));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public static ContentDisposition getDisposition(String displayType) {
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
