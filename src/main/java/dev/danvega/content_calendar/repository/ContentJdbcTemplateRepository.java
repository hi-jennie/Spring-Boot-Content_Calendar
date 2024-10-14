package dev.danvega.content_calendar.repository;

import dev.danvega.content_calendar.model.Content;
import dev.danvega.content_calendar.model.Status;
import dev.danvega.content_calendar.model.Type;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContentJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    // DI
    public ContentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Content mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Content(rs.getInt("id"),
                rs.getString("title"),
                rs.getString("desc"),
                Status.valueOf(rs.getString("status")),
                Type.valueOf(rs.getString("content_type")),// 将 content_type 映射为枚举类型
                rs.getObject("date_created", LocalDateTime.class),
                rs.getObject("date_updated", LocalDateTime.class),
                rs.getString("url"));
    }

    public List<Content> findAll() {
        String sql = "SELECT * FROM Content";
        List<Content> contents = jdbcTemplate.query(sql, ContentJdbcTemplateRepository::mapRow);
        return contents;
        // 多次调用 mapRow()，将每一行转换为一个 Content 实例。这是一个循环过程：ResultSet 中有几行，mapRow 就会被调用几次。
    }

    public void createContent(Content content) {
        String sql = "INSERT INTO Content (title, desc, status, content_type, date_created, url) " +
                "VALUES (?, ?, ?, ?, NOW(), ?)";
        jdbcTemplate.update(sql,
                content.title(),
                content.description(),
                content.status().name(),
                content.contentType().name(),
                content.url()
        );
    }

    public void save(Content content) {
        String sql = "UPDATE Content SET title = ?, desc = ?, status = ?, content_type = ?, date_updated = NOW(), url = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                content.title(),
                content.description(),
                content.status().name(),
                content.contentType().name(),
                content.url(),
                content.id()
        );
    }

    public void delete(int id) {
        String sql = "DELETE FROM Content WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public Content findById(int id) {
        String sql = "SELECT * FROM Content WHERE id=?";
        Content content = null;
        try {
            content = jdbcTemplate.queryForObject(sql, new Object[]{id}, ContentJdbcTemplateRepository::mapRow);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found");
        }
        return content;

        // 使用 sql 和 new Object[]{id} 组合，形成完整的 SQL 查询
        // jdbcTemplate.queryForObject() 查询数据库时没有找到与给定 id 匹配的记录，它会抛出一个 EmptyResultDataAccessException 异常
    }
}

/*
 可以升级为spring data jpa
 通过 Spring Data，你可以省略：
	1.	手动 SQL 查询：无需再写 SELECT、INSERT、UPDATE 等语句。
	2.	数据映射逻辑：不用再处理 ResultSet，Spring Data 自动将数据库记录映射为对象。
	3.	异常处理：Spring Data 会自动处理查询为空的情况，并提供 Optional 类型的支持。
	4.	重复的 CRUD 代码：findAll()、save()、deleteById() 等常用方法都已内置。

  如果使用 Spring Data，你的代码将变得更简洁、可读性更高。
  例如，你的 ContentJdbcTemplateRepository 中大量冗余的查询和数据操作都可以直接用 JpaRepository 替代，这样你可以将更多精力放在业务逻辑上，而不是处理数据库操作。
*/