package dev.danvega.content_calendar.repository;

import dev.danvega.content_calendar.model.Content;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 使用 ListCrudRepository，Spring 自动为其提供实现，无需 @Repository
@Repository
public interface ContentRepository extends ListCrudRepository<Content, Integer> {

    List<Content> findAllByContentType(String type);
}

// 返回类型是 List：与 CrudRepository 不同的是，ListCrudRepository 中的查询方法返回 List，而不是 Iterable，这使得处理结果更加方便。