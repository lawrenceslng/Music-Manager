package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the library that makes up the music manager's content.
 * The library is mainly made up of songs, but there is also a list of artists and albums for easier traversal.
 */
public class Library {
    private ArrayList<Song> songs;
    private ArrayList<Artist> artists;
    private ArrayList<Album> albums;

    /**
     * Class constructor
     */
    public Library() {
        songs = new ArrayList<Song>();
        artists = new ArrayList<Artist>();
        albums = new ArrayList<Album>();
    }

    /**
     * (UNUSED) This method allows users to find if a particular song exists in the library.
     *
     * @param s the Song object that the user is trying to find
     * @return true or false value depending on whether the song exists within the library
     */
    public boolean findSong(Song s) {
        return songs.contains(s);
    }

    /**
     * Returns an arraylist of songs
     *
     * @return an arraylist of all songs in the library
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * (UNUSED) Returns an arraylist of artists
     *
     * @return an arraylist of all artists in the library
     */
    public ArrayList<Artist> getArtists() {
        return artists;
    }

    /**
     * (UNUSED) Returns an arraylist of albums
     *
     * @return an arraylist of all albums in the library
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * Adds a song to the library.
     *
     * @param s the song object to be added to the library
     */
    public void addSong(Song s) {
        songs.add(s);
    }

    /**
     * (UNUSED) Adds an artist to the library.
     *
     * @param artist the artist object to be added to the library
     */
    public void addArtist(Artist artist) {
        artists.add(artist);
    }

    /**
     * (UNUSED) Adds an album to the library.
     *
     * @param album the album object to be added to the library
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * Returns a playlist object containing the list of songs that have the specified minimum amount of listeners.
     *
     * @return a playlist object
     */
    public Playlist generateRandomPlaylist(){
        Playlist randomPlaylist = new Playlist();

        Scanner input = new Scanner(System.in);
        System.out.println("Generating random playlist based on minimum number of listeners...");
        System.out.println("What is the minimum number of listeners you wish to set as threshold? ");
        System.out.print(">> ");
        int minNum = input.nextInt();

        System.out.println("Generating random playlist with minimum of " + minNum + " listeners...");

        for(Song song : this.songs){
            if(song.getListeners() >= minNum){
                randomPlaylist.addSong(song);
            }
        }

        return randomPlaylist;
    }

    /**
     * Produces an xml formatted string containing all the songs in the library
     *
     * @return a String object containing the XML output
     */
    public String writeToXML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\"  ?><library><songs>");
        for(Song song : this.songs){
            sb.append(song.toXML());
        }
        sb.append("</songs></library>");
        return sb.toString();
    }

    /**
     * (UNUSED) Outputs an XML file of the entire library
     */
    public void outputXML(){
        try {
            FileWriter outputXML = new FileWriter("./generated_playlist.xml");
            outputXML.write(this.writeToXML());
            outputXML.close();
        } catch (IOException e){
            System.out.println("unable to write file");
        }
    }

}
