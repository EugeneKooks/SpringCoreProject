package ua.epam.spring.hometask.dao.impl;

import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.AbstractStaticStorage;
import ua.epam.spring.hometask.domain.Event;

@Repository
public class StaticEventDAO extends AbstractStaticStorage<Event> {

    @Override
    protected Class<Event> getDomainClass() {
        return Event.class;
    }
}
