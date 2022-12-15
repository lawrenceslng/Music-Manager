package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Song extends Entity implements Comparable<Song>{
    protected Album album;
    protected Artist performer;
    protected SongInterval duration;
    protected String genre;

    protected int likes;

    protected int audioDBId;

    public Song() { super(); }
    public Song(String name) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(0);
        genre = "";
        likes = 0;

    }
    public Song(String name, int length) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(length);
        genre = "";
        likes = 0;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        duration = new SongInterval(length);
   }

   public String showLength() {
        return duration.toString();
   }


    protected Album getAlbum() {
        return album;
    }

    protected void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getPerformer() {
        return performer;
    }

    public void setPerformer(Artist performer) {
        this.performer = performer;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getAudioDBId() {
        return audioDBId;
    }

    public void setAudioDBId(int audioDBId) {
        this.audioDBId = audioDBId;
    }

    public String toString() {
        return super.toString() + " " + this.performer + " " + this.album + " " + this.duration;

    }

    public boolean equals(Song other) {
        return (this.performer.equals(other.performer) && this.album.equals(other.album) && this.name.equals(other.name));
    }

    public String toXML(){
        return "<song id=\"" + this.entityID + "\"><title>" + this.name + "</title><artist id=\"" + this.performer.entityID + "\">" + this.performer.getName() + "</artist><album id=\"" + this.album.entityID +">"+ this.album.getName() + "</album><length>" + this.duration + "</length><likes>" + this.likes + "</likes></song>";
    }
    public String toSQL() {
        return "insert into songs (id, name, audioDBArtistId, audioDBAlbumId) values (" + this.audioDBId + ", \"" + this.name + "\", "
                + this.performer.audioDbId  + ", " + this.album.audioDbId + ");";
    }

    public void fromSQL(ResultSet rs) {
        try {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
        } catch(SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }
    }

    @Override
    public int compareTo(Song o) {
        if(this.likes > o.likes){
            return -1;
        } else if(this.likes < o.likes){
            return 1;
        } else {
            return 0;
        }
    }
}