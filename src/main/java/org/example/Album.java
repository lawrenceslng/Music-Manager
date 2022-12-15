package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents an Album that exists in the library/database.
 */
public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;

    protected int audioDbId;

    /**
     * Class Constructor
     *
     * @param name the name of the Album to be created
     */
    public Album(String name) {
        super(name);
    }

    /**
     * Returns the name of the Album
     *
     * @return a String containing the name of this album
     */
    public String getName() {
        return name;
    }

    /**
     * Checks whether this Album is equal to another Album
     *
     * @param otherAlbum an Album object to be compared to the current Album
     * @return a boolean value indicating whether the albums are equal or not
     */
    public boolean equals(Album otherAlbum) {
        if ((this.artist.equals(otherAlbum.getArtist())) &&
                (this.name.equals(otherAlbum.getName()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * (UNUSED) Returns the list of songs that exists in the library that is associated with this album
     *
     * @return an arraylist of songs associated with the album
     */
    protected ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * (UNUSED) Sets the list of songs that exists in the library that is associated with this album
     *
     * @param songs an arraylist of songs to be associated with the album
     */
    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * Returns the Artist that exists in the library that is associated with this album
     *
     * @return an Artist object of the artist associated with the album
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Sets the Artist that exists in the library that is associated with this album
     *
     * @param artist an Artist object of the artist to be associated with the album
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Returns the AudioDB ID of the album
     *
     * @return an Integer representing the AudioDB ID of the album
     */
    public int getAudioDbId() {
        return audioDbId;
    }

    /**
     * Sets the AudioDB ID of the album
     *
     * @param audioDbId an Integer representing the AudioDB ID of the album
     */
    public void setAudioDbId(int audioDbId) {
        this.audioDbId = audioDbId;
    }

    /**
     * Creates a SQL query that would insert this Album into the database
     *
     * @return a String representing the SQL query that would insert this Album into the database
     */
    public String toSQL() {
        return "insert into albums (id, name, audioDBId, audioDBArtistId) values (" + this.audioDbId + ", \"" + this.name + "\", " + this.audioDbId + ", " + this.artist.audioDbId + ");";
    }

    /**
     * (UNUSED) Create an Album object based on returned information from querying the database
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
