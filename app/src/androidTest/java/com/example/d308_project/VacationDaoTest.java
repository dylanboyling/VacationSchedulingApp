package com.example.d308_project;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.d308_project.dao.VacationDao;
import com.example.d308_project.database.DatabaseBuilder;
import com.example.d308_project.entities.Vacation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class VacationDaoTest {

    private VacationDao vacationDao;
    private DatabaseBuilder db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, DatabaseBuilder.class).build();
        vacationDao = db.vacationDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertVacation() throws Exception {
        Vacation vacation = new Vacation("Test Vacation", "Test Hotel", "2024-01-01", "2024-01-10");
        vacationDao.insert(vacation);

        List<Vacation> vacations = vacationDao.getAllVacations();
        assertEquals(1, vacations.size());
        assertEquals("Test Vacation", vacations.get(0).getName());
    }

    @Test
    public void updateVacation() throws Exception {
        Vacation vacation = new Vacation("Test Vacation", "Test Hotel", "2024-01-01", "2024-01-10");
        vacationDao.insert(vacation);

        List<Vacation> vacations = vacationDao.getAllVacations();
        assertEquals(1, vacations.size());

        Vacation vacationToUpdate = vacations.get(0);
        vacationToUpdate.setName("Updated Vacation");
        vacationDao.update(vacationToUpdate);

        vacations = vacationDao.getAllVacations();
        assertEquals(1, vacations.size());
        assertEquals("Updated Vacation", vacations.get(0).getName());
    }

    @Test
    public void deleteVacation() throws Exception {
        Vacation vacation = new Vacation("Test Vacation", "Test Hotel", "2024-01-01", "2024-01-10");
        vacationDao.insert(vacation);

        List<Vacation> vacations = vacationDao.getAllVacations();
        assertEquals(1, vacations.size());

        Vacation vacationToDelete = vacations.get(0);
        vacationDao.delete(vacationToDelete);

        vacations = vacationDao.getAllVacations();
        assertEquals(0, vacations.size());
    }
}
