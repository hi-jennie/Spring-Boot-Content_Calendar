package dev.danvega.content_calendar.repository;


import dev.danvega.content_calendar.model.Content;
import dev.danvega.content_calendar.model.Status;
import dev.danvega.content_calendar.model.Type;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// this just an in-memory repository,doesn't talk to spring database
// using a list to mock a database
@Repository
public class ContentCollectionRepository {
    private final List<Content> contentList = new ArrayList<>();

    public ContentCollectionRepository(){

    }

    public List<Content> findAll(){
        return contentList;
    }

    public Optional<Content> findById(Integer id){
        return contentList.stream().filter(c -> c.id().equals(id)).findFirst();
    }

    @PostConstruct
    public void init(){
        contentList.add(new Content(1, "First Content", "This is the first content", Status.PUBLISHED, Type.ARTICLE, LocalDateTime.now(), null, "http://example.com"));
        contentList.add(new Content(2, "Second Content", "This is the second content",Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "http://example.com"));
    }

    public void save(Content content) {
        contentList.removeIf(c -> c.id().equals(content.id()));
        contentList.add(content);
    }


    public boolean existById(Integer id) {
        return contentList.stream().filter(c -> c.id().equals(id)).count()==1;
    }

    public void delete(Integer id) {
        contentList.removeIf(c -> c.id().equals(id));
    }
}
