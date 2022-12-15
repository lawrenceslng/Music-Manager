package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class represents a Playlist that can be created from the library. A Playlist is a list of Songs.
 */
public class Playlist {

    private List<Song> listOfSongs;

    /**
     * Class Constructor
     */
    public Playlist(){
        listOfSongs = new ArrayList<Song>();
    }

    /**
     * (UNUSED) Returns the size of the playlist
     *
     * @return an Integer representing the number of songs in the playlist
     */
    public int size(){
        return listOfSongs.size();
    }

    /**
     * Adds a song to the playlist
     *
     * @param s a Song object to be added to the playlist
     */
    public void addSong(Song s) {
        boolean toAdd = true;
        if(this.listOfSongs.contains(s)){
            System.out.println("Song already in playlist, not adding duplicate...");
            toAdd = false;
        }
        if(toAdd){
            listOfSongs.add(s);
        }
    }

    /**
     * (UNUSED) Deletes a song from the playlist
     *
     * @param s a Song object representing the song to be deleted from the playlist
     */
    public void deleteSong(Song s){
        if(listOfSongs.contains(s)){
            listOfSongs.remove(s);
        } else {
            System.out.println("The song " + s.getName() + " is not in the playList.");
        }
    }

    /**
     * (UNUSED Deletes a song from the playlist given just the song name
     *
     * @param songName a String representing the name of the song to be deleted from the playlist
     */
    public void deleteSong(String songName){
        Scanner input = new Scanner(System.in);
        for(Song song : listOfSongs){
            // maybe refine condition
            if(song.getName().equalsIgnoreCase(songName)){
                System.out.println("Song exists, delete? (Y/N) ");
                String ans = input.next();
                if(ans.equals("Y")){
                    System.out.println("Deleting song: " + songName);
                    listOfSongs.remove(song);
                    break;
                }
            }
        }
    }

    /**
     * (UNUSED) Merges two playlists into 1
     *
     * @param otherPlaylist a Playlist object representing the playlist to be merged with this playlist
     */
    public void mergePlaylist(Playlist otherPlaylist){
        Set<Song> playList1 = new HashSet<>();
        playList1.addAll(this.listOfSongs);
        playList1.addAll(otherPlaylist.listOfSongs);
        this.listOfSongs = playList1.stream().toList();
    }

    /**
     * (UNUSED) Sorts the playlist based on a song's compareTo method
     */
    public void sortPlaylist(){
        Collections.sort(this.listOfSongs);
    }

    /**
     * (UNUSED) Randomly shuffles the content of the playlist
     */
    public void shufflePlaylist(){
        Collections.shuffle(this.listOfSongs);
    }

    /**
     * (UNUSED) Prints the songs in order along with their respective numbers of listeners
     */
    public void printSongOrderAndNListeners(){
        System.out.println("---------------------------");
        System.out.println("Song order in PlayList");
        for(Song song : this.listOfSongs){
            System.out.println("name: " + song.getName() + "; number of Listeners: " + song.getListeners());
        }
        System.out.println("---------------------------");
    }

    /**
     * Produces an xml formatted string containing all the songs in the playlist
     *
     * @return a String object containing the XML output
     */
    public String writeToXML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\"  ?><songs>");
        for(Song song : this.listOfSongs){
            sb.append(song.toXML());
        }
        sb.append("</songs>");
        return sb.toString();
    }

    /**
     * Outputs an XML file of the playlist
     */
    public void outputXML(){
        try {
            FileWriter outputXML = new FileWriter("./playlist_outputXML.xml");
            outputXML.write(this.writeToXML());
            outputXML.close();
        } catch (IOException e){
            System.out.println("unable to write file");
        }
    }
}
