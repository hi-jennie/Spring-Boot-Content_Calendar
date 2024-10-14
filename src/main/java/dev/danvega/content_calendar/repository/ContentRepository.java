package dev.danvega.content_calendar.repository;

import dev.danvega.content_calendar.model.Content;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// 使用 ListCrudRepository，Spring 自动为其提供实现，无需 @Repository
@Repository
public interface ContentRepository extends ListCrudRepository<Content, Integer> {

    // ContentType 会被映射为 实体类的字段 contentType。
    //	通过 JPA 的默认命名规范，实体字段 contentType 会映射到数据库列 content_type。
    List<Content> findAllByContentType(String type);

    // 通过 @Query 注解，可以使用自定义的 SQL 查询语句
    @Query("SELECT * FROM content WHERE status = :status")
    List<Content> findAllByStatus(@Param("status") String status);
}

// 返回类型是 List：与 CrudRepository 不同的是，ListCrudRepository 中的查询方法返回 List，而不是 Iterable，这使得处理结果更加方便。