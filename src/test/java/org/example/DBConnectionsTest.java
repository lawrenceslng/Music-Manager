package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionsTest {

    Library library;
    @BeforeEach
    void setup() {
        library = new Library();
    }

    @Test
    void populateLibrary() {
        assertTrue(library.getSongs().size() == 0);

        DBConnections.populateLibrary(library);

        assertNotNull(library.getSongs());

        for(Song song : library.getSongs()){
            System.out.println(song.getName());
        }
    }

    @Test
    void getAllArtists() {
        ArrayList<String> artistList = new ArrayList<>();

        artistList = DBConnections.getAllArtists();

        assertTrue(artistList.size() > 0);

        for(String artistName : artistList){
            System.out.println(artistName);
        }
    }

    @Test
    void getAllAlbums() {
        ArrayList<String> albumList = new ArrayList<>();

        albumList = DBConnections.getAllArtists();

        assertTrue(albumList.size() > 0);

        for(String albumName : albumList){
            System.out.println(albumName);
        }
    }

    @Test
    void addNewSong() {
        Song newSong = new Song("Song 1");
        Artist newArtist = new Artist("Artist 1");
        Album newAlbum = new Album("Album 1");

        newSong.setListeners(150);
        newSong.setAudioDBId(12345);
        newSong.setLength(213);
        newSong.setPerformer(newArtist);
        newSong.setAlbum(newAlbum);

        newArtist.setNationality("GB");
        newArtist.setAudioDbId(67890);

        newAlbum.setArtist(newArtist);
        newAlbum.setAudioDbId(13579);

        boolean success = DBConnections.addNewSong(newSong);
        assertTrue(success);

        // remove song manually from DB afterwards
        // delete from songs where name = 'Song 1';
        String query = "delete from songs where name = 'Song 1';";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    @Test
    void addNewArtist() {
        Song newSong = new Song("Song 1");
        Artist newArtist = new Artist("Artist 1");
        Album newAlbum = new Album("Album 1");

        newSong.setListeners(150);
        newSong.setAudioDBId(12345);
        newSong.setLength(213);
        newSong.setPerformer(newArtist);
        newSong.setAlbum(newAlbum);

        newArtist.setNationality("GB");
        newArtist.setAudioDbId(67890);

        newAlbum.setArtist(newArtist);
        newAlbum.setAudioDbId(13579);

        boolean success = DBConnections.addNewArtist(newArtist);
        assertTrue(success);

        // remove artist manually from DB afterwards
        // delete from artists where name = 'Artist 1';
        String query = "delete from artists where name = 'Artist 1';";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    @Test
    void addNewAlbum() {
        Song newSong = new Song("Song 1");
        Artist newArtist = new Artist("Artist 1");
        Album newAlbum = new Album("Album 1");

        newSong.setListeners(150);
        newSong.setAudioDBId(12345);
        newSong.setLength(213);
        newSong.setPerformer(newArtist);
        newSong.setAlbum(newAlbum);

        newArtist.setNationality("GB");
        newArtist.setAudioDbId(67890);

        newAlbum.setArtist(newArtist);
        newAlbum.setAudioDbId(13579);

        boolean success = DBConnections.addNewAlbum(newAlbum);
        assertTrue(success);

        // remove albums manually from DB afterwards
        // delete from albums where name = 'Album 1';
        String query = "delete from albums where name = 'Album 1';";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

    }

    @Test
    void findArtist() {
        Artist foundArtist = DBConnections.findArtist(111256);

        assertEquals(foundArtist.getName(), "Elton John");
        assertEquals(foundArtist.getNationality(), "GB");
    }

    @Test
    void findAlbum() {
        Album foundAlbum = DBConnections.findAlbum(2113815);

        assertEquals(foundAlbum.getName(), "Live in Australia");
    }

    @Test
    void findSong() {
        Song foundSong = DBConnections.findSong(32768127);

        assertEquals(foundSong.getName(), "Tiny Dancer");
    }

}