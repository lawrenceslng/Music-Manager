package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a Song that exists in the library/database.
 */
public class Song extends Entity implements Comparable<Song>{
    protected Album album;
    protected Artist performer;
    protected SongInterval duration;
    protected String genre;

    protected int numListeners;

    protected int audioDBId;

    /**
     * Class Constructor
     */
    public Song() { super(); }

    /**
     * (UNUSED) Class Constructor with overloading for song name
     *
     * @param name a String representing the name of the song
     */
    public Song(String name) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(0);
        genre = "";
        numListeners = 0;

    }

    /**
     * (UNUSED) Class Constructor with overloading for song name and song length
     *
     * @param name a String representing the name of the song
     * @param length an Integer representing the length of the song
     */
    public Song(String name, int length) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(length);
        genre = "";
        numListeners = 0;
    }

    /**
     * (UNUSED) Returns the genre of the song
     *
     * @return a String representing the genre of the song
     */
    public String getGenre() {
        return genre;
    }

    /**
     * (UNUSED) Sets the genre of the song
     *
     * @param genre a String representing the genre of the song
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * (UNUSED) Sets the length of the song
     *
     * @param length an Integer representing the length of the song
     */
    public void setLength(int length) {
        duration = new SongInterval(length);
   }

    /**
     * (UNUSED) Returns the length of the song
     *
     * @return a String value indicating the length of the song
     */
   public String showLength() {
        return duration.toString();
   }

    /**
     * Returns the album associated with the song
     *
     * @return an Album object associated with the song
     */
    protected Album getAlbum() {
        return album;
    }

    /**
     * Sets the album associated with the song
     *
     * @param album an Album object to be associated with the song
     */
    protected void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Returns the artist associated with the song
     *
     * @return an Artist object associated with the song
     */
    public Artist getPerformer() {
        return performer;
    }

    /**
     * Sets the artist associated with the song
     *
     * @param performer an Artist object to be associated with the song
     */
    public void setPerformer(Artist performer) {
        this.performer = performer;
    }

    /**
     * Returns the number of listeners of the song (based on AudioDB response)
     *
     * @return an Integer representing the number of listeners of the song
     */
    public int getListeners() {
        return numListeners;
    }

    /**
     * Sets the number of listeners of the song
     *
     * @param nListeners an Integer representing the number of listeners of the song
     */
    public void setListeners(int nListeners) {
        this.numListeners = nListeners;
    }

    /**
     * Returns the AudioDB ID of the song
     *
     * @return an Integer representing the AudioDB ID of the song
     */
    public int getAudioDBId() {
        return audioDBId;
    }

    /**
     * Sets the AudioDB ID of the song
     *
     * @param audioDBId an Integer representing the AudioDB ID of the song
     */
    public void setAudioDBId(int audioDBId) {
        this.audioDBId = audioDBId;
    }

    /**
     * Returns a String providing information about this song
     *
     * @return a String containing the song, artist, and album information of the song
     */
    public String toString() {
        return super.toString() + " " + this.performer + " " + this.album + " " + this.duration;

    }

    /**
     * (UNUSED) Checks whether this song is equal to another song
     *
     * @param other the Song object to be compared against this song
     * @return a boolean value indicating whether the songs are equal or not
     */
    public boolean equals(Song other) {
        return (this.performer.equals(other.performer) && this.album.equals(other.album) && this.name.equals(other.name));
    }

    /**
     * Returns an xml formatted string containing this song's information
     *
     * @return a String object containing the XML output
     */
    public String toXML(){
        return "<song id=\"" + this.audioDBId + "\"><title>" + this.name + "</title><artist id=\"" + this.performer.audioDbId + "\">" + this.performer.getName() + "</artist><album id=\"" + this.album.audioDbId +">"+ this.album.getName() + "</album><numOfListeners>" + this.numListeners + "</numOfListeners></song>";
    }

    /**
     * Creates a SQL query that would insert this Song into the database
     *
     * @return a String representing the SQL query that would insert this Song into the database
     */
    public String toSQL() {
        return "insert into songs (id, audioDBId, name, audioDBArtistId, audioDBAlbumId, nlisteners) values (" + this.audioDBId + ", " + this.audioDBId + ", \"" + this.name + "\", "
                + this.performer.audioDbId  + ", " + this.album.audioDbId + ", " + this.numListeners + ");";
    }

    /**
     * (UNUSED) Create a Song object based on returned information from querying the database
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

    /**
     * Compares this song to another song based on number of listeners
     *
     * @param o the object to be compared.
     * @return -1 if this song has more listeners, 1 if other song has more listeners, 0 if equal number of listeners
     */
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