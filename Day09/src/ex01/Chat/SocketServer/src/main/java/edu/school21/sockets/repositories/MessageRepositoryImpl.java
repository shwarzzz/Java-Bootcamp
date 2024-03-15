package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Component
public class MessageRepositoryImpl implements MessageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        validateObject(id, "id");
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(INSERT_QUERY,
                    new MessageMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        try {
            return jdbcTemplate.query(FIND_ALL_QUERY,
                    new MessageMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void save(Message entity) {
        validateObject(entity, "entity");
        validateObject(entity.getSenderName(), "sender");
        validateObject(entity.getText(), "message_text");
        validateObject(entity.getSendingTime(), "sending time");
        jdbcTemplate.update(INSERT_QUERY, entity.getSenderName(),
                entity.getText(), entity.getSendingTime());
    }

    @Override
    public void update(Message entity) {
        validateObject(entity, "entity");
        validateObject(entity.getId(), "id");
        validateObject(entity.getSenderName(), "sender");
        validateObject(entity.getText(), "message_text");
        validateObject(entity.getSendingTime(), "sending time");
        jdbcTemplate.update(UPDATE_QUERY, entity.getSenderName(),
                entity.getText(), entity.getSendingTime(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        validateObject(id, "id");
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    private void validateObject(Object obj, String objectName) {
        if (obj == null) {
            throw new IllegalArgumentException("Message's " + objectName
                    + " can't be null");
        }
    }

    private static class MessageMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Message(resultSet.getLong("id"),
                    resultSet.getString("sender"),
                    resultSet.getString("message_text"),
                    resultSet.getTimestamp("sending_time"));
        }
    }
}
