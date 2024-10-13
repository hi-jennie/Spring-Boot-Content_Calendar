package dev.danvega.content_calendar.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record Content(
        Integer id,
        @NotBlank // coming from spring-boot-starter-validation dependency
        String title,
        String description,
        Status status,
        Type contentType,
        LocalDateTime dataCreated,
        LocalDateTime dataUpdated,
        String url) {

}
