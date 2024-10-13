package dev.danvega.content_calendar.model;

import java.time.LocalDateTime;

public record Content(
        Integer id,
        String title,
        String description,
        Status status,
        Type contentType,
        LocalDateTime dataCreated,
        LocalDateTime dataUpdated,
        String url) {

}
