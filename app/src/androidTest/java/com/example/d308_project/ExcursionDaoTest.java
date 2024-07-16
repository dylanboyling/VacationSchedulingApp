package com.example.d308_project;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.d308_project.dao.ExcursionDao;
import com.example.d308_project.dao.VacationDao;
import com.example.d308_project.database.DatabaseBuilder;
import com.example.d308_project.entities.Excursion;
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
public class ExcursionDaoTest {
    private VacationDao vacationDao;
    private ExcursionDao excursionDao;
    private DatabaseBuilder db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, DatabaseBuilder.class).build();
        vacationDao = db.vacationDao();
        excursionDao = db.excursionDao();

        Vacation vacation = new Vacation("Test Vacation", "Test Hotel", "2024-01-01", "2024-01-10");
        vacationDao.insert(vacation);
        vacationDao.insert(vacation);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertExcursion() throws Exception {
        Excursion excursion = new Excursion("Test Excursion", "2024-01-05", 1);
        excursionDao.insert(excursion);

        List<Excursion> excursions = excursionDao.getAllExcursions();
        assertEquals(1, excursions.size());
        assertEquals("Test Excursion", excursions.get(0).getName());
    }

    @Test
    public void updateExcursion() throws Exception {
        Excursion excursion = new Excursion("Test Excursion", "2024-01-05", 1);
        excursionDao.insert(excursion);

        List<Excursion> excursions = excursionDao.getAllExcursions();
        assertEquals(1, excursions.size());

        Excursion excursionToUpdate = excursions.get(0);
        excursionToUpdate.setName("Updated Excursion");
        excursionDao.update(excursionToUpdate);

        excursions = excursionDao.getAllExcursions();
        assertEquals(1, excursions.size());
        assertEquals("Updated Excursion", excursions.get(0).getName());
    }

    @Test
    public void deleteExcursion() throws Exception {
        Excursion excursion = new Excursion("Test Excursion", "2024-01-05", 1);
        excursionDao.insert(excursion);

        List<Excursion> excursions = excursionDao.getAllExcursions();
        assertEquals(1, excursions.size());

        Excursion excursionToDelete = excursions.get(0);
        excursionDao.delete(excursionToDelete);

        excursions = excursionDao.getAllExcursions();
        assertEquals(0, excursions.size());
    }

    @Test
    public void deleteExcursionById() throws Exception {
        Excursion excursion = new Excursion("Test Excursion", "2024-01-05", 1);
        excursionDao.insert(excursion);

        List<Excursion> excursions = excursionDao.getAllExcursions();
        assertEquals(1, excursions.size());

        Excursion excursionToDelete = excursions.get(0);
        excursionDao.deleteById(excursionToDelete.getId());

        excursions = excursionDao.getAllExcursions();
        assertEquals(0, excursions.size());
    }

    @Test
    public void getAssociatedExcursions() throws Exception {
        Excursion excursion1 = new Excursion("Test Excursion 1", "2024-01-05", 1);
        Excursion excursion2 = new Excursion("Test Excursion 2", "2024-01-10", 2);
        Excursion excursion3 = new Excursion("Test Excursion 3", "2024-01-15", 1);
        excursionDao.insert(excursion1);
        excursionDao.insert(excursion2);
        excursionDao.insert(excursion3);

        List<Excursion> excursions = excursionDao.getAssociatedExcursions(1);
        assertEquals(2, excursions.size());
        assertEquals("Test Excursion 1", excursions.get(0).getName());
        assertEquals("Test Excursion 3", excursions.get(1).getName());
    }
}
