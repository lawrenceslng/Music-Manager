package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    Artist artistA, artistB;
    Album albumA, albumB;
    Song songA, songB;

    @BeforeEach
    void setup() {
        albumA = new Album("Test Album 1");
        albumB = new Album("");

        artistA = new Artist("Artist 1");
        artistB = new Artist("Artist 2");

        songA = new Song();
        songB = new Song();
    }

    @Test
    void getGenre() {
        assertNull(songA.getGenre());
        songB.genre = "";

        assertTrue(songB.getGenre() == "");

        songA.genre = "R&B";
        assertEquals(songA.getGenre(),"R&B");
    }

    @Test
    void setGenre() {
        assertNull(songA.getGenre());

        songA.setGenre("R&B");
        assertEquals(songA.genre, "R&B");
    }

    @Test
    void setLength() {
        assertNull(songA.duration);

        songA.setLength(100);
        assertEquals(songA.showLength(), "1:40");
    }

    @Test
    void showLength() {
        songA.setLength(50);
        assertEquals(songA.showLength(), "0:50");
    }

    @Test
    void getAlbum() {
        assertNull(songA.getAlbum());

        songA.album = albumA;
        assertEquals(songA.getAlbum(), albumA);
    }

    @Test
    void setAlbum() {
        songA.setAlbum(albumA);
        assertEquals(songA.album, albumA);
    }

    @Test
    void getPerformer() {
        assertNull(songA.getPerformer());

        songA.performer = artistA;
        assertEquals(songA.getPerformer(), artistA);
    }

    @Test
    void setPerformer() {
        songA.setPerformer(artistA);
        assertEquals(songA.performer, artistA);
    }

    @Test
    void getListeners() {
        assertTrue(songA.numListeners == 0);

        songA.setListeners(145029);
        assertEquals(songA.getListeners(), 145029);
    }

    @Test
    void setListeners() {
        songA.setListeners(145029);
        assertEquals(songA.numListeners, 145029);
    }

    @Test
    void getAudioDBId() {
        assertEquals(songA.getAudioDBId(),0);

        songA.setAudioDBId(12345);
        assertEquals(songA.getAudioDBId(), 12345);
    }

    @Test
    void setAudioDBId() {
        songA.setAudioDBId(12345);

        assertEquals(songA.getAudioDBId(), 12345);
        assertEquals(songB.getAudioDBId(),0);
    }

    @Test
    void testToString() {
        songA.setPerformer(artistA);
        songA.setAlbum(albumA);
        songA.setLength(123);
        songA.setName("Song 1");

        String songId = String.valueOf(songA.entityID);
        String artistId = String.valueOf(artistA.entityID);
        String albumId = String.valueOf(albumA.entityID);

        String query = "Name: Song 1 Entity ID: " + songId + " Name: Artist 1 Entity ID: " + artistId + " Name: Test Album 1 Entity ID: " + albumId + " 2:03";
        assertEquals(songA.toString(),query);
    }

    @Test
    void testEquals() {
        songA.setPerformer(artistA);
        songB.setPerformer(artistA);

        songA.setAlbum(albumA);
        songB.setAlbum(albumB);

        albumA.setArtist(artistA);
        albumB.setArtist(artistA);
        albumB.setName("Test Album 1");

        songA.setName("Song 1");
        songB.setName("Song 1");

        assertTrue(songA.equals(songB));

        albumB.setName("Some other Name");
        assertFalse(songA.equals(songB));
    }

    @Test
    void toXML() {
        songA.setAudioDBId(12345);
        songA.setName("Song 1");

        artistA.setAudioDbId(67890);
        songA.setPerformer(artistA);

        songA.setAlbum(albumA);
        albumA.setArtist(artistA);
        albumA.setAudioDbId(13579);

        songA.setListeners(155);

        String query = "<song id=\"12345\"><title>Song 1</title><artist id=\"67890\">Artist 1</artist><album id=\"13579>Test Album 1</album><numOfListeners>155</numOfListeners></song>";
        assertEquals(songA.toXML(), query);
    }

    @Test
    void toSQL() {
        songA.setAudioDBId(12345);
        songA.setName("Song 1");

        artistA.setAudioDbId(67890);
        songA.setPerformer(artistA);

        songA.setAlbum(albumA);
        albumA.setArtist(artistA);
        albumA.setAudioDbId(13579);

        songA.setListeners(155);

        String query = "insert into songs (id, audioDBId, name, audioDBArtistId, audioDBAlbumId, nlisteners) values (12345, 12345, \"Song 1\", 67890, 13579, 155);";

        assertEquals(songA.toSQL(), query);
    }

    @Test
    void fromSQL() {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library_Test.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            String statementQuery = "Select * from songs";

            ResultSet rs1 = statement.executeQuery(statementQuery);

            int size = 0;
            while(rs1.next()) {
                size++;
            }

            // check that no artists exist
            assertEquals(size,0);

            String insertQuery = "INSERT INTO songs (id, name) values (1,\"Another New Song\");";
            statement.executeUpdate(insertQuery);

            ResultSet rs2 = statement.executeQuery(statementQuery);
            songA.fromSQL(rs2);

            assertEquals(songA.entityID, 1);
            assertEquals(songA.name, "Another New Song");


//            remove artist from DB
            String removeQuery = "DELETE FROM songs WHERE name = 'Another New Song'";
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

    @Test
    void compareTo() {
        songA.setListeners(150);
        songB.setListeners(300);

        assertEquals(songA.compareTo(songB), 1);
        assertEquals(songB.compareTo(songA), -1);
        assertEquals(songA.compareTo(songA), 0);
    }
}