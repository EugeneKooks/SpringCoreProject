package ua.epam.spring.hometask.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.AbstractStaticStorage;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

@Repository
public class EventDaoImpl extends AbstractStaticStorage<Event> {

    @Override
    protected Class<Event> getDomainClass() {
        return Event.class;
    }
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EventDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event save(@Nonnull Event object) {
        saveAirDates(object.getId(), object.getAirDates());
        jdbcTemplate.update(
                "insert into EVENTS (id, eventname, baseprice, rating) VALUES (? ,? ,?, ?)",
                object.getId(),
                object.getName(),
                object.getBasePrice(),
                object.getRating().toString()
        );
        return object;
    }

    private void saveAirDates(long id, NavigableSet<LocalDateTime> airDates) {
        for (LocalDateTime ldt :
                airDates) {
            Timestamp timestamp = Timestamp.valueOf(ldt);
            jdbcTemplate.update(
                    "insert into AIRDATES (number, timestamp) values (? , ?)", id, timestamp
            );
        }
    }

    private NavigableSet<LocalDateTime> getAirDatesFor(long id) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "select * from AIRDATES where eventid = ?", new Object[]{id});
        NavigableSet<LocalDateTime> airdates = new TreeSet<>();
        for (Map row : rows) {
            Timestamp bd = (Timestamp) row.get("airdate");
            LocalDateTime ldt = bd.toLocalDateTime();
            airdates.add(ldt);
        }
        return airdates;
    }

    @Override
    public void remove(@Nonnull Event object) {
        jdbcTemplate.update("delete from events where id = ?", object.getId());
    }


    @Override
    public Event getById(@Nonnull Long id) {
        return jdbcTemplate.queryForObject(
                "select * from EVENTS where id = ?",
                new Object[]{id},
                (resultSet, i) -> setUpEvent(resultSet));
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from EVENTS");
        List<Event> events = new ArrayList<>();
        for (Map row : rows) {
            Event event = new Event();
            event.setId(((BigDecimal) row.get("id")).longValue());
            event.setName((String) row.get("eventname"));
            event.setBasePrice(((BigDecimal) row.get("baseprice")).doubleValue());
            event.setRating(EventRating.valueOf((String) row.get("rating")));
            event.setAirDates(getAirDatesFor(event.getId()));
            events.add(event);
        }
        return events;
    }

        public Event getByName(String name) {
        return jdbcTemplate.queryForObject(
                "select * from EVENTS where eventname = ?",
                new Object[]{name},
                (resultSet, i) -> setUpEvent(resultSet)
        );
    }

    public Event setUpEvent(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getLong("id"));
        event.setName(resultSet.getString("eventname"));
        event.setBasePrice(resultSet.getDouble("baseprice"));
        event.setRating(EventRating.valueOf(resultSet.getString("rating")));
        event.setAirDates(getAirDatesFor(event.getId()));
        return event;
    }
}
