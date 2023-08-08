package com.example.fin;

import com.example.fin.database.Sqlite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class FinansTest {
    @Test
    void checkUpdate() {
    }

    @Test
    void getSum() {
        try(MockedStatic<Sqlite> sqlite = Mockito.mockStatic(Sqlite.class)) {
            Pillow pillow = new Pillow(1000, 25, 10, 1000, 5000);
            sqlite.when(Sqlite::getPillow).thenReturn(pillow);
            double expected = 1000;
            double result = Finans.getSum();
            assertEquals(expected, result);
        }
    }

    @Test
    void getSumWithNull() {
        try(MockedStatic<Sqlite> sqlite = Mockito.mockStatic(Sqlite.class)) {
            sqlite.when(Sqlite::getPillow).thenReturn(null);
            double expected = 0;
            double result = Finans.getSum();
            assertEquals(expected, result);
        }
    }

    @Test
    void update() {
    }

    @Test
    void savePillow() {
    }
}