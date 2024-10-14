CREATE TABLE IF NOT EXISTS Content(
    id INTEGER AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description text,
    status VARCHAR(20) NOT NULL,
    content_type VARCHAR(50) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP NOT NULL,
    url VARCHAR(255),
    PRIMARY KEY (id)
);

--  这一步写完：h2 console 里面就应该有content表格了，不区分大小写；执行完下面的insert的操作，content table里面应该就有内容了

INSERT INTO Content (title, description, status, content_type, date_created, date_updated, url)
VALUES ('title1', 'description1', 'active', 'article', '2020-01-01 00:00:00', '2020-01-01 00:00:00', 'http://www.baidu.com');