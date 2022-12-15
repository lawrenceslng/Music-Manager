package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DBConnections {

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
    private static void closeConnection(Connection connection){
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }

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
