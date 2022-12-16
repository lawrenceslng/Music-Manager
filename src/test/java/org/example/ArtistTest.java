package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {

    Artist artistA, artistB;
    Album albumA, albumB;
    Song songA, songB;

    @BeforeEach
    void setup(){
        albumA = new Album("Test Album 1");
        albumB = new Album("");

        artistA = new Artist("Artist 1");
        artistB = new Artist("Artist 2");

        songA = new Song();
        songB = new Song();
    }

    @Test
    void getSongs() {
        assertNull(artistA.getSongs());

        ArrayList<Song> songList = new ArrayList<>();
        songList.add(songA);

        artistA.setSongs(songList);

        assertTrue(artistA.getSongs().size() == 1);
    }

    @Test
    void setSongs() {
        assertNull(artistA.songs);

        ArrayList<Song> songList = new ArrayList<>();
        songList.add(songA);

        artistA.setSongs(songList);
        assertTrue(artistA.songs.size() == 1);

        songList.add(songB);

        artistA.setSongs(songList);
        assertTrue(artistA.songs.size() == 2);
    }

    @Test
    void getAlbums() {
        assertNull(artistA.getAlbums());

        ArrayList<Album> albumList = new ArrayList<>();
        albumList.add(albumA);

        artistA.setAlbums(albumList);

        assertTrue(artistA.getAlbums().size() == 1);
    }

    @Test
    void setAlbums() {
        assertNull(artistA.getAlbums());

        ArrayList<Album> albumList = new ArrayList<>();
        albumList.add(albumA);

        artistA.setAlbums(albumList);
        assertTrue(artistA.getAlbums().size() == 1);

        albumList.add(albumB);

        artistA.setAlbums(albumList);
        assertTrue(artistA.getAlbums().size() == 2);
    }

    @Test
    void addSong() {
        assertNull(artistA.getSongs());

        ArrayList<Song> songList = new ArrayList<>();
        artistA.setSongs(songList);

        artistA.addSong(songA);

        assertTrue(artistA.getSongs().size() == 1);

        artistA.addSong(songB);

        assertTrue(artistA.getSongs().size() == 2);
    }

    @Test
    void getNationality() {
        assertNull(artistA.getNationality());

        artistA.setNationality("US");

        assertEquals(artistA.getNationality(), "US");
    }

    @Test
    void setNationality() {
        assertNull(artistA.getNationality());

        artistA.setNationality("US");

        assertEquals(artistA.nationality, "US");
    }

    @Test
    void getAudioDbId() {
        assertEquals(artistA.getAudioDbId(),0);

        artistA.setAudioDbId(12345);
        assertEquals(artistA.getAudioDbId(), 12345);
    }

    @Test
    void setAudioDbId() {
        artistA.setAudioDbId(12345);

        assertEquals(artistA.getAudioDbId(), 12345);
        assertEquals(artistB.getAudioDbId(),0);
    }

    @Test
    void testEquals() {
        assertFalse(artistA.equals(artistB));
        artistB.setName("Artist 1");
        assertTrue(artistA.equals(artistB));
    }

    @Test
    void toSQL() {
        artistA.setAudioDbId(67890);
        artistA.setNationality("US");

        String query = "INSERT INTO artists (id, name, audioDBId, country) VALUES (67890, \"Artist 1\", 67890, \"US\");";
        assertEquals(artistA.toSQL(),query);
    }

    @Test
    void fromSQL() {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library_Test.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            String statementQuery = "Select * from artists";

            ResultSet rs1 = statement.executeQuery(statementQuery);

            int size = 0;
            while(rs1.next()) {
                size++;
            }

            // check that no artists exist
            assertEquals(size,0);

            String insertQuery = "INSERT INTO artists (id, name) values (1,\"Another New Artist\");";
            statement.executeUpdate(insertQuery);

            ResultSet rs2 = statement.executeQuery(statementQuery);
            artistA.fromSQL(rs2);

            assertEquals(artistA.entityID, 1);
            assertEquals(artistA.name, "Another New Artist");


//            remove artist from DB
            String removeQuery = "DELETE FROM artists WHERE name = 'Another New Artist'";
            statement.executeUpdate(removeQuery);

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
}