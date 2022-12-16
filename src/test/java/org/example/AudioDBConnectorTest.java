package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioDBConnectorTest {

    @Test
    void testArtistSearch() {
        Artist newArtist = AudioDBConnector.artistSearch("coldplay");

        assertEquals(newArtist.getName(),"Coldplay");
        assertEquals(newArtist.getAudioDbId(),111239);
        assertEquals(newArtist.getNationality(),"GB");
    }

    @Test
    void testArtistIdSearch() {
        Artist newArtist = AudioDBConnector.artistIdSearch(111239);

        assertEquals(newArtist.getName(),"Coldplay");
        assertEquals(newArtist.getAudioDbId(),111239);
        assertEquals(newArtist.getNationality(),"GB");
    }

    @Test
    void testAlbumSearch() {
        Album newAlbum = AudioDBConnector.albumSearch("coldplay", "parachutes");

        assertEquals(newAlbum.getName(),"Parachutes");
        assertEquals(newAlbum.getAudioDbId(),2109615);
    }

    @Test
    void testAlbumIdSearch() {
        Album newAlbum = AudioDBConnector.albumIdSearch(2115888);

        assertEquals(newAlbum.getName(),"Echoes of Silence");
        assertEquals(newAlbum.getAudioDbId(),2115888);
    }

    @Test
    void testSongSearch() {
        Song newSong = AudioDBConnector.songSearch("coldplay", "yellow");

        assertEquals(newSong.getName(),"Yellow");
        assertEquals(newSong.getAudioDBId(),32724184);
        assertEquals(newSong.getListeners(),1635592);
    }
}