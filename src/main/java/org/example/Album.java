package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;

    protected int audioDbId;

    public Album(String name) {
        super(name);
    }

    public String getName() {
        return name;
    }

    public boolean equals(Album otherAlbum) {
        if ((this.artist.equals(otherAlbum.getArtist())) &&
                (this.name.equals(otherAlbum.getName()))) {
            return true;
        } else {
            return false;
        }
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getAudioDbId() {
        return audioDbId;
    }

    public void setAudioDbId(int audioDbId) {
        this.audioDbId = audioDbId;
    }

    public String toSQL() {
        return "insert into albums (id, name, audioDBId, audioDBArtistId) values (" + this.audioDbId + ", \"" + this.name + "\", " + this.audioDbId + ", " + this.artist.audioDbId + ");";
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
