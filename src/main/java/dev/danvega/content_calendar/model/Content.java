package dev.danvega.content_calendar.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

//@Table(name = "Content") // coming from spring-boot-starter-data-jpa dependency
public record Content(
        @Id
        Integer id,
        @NotBlank // coming from spring-boot-starter-validation dependency
        String title,
        String description,
        Status status,
        Type contentType,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated,
        String url) {

}
