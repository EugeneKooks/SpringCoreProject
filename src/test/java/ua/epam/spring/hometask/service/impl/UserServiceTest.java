package ua.epam.spring.hometask.service.impl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;
import ua.epam.spring.hometask.service.UserService;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class UserServiceTest {

    private UserService userService;
    private List<User> testData = new ArrayList<>();

    @BeforeClass
    @SuppressWarnings("unchecked")
    public void init() {
        AbstractDomainObjectService<User> userDAO = (AbstractDomainObjectService<User>) mock(AbstractDomainObjectService.class);
        testData.add(new User("First", "User", "first@mail.com"));
        testData.add(new User("Second", "User", "second@mail.com"));
        testData.add(new User("Third", "User", "third@mail.com"));
        when(userDAO.getAll()).thenReturn(testData);

        userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void testGetUserByExistentEmail() {
        User ethalonUser = testData.get(1);
        User user = userService.getUserByEmail(ethalonUser.getEmail());
        assertNotNull(user, "User must be not null");
        assertEquals(user, ethalonUser);
    }

    @Test
    public void testGetUserByNotExistentEmail() {
        User user = userService.getUserByEmail("fake@mail.com");
        assertNull(user, "Return value must be null");
    }

}
