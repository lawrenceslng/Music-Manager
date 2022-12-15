package org.example;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class connects to the SQLite DB that contains all the songs, artists, and albums information.
 *
 */
public class DBConnections {

    /**
     * Starts a connection to the SQLite DB
     *
     * @return a Connection object establishing a connection to the SQLite DB
     */
    private static Connection startConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        return connection;
    }

    /**
     * Creates a Statement object to be used for executing SQL queries
     *
     * @param connection a Connection object containing the opened connection to the SQLite DB
     * @return a Statement object ready to be used for executing SQL queries
     */
    private static Statement statementCreation(Connection connection){
        Statement statement = null;
        try{
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        return statement;
    }

    /**
     * Executes a SQL query and returns the result
     *
     * @param statement the Statement object to be used for executing SQL queries
     * @param query a String representing the SQL query to be executed
     * @return a ResultSet object containing the response
     */
    private static ResultSet queryExecution(Statement statement, String query){
        ResultSet rs = null;
        try{
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
        return rs;
    }

    /**
     * Executes a SQL query that updates the database
     *
     * @param statement the Statement object to be used for executing SQL queries
     * @param query a String representing the SQL query to be executed
     * @return a boolean value indicating whether the query was successfully executed
     */
    private static boolean queryUpdate(Statement statement, String query){
        try{
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Closes connection to the SQLite DB
     *
     * @param connection a Connection object with a connection to the SQLite DB that needs to be closed
     */
    private static void closeConnection(Connection connection){
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves basic information from the SQLite DB and populates a Library object
     *
     * @param library the library to be instantiated/loaded with information
     */
    public static void populateLibrary(Library library){
        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        String query = "SELECT songs.name, songs.audioDBId, songs.nlisteners, artists.name, artists.audioDBId, albums.name, albums.audioDBId FROM songs INNER JOIN artists ON songs.audioDBArtistId == artists.id INNER JOIN albums ON songs.audioDBAlbumId == albums.id;";
        ResultSet rs = queryExecution(statement,query);
        try {
            while (rs.next()) {
                Song newSong = new Song();
                newSong.setName(rs.getString(1));

                Artist newArtist = new Artist(rs.getString(4));
                newArtist.setAudioDbId(Integer.parseInt(rs.getString(5)));
                newSong.setPerformer(newArtist);

                Album newAlbum = new Album(rs.getString(6));
                newAlbum.setArtist(newArtist);
                newAlbum.setAudioDbId(Integer.parseInt(rs.getString(7)));
                newSong.setAlbum(newAlbum);

                newSong.setListeners(rs.getInt(3));

                newSong.setAudioDBId(Integer.parseInt(rs.getString(2)));
                library.addSong(newSong);
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        } finally {
           closeConnection(connection);
        }
    }

    /**
     * Retrieves all the artists that exist within the SQLite DB
     *
     * @return an Arraylist of String containing all artist names in the DB
     */
    public static ArrayList<String> getAllArtists(){
        ArrayList<String> artistsList = new ArrayList<String>();

        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        String query = "SELECT name FROM artists";
        ResultSet rs = queryExecution(statement,query);

        try{
            while (rs.next()) {
                artistsList.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }

        return artistsList;
    }

    /**
     * Retrieves all the albums that exist within the SQLite DB
     *
     * @return an Arraylist of String containing all album names in the DB
     */
    public static ArrayList<String> getAllAlbums(){
        ArrayList<String> albumList = new ArrayList<String>();

        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        String query = "SELECT name FROM albums";
        ResultSet rs = queryExecution(statement,query);

        try{
            while (rs.next()) {
                albumList.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }

        return albumList;
    }

    /**
     * Adds a Song object to the SQLite DB
     *
     * @param song the Song object to be added to the DB
     * @return a boolean value indicating whether the INSERT operation is successful or not
     */
    public static boolean addNewSong(Song song){
        String query = song.toSQL();

        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        boolean updateSuccess = queryUpdate(statement,query);
        closeConnection(connection);

        if(updateSuccess){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds an Artist object to the SQLite DB
     *
     * @param artist the Artist object to be added to the DB
     * @return a boolean value indicating whether the INSERT operation is successful or not
     */
    public static boolean addNewArtist(Artist artist){
        String query = artist.toSQL();

        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        boolean updateSuccess = queryUpdate(statement,query);
        closeConnection(connection);

        if(updateSuccess){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds an Album object to the SQLite DB
     *
     * @param album the Album object to be added to the DB
     * @return a boolean value indicating whether the INSERT operation is successful or not
     */
    public static boolean addNewAlbum(Album album){
        String query = album.toSQL();

        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        boolean updateSuccess = queryUpdate(statement,query);
        closeConnection(connection);

        if(updateSuccess){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Finds whether an artist exists in the SQLite DB based on AudioDB ID
     *
     * @param audioDBID an Integer representing the AudioDB ID of the artist
     * @return an Artist object containing the requested artist
     */
    public static Artist findArtist(int audioDBID){
        String query = "SELECT * FROM artists WHERE id == " + audioDBID;

        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        ResultSet rs = queryExecution(statement,query);

        try{
            if(rs.next()){
                Artist newArtist = new Artist("");
                newArtist.setAudioDbId(rs.getInt("audioDBId"));
                newArtist.setName(rs.getString("name"));
                newArtist.setNationality(rs.getString("country"));
                return newArtist;
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }

        return null;
    }


    /**
     * Finds whether an album exists in the SQLite DB based on AudioDB ID
     *
     * @param audioDBID an Integer representing the AudioDB ID of the album
     * @return an Album object containing the requested artist
     */
    public static Album findAlbum(int audioDBID){
        String query = "SELECT * FROM albums WHERE id == " + audioDBID;

        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        ResultSet rs = queryExecution(statement,query);

        try{
            if(rs.next()){
                Album newAlbum = new Album("");
                newAlbum.setAudioDbId(rs.getInt("audioDBId"));
                newAlbum.setName(rs.getString("name"));
                return newAlbum;
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }

        return null;
    }

    /**
     * Finds whether a song exists in the SQLite DB based on AudioDB ID
     *
     * @param audioDBID an Integer representing the AudioDB ID of the song
     * @return a Song object containing the requested artist
     */
    public static Song findSong(int audioDBID){
        String query = "SELECT * FROM songs WHERE id == " + audioDBID;

        Connection connection = startConnection();
        Statement statement = statementCreation(connection);
        ResultSet rs = queryExecution(statement,query);

        try{
            if(rs.next()){
                Song newSong = new Song();
                newSong.setAudioDBId(rs.getInt("audioDBId"));
                newSong.setName(rs.getString("name"));
                return newSong;
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }

        return null;
    }
}
