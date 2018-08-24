package ua.epam.spring.hometask.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.AbstractStaticStorage;
import ua.epam.spring.hometask.domain.User;

@Repository
public class UserDaoImpl extends AbstractStaticStorage<User> {

	private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	@Override
	protected Class<User> getDomainClass() {
		return User.class;
	}
	@Override
    public User save(@Nonnull User object) {
        try {
            getById(object.getId());
            updateUser(object);
        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update(
                    "INSERT INTO USERS (id, firstname, lastname, email) VALUES (? ,? ,? ,?)",
                    object.getId(),
                    object.getFirstName(),
                    object.getLastName(),
                    object.getEmail()
            );
        }
        return object;
    }

    private void updateUser(User object) {
        jdbcTemplate.update(
                "UPDATE USERS SET firstname = ?, lastname = ?, email= ? where id = ?",
                object.getFirstName(),
                object.getLastName(),
                object.getEmail(),
                object.getId()
        );
    }

    @Override
    public void remove(@Nonnull User object) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", object.getId());
    }

    @Override
    public User getById(@Nonnull Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM USERS WHERE id = ?",
                new Object[]{id},
                (resultSet, i) -> setUpUser(resultSet)
        );
    }

    @Override
    public Collection<User> getAll() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM USERS");
        List<User> users = new ArrayList<>();
        for (Map row : rows) {
            User user = new User();
            user.setId(((BigDecimal) row.get("id")).longValue());
            user.setEmail((String) row.get("email"));
            user.setFirstName((String) row.get("firstname"));
            user.setLastName((String) row.get("lastname"));
            users.add(user);
        }
        return users;
    }
    
    public User getUserByEmail(@Nonnull String email) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM USERS WHERE email = ?",
                new Object[]{email},
                (resultSet, i) -> setUpUser(resultSet)
        );
    }

    private User setUpUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));
        return user;
    }
}