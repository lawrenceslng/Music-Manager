package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents an Artist that exists in the library/database.
 */
public class Artist extends Entity {

    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;

    protected int audioDbId;
    protected String nationality;

    /**
     * Class Constructor
     *
     * @param name the name of the Artist to be created
     */
    public Artist(String name) {
        super(name);
    }

    /**
     * (UNUSED) Returns the list of songs that exists in the library that is associated with the artist
     *
     * @return an arraylist of songs associated with the artist
     */
    protected ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * (UNUSED) Sets the list of songs that exists in the library that is associated with the artist
     *
     * @param songs an arraylist of songs associated with the artist
     */
    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * (UNUSED) Returns the list of albums that exists in the library that is associated with the artist
     *
     * @return an arraylist of albums associated with the artist
     */
    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * (UNUSED) Sets the list of albums that exists in the library that is associated with the artist
     *
     * @param albums an arraylist of albums associated with the artist
     */
    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     * (UNUSED) Adds a song to the list of songs associated with the artist
     *
     * @param s the Song object to be associated with the artist
     */
    public void addSong(Song s) {
        songs.add(s);
    }

    /**
     * (UNUSED) Returns the nationality of the artist
     *
     * @return the nationality of the artist
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality of the artist
     *
     * @param nationality a String containing the nationality (country code) of the artist
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Returns the AudioDB ID of the artist
     *
     * @return an Integer representing the AudioDB ID of the artist
     */
    public int getAudioDbId() {
        return audioDbId;
    }

    /**
     * Sets the AudioDB ID of the artist
     *
     * @param audioDbId an Integer representing the AudioDB ID of the artist
     */
    public void setAudioDbId(int audioDbId) {
        this.audioDbId = audioDbId;
    }

    /**
     * Checks whether this Artist is equal to another Artist
     *
     * @param otherArtist an Artist object to be compared to the current Artist
     * @return a boolean value indicating whether the artists are equal or not
     */
    public boolean equals(Artist otherArtist) {
        if (this.name.equals(otherArtist.getName())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a SQL query that would insert this Artist into the database
     *
     * @return a String representing the SQL query that would insert this Artist into the database
     */
    public String toSQL() {
        return "INSERT INTO artists (id, name, audioDBId, country) VALUES (" + this.audioDbId + ", \"" + this.name + "\", " + this.audioDbId + ", \"" + this.nationality + "\");";
    }

    /**
     * (UNUSED) Create an Artist object based on returned information from querying the database
     *
     * @param rs a ResultSet object after making executing a SQL query against the database
     */
    public void fromSQL(ResultSet rs) {
        try {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
        } catch(SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }
    }
}
