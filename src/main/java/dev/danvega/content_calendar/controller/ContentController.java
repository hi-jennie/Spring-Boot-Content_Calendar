package dev.danvega.content_calendar.controller;

import dev.danvega.content_calendar.model.Content;
import dev.danvega.content_calendar.repository.ContentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    private final ContentRepository repository;

    public ContentController(ContentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Content> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Content> findById(@PathVariable Integer id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found.")));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody Content content) {
        if (content.id() != null) {
            // 因为在创建表格的时候id配置了自增，所以这里不需要传入id否则会报错
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID should not be provided when creating new content.");
        }
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Content content, @PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found.");
        }
        // 确保传入对象的 ID 与路径参数一致
        if (!id.equals(content.id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and request body must match.");
        }
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @GetMapping("/filter/type/{type}")
    public List<Content> filterByType(@PathVariable String type) {
        return repository.findAllByContentType(type.toUpperCase());
    }
}