package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Artist extends Entity {

    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;

    protected int audioDbId;
    protected String nationality;

    public Artist(String name) {
        super(name);
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAudioDbId() {
        return audioDbId;
    }

    public void setAudioDbId(int audioDbId) {
        this.audioDbId = audioDbId;
    }

    public boolean equals(Artist otherArtist) {
        if (this.name.equals(otherArtist.getName())) {
            return true;
        } else {
            return false;
        }
    }
    public String toSQL() {
        return "INSERT INTO artists (id, name, audioDBId, country) VALUES (" + this.audioDbId + ", \"" + this.name + "\", " + this.audioDbId + ", \"" + this.nationality + "\");";
    }

    public void fromSQL(ResultSet rs) {
        try {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
        } catch(SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }
    }
}
