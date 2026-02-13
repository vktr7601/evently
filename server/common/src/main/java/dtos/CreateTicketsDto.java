package dtos;

import java.time.LocalDateTime;

public record CreateTicketsDto(
    long eventLocationId,
    long ticketsCount,
    LocalDateTime dateTime
) {
}