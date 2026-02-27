package com.evently.booking.ticket.service;

import com.evently.booking.ticket.dto.TicketListItem;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;


public class TicketPdfService {


    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generateTicketPdf(TicketListItem data) throws Exception {
        // 1. Prepare Thymeleaf Context
        Context context = new Context();
        context.setVariable("eventName", data.getEventName());
        context.setVariable("locationName", data.getEventLocationName());
        context.setVariable("eventDate", data.getEventStartTime());
        context.setVariable("eventTime", data.getEventStartTime());
        context.setVariable("ticketNumber", data.getNumber());
        // context.setVariable("qrCode", "data:image/png;base64," +
        // base64String);

        // 2. Render HTML String
        String html = templateEngine.process("ticket-template", context);

        // 3. Generate PDF
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null); // null is the base URI
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        }
    }
}