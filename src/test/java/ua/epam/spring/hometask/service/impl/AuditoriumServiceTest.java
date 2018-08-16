package ua.epam.spring.hometask.service.impl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import java.util.*;

import static org.testng.Assert.*;

public class AuditoriumServiceTest {

    private AuditoriumService auditoriumService;

    @BeforeClass
    public void init() {
        Map<String, String> auditoriumData = new HashMap<>();
        auditoriumData.put("First", "10;[1,2,3,8,9,10]");
        auditoriumData.put("Second", "4;[3,4]");
        auditoriumData.put("Third", "4;[]");

        auditoriumService = new AuditoriumServiceImpl(auditoriumData);
    }

    @DataProvider
    public Object[][] auditoriumDataProvider() {
        return new Object[][] {
            {"First", 10, 6},
            {"Second", 4, 2},
            {"Third", 4, 0}
        };
    }

    @Test
    public void testGetAllAuditorium() {
        Set<Auditorium> auditoriums = auditoriumService.getAll();
        assertEquals(auditoriums.size(), 3);
    }

    @Test
    public void testGetByNotExistentName() {
        Auditorium fake = auditoriumService.getByName("fake");
        assertNull(fake);
    }

    @Test(dataProvider = "auditoriumDataProvider")
    public void testGetByName(String name, int totalSeats, int vipSeats) {
        Auditorium auditorium = auditoriumService.getByName(name);
        assertNotNull(auditorium);
        assertEquals(auditorium.getAllSeats().size(), totalSeats);
        assertEquals(auditorium.getVipSeats().size(), vipSeats);
    }
}
