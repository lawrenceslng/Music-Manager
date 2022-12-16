package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {

    Playlist playlist, playlist1, playlist2;
    Song s1, s2, s3, s4, s5, s6;
    Artist a1, a2, a3, a4, a5, a6;
    Album album1, album2, album3, album4, album5, album6;

    @BeforeEach
    void setup(){
        playlist = new Playlist();
        playlist1 = new Playlist();
        playlist2 = new Playlist();

        s1 = new Song();
        a1 = new Artist("Artist 1");
        album1 = new Album("Album 1");

        s1.setName("Song 1");
        s1.setPerformer(a1);
        s1.setAlbum(album1);
        s1.setListeners(5);
        s1.setLength(255);
        album1.setArtist(a1);

        s2 = new Song();
        a2 = new Artist("Artist 2");
        album2 = new Album("Album 2");

        s2.setName("Song 2");
        s2.setPerformer(a2);
        s2.setAlbum(album2);
        s2.setListeners(10);
        s2.setLength(175);
        album1.setArtist(a2);

        s3 = new Song();
        a3 = new Artist("Artist 3");
        album3 = new Album("Album 3");

        s3.setName("Song 3");
        s3.setPerformer(a3);
        s3.setAlbum(album3);
        s3.setListeners(15);
        s3.setLength(85);
        album1.setArtist(a3);

        s4 = new Song();
        a4 = new Artist("Artist 4");
        album4 = new Album("Album 4");

        s4.setName("Song 4");
        s4.setPerformer(a4);
        s4.setAlbum(album4);
        s4.setListeners(20);
        s4.setLength(205);
        album1.setArtist(a4);

        s5 = new Song();
        a5 = new Artist("Artist 5");
        album5 = new Album("Album 5");

        s5.setName("Song 5");
        s5.setPerformer(a5);
        s5.setAlbum(album5);
        s5.setListeners(25);
        s5.setLength(195);
        album1.setArtist(a5);

        s6 = new Song();
        a6 = new Artist("Artist 6");
        album6 = new Album("Album 6");

        s6.setName("Song 6");
        s6.setPerformer(a6);
        s6.setAlbum(album6);
        s6.setListeners(30);
        s6.setLength(225);
        album1.setArtist(a6);
    }

    @Test
    void addSong() {
        assertEquals(playlist.size(), 0);

        playlist.addSong(s1);

        assertEquals(playlist.size(), 1);

        playlist.addSong(s1);

        // cannot add same song twice to playlist
        assertEquals(playlist.size(), 1);
    }

    @Test
    void deleteSong() {
        assertEquals(playlist.size(), 0);

        InputStream sysInBackup = System.in;

        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        playlist.deleteSong("Song 1");

        playlist.addSong(s1);

        assertEquals(playlist.size(), 1);

        playlist.deleteSong("Song 1");

        assertEquals(playlist.size(), 0);

        playlist.deleteSong(s1);
        playlist.addSong(s1);
        assertEquals(playlist.size(), 1);
        playlist.deleteSong(s1);
        assertEquals(playlist.size(), 0);

        System.setIn(sysInBackup);
    }

    @Test
    void mergePlaylist(){

        playlist1.addSong(s1);

        playlist2.addSong(s1);
        playlist2.addSong(s2);

        assertEquals(playlist1.size(), 1);
        assertEquals(playlist2.size(), 2);

        playlist1.mergePlaylist(playlist2);

        assertEquals(playlist1.size(), 2);

    }

    @Test
    void sortPlaylist(){

        playlist.addSong(s5);
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s4);
        playlist.addSong(s3);
        playlist.addSong(s6);

        playlist.printSongOrderAndNListeners();

        playlist.sortPlaylist();

        playlist.printSongOrderAndNListeners();
    }

    @Test
    void shufflePlaylist(){

        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);
        playlist.addSong(s6);

        playlist.printSongOrderAndNListeners();

        playlist.shufflePlaylist();

        playlist.printSongOrderAndNListeners();
    }

    @Test
    void outputXML(){
        System.out.println("no test to avoid overwriting existing .xml output");

//        playlist.addSong(s1);
//        playlist.addSong(s2);
//        playlist.addSong(s3);
//        playlist.addSong(s4);
//        playlist.addSong(s5);
//        playlist.addSong(s6);
//
//        playlist.outputXML();
    }

    @Test
    void writeToXML(){

        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);
        playlist.addSong(s6);

        String output = playlist.writeToXML();
        System.out.println(output);
    }

    @Test
    void size() {
        assertEquals(playlist.size(), 0);

        playlist.addSong(s1);

        assertEquals(playlist.size(), 1);

        playlist.addSong(s1);

        // cannot add same song twice to playlist
        assertEquals(playlist.size(), 1);
    }


    @Test
    void printSongOrderAndNListeners() {
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);
        playlist.addSong(s6);

        playlist.printSongOrderAndNListeners();
    }
}