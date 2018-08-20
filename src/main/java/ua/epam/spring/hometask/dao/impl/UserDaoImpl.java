package ua.epam.spring.hometask.dao.impl;

import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.AbstractStaticStorage;
import ua.epam.spring.hometask.domain.User;

@Repository
public class UserDaoImpl extends AbstractStaticStorage<User> {

	@Override
	protected Class<User> getDomainClass() {
		return User.class;
	}
}