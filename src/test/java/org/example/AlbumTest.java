package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    Album albumA, albumB;
    Artist artistA, artistB;
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
    void getName() {
        assertEquals(albumA.getName(), "Test Album 1");
        assertEquals(albumB.getName(), "");
    }

    @Test
    void testEquals() {
        albumA.setArtist(artistA);
        albumB.setArtist(artistB);
        assertTrue(albumA.equals(albumA));
        assertFalse(albumA.equals(albumB));
    }

    @Test
    void getSongs() {
        assertNull(albumA.getSongs());

        ArrayList<Song> songList = new ArrayList<>();
        songList.add(songA);

        albumA.setSongs(songList);

        assertTrue(albumA.getSongs().size() == 1);
    }

    @Test
    void setSongs() {
        assertNull(albumA.songs);

        ArrayList<Song> songList = new ArrayList<>();
        songList.add(songA);

        albumA.setSongs(songList);
        assertTrue(albumA.songs.size() == 1);

        songList.add(songB);

        albumA.setSongs(songList);
        assertTrue(albumA.songs.size() == 2);
    }

    @Test
    void getArtist() {
        assertNull(albumA.getArtist());

        albumA.setArtist(artistA);
        assertEquals(albumA.getArtist().name, "Artist 1");
    }

    @Test
    void setArtist() {
        albumA.setArtist(artistA);

        assertEquals(albumA.getArtist().name, "Artist 1");
        assertNull(albumB.getArtist());
    }

    @Test
    void getAudioDbId() {
        assertEquals(albumA.getAudioDbId(),0);

        albumA.setAudioDbId(12345);
        assertEquals(albumA.getAudioDbId(), 12345);
    }

    @Test
    void setAudioDbId() {
        albumA.setAudioDbId(12345);

        assertEquals(albumA.getAudioDbId(), 12345);
        assertEquals(albumB.getAudioDbId(),0);
    }

    @Test
    void toSQL() {
        artistA.setAudioDbId(67890);

        albumA.setArtist(artistA);
        albumA.setAudioDbId(12345);

        String query = "insert into albums (id, name, audioDBId, audioDBArtistId) values (12345, \"Test Album 1\", 12345, 67890);";
        assertEquals(albumA.toSQL(),query);
    }

    @Test
    void fromSQL() {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library_Test.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            String statementQuery = "Select * from albums";

            ResultSet rs1 = statement.executeQuery(statementQuery);

            int size = 0;
            while(rs1.next()) {
                size++;
            }

            // check that no albums exist
            assertEquals(size,0);

            String insertQuery = "INSERT INTO albums (id, name) values (1,\"Another New Album\");";
            statement.executeUpdate(insertQuery);

            ResultSet rs2 = statement.executeQuery(statementQuery);
            albumB.fromSQL(rs2);

            assertEquals(albumB.entityID, 1);
            assertEquals(albumB.name, "Another New Album");

//            remove album from DB
            String removeQuery = "DELETE FROM albums WHERE name = 'Another New Album'";
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