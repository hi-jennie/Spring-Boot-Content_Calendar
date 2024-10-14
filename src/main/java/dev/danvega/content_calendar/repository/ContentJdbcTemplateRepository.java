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
