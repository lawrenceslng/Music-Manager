package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Song> songs;
    private ArrayList<Artist> artists;
    private ArrayList<Album> albums;

    public Library() {
        songs = new ArrayList<Song>();
        artists = new ArrayList<Artist>();
        albums = new ArrayList<Album>();
    }
    public boolean findSong(Song s) {
        return songs.contains(s);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
    public ArrayList<Artist> getArtists() {
        return artists;
    }
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void addSong(Song s) {
        songs.add(s);
    }
    public void addArtist(Artist artist) {
        artists.add(artist);
    }
    public void addAlbum(Album album) {
        albums.add(album);
    }

    public Playlist generateRandomPlaylist(){
        Playlist randomPlaylist = new Playlist();

        Scanner input = new Scanner(System.in);
        System.out.println("Generating random playlist based on minimum number of likes...");
        System.out.println("What is the minimum number of likes you wish to set as threshold? ");
        int minNum = input.nextInt();

        System.out.println("Generating random playlist with minimum of " + minNum + " likes.");

        for(Song song : this.songs){
            if(song.getLikes() >= minNum){
                randomPlaylist.addSong(song);
            }
        }

        return randomPlaylist;
    }

    public String writeToXML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\"  ?><library><songs>");
        for(Song song : this.songs){
            sb.append(song.toXML());
        }
        sb.append("</songs></library>");
        return sb.toString();
    }

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
