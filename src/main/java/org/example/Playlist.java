package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Playlist {

    private List<Song> listOfSongs;

    public Playlist(){
        listOfSongs = new ArrayList<Song>();
    }

    public int size(){
        return listOfSongs.size();
    }
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

    public void deleteSong(Song s){
        if(listOfSongs.contains(s)){
            listOfSongs.remove(s);
        } else {
            System.out.println("The song " + s.getName() + " is not in the playList.");
        }
    }

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

    public void mergePlaylist(Playlist otherPlaylist){
        Set<Song> playList1 = new HashSet<>();
        playList1.addAll(this.listOfSongs);
        playList1.addAll(otherPlaylist.listOfSongs);
        this.listOfSongs = playList1.stream().toList();
    }

    public void sortPlaylist(){
        Collections.sort(this.listOfSongs);
    }

    public void shufflePlaylist(){
        Collections.shuffle(this.listOfSongs);
    }

    public void printSongOrderAndLikes(){
        System.out.println("---------------------------");
        System.out.println("Song order in PlayList");
        for(Song song : this.listOfSongs){
            System.out.println("name: " + song.getName() + "; likes: " + song.getLikes());
        }
        System.out.println("---------------------------");
    }

    public String writeToXML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\"  ?><songs>");
        for(Song song : this.listOfSongs){
            sb.append(song.toXML());
        }
        sb.append("</songs>");
        return sb.toString();
    }

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
