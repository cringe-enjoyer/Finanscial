package com.example.fin;

import com.example.fin.database.Sqlite;
import com.example.fin.model.Cushion;
import com.example.fin.model.Finans;
import com.example.fin.model.UserData;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class FinansTest {
    @Test
    void checkUpdate() {
    }

    @Test
    void getUserData() {
        try(MockedStatic<Sqlite> sqlite = Mockito.mockStatic(Sqlite.class)) {
            UserData userData = new UserData(new Cushion(1000, 25), 10, 1000, 5000);
            sqlite.when(Sqlite::getUserData).thenReturn(userData);

            UserData expected = new UserData(new Cushion(1000, 25), 10, 1000, 5000);
            UserData result = Finans.getUserData();
            assertEquals(expected, result);
        }
    }

    @Test
    void getSumWithNull() {
        try(MockedStatic<Sqlite> sqlite = Mockito.mockStatic(Sqlite.class)) {
            sqlite.when(Sqlite::getUserData).thenReturn(null);
            UserData result = Finans.getUserData();
            assertNull(result);
        }
    }

    @Test
    void update() {
    }

    @Test
    void savePillow() {
    }
}