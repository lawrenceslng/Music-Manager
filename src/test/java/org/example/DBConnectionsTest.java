package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionsTest {

    @Test
    void getAllSongs() {
        Library testLibrary = new Library();
        DBConnections.getAllSongs(testLibrary);
    }
}