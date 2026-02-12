package dtos;

import java.time.LocalDateTime;

public record CreateTicketsDto(
    long eventVenueId,
    long ticketsCount,
    LocalDateTime dateTime
) {
}