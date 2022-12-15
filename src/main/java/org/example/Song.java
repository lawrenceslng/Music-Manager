package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Song extends Entity implements Comparable<Song>{
    protected Album album;
    protected Artist performer;
    protected SongInterval duration;
    protected String genre;

    protected int numListeners;

    protected int audioDBId;

    public Song() { super(); }
    public Song(String name) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(0);
        genre = "";
        numListeners = 0;

    }
    public Song(String name, int length) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(length);
        genre = "";
        numListeners = 0;
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

    public int getListeners() {
        return numListeners;
    }

    public void setListeners(int nListeners) {
        this.numListeners = nListeners;
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
        return "<song id=\"" + this.audioDBId + "\"><title>" + this.name + "</title><artist id=\"" + this.performer.audioDbId + "\">" + this.performer.getName() + "</artist><album id=\"" + this.album.audioDbId +">"+ this.album.getName() + "</album><numOfListeners>" + this.numListeners + "</numOfListeners></song>";
    }
    public String toSQL() {
        return "insert into songs (id, audioDBId, name, audioDBArtistId, audioDBAlbumId, nlisteners) values (" + this.audioDBId + ", " + this.audioDBId + ", \"" + this.name + "\", "
                + this.performer.audioDbId  + ", " + this.album.audioDbId + ", " + this.numListeners + ");";
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
        if(this.numListeners > o.numListeners){
            return -1;
        } else if(this.numListeners < o.numListeners){
            return 1;
        } else {
            return 0;
        }
    }
}