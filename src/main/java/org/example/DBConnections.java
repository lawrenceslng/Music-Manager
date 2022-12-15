package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DBConnections {

    public static void populateLibrary(Library library){
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("SELECT songs.name, songs.likes, artists.name, albums.name FROM songs INNER JOIN artists ON songs.artist == artists.id INNER JOIN albums ON songs.album == albums.id;");

            while (rs.next()) {
                Song newSong = new Song();
                newSong.setName(rs.getString(1));

                Artist newArtist = new Artist(rs.getString(3));
                newSong.setPerformer(newArtist);

                Album newAlbum = new Album(rs.getString(4));
                newAlbum.setArtist(newArtist);
                newSong.setAlbum(newAlbum);

                newSong.setLikes(rs.getInt(2));
                library.addSong(newSong);
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public static void getComprehensive(){

    }
    public static void getAllSongs(Library library){
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("SELECT songs.name, songs.likes, artists.name, albums.name FROM songs INNER JOIN artists ON songs.artist == artists.id INNER JOIN albums ON songs.album == albums.id;");
//            int count = rs.getMetaData().getColumnCount();
//            for(int i = 1; i <= count; i++){
//                System.out.println(rs.getMetaData().getColumnName(i));
//            }

            while (rs.next()) {
                // read the result set
//                    rs.getString(1);

//                System.out.println("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
                Song newSong = new Song();
                newSong.setName(rs.getString(1));

                Artist newArtist = new Artist(rs.getString(3));
                newSong.setPerformer(newArtist);

                Album newAlbum = new Album(rs.getString(4));
                newAlbum.setArtist(newArtist);
                newSong.setAlbum(newAlbum);

                newSong.setLikes(rs.getInt(2));
                library.addSong(newSong);
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public static ArrayList<String> getAllArtists(){
        ArrayList<String> artistsList = new ArrayList<String>();
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("SELECT name FROM artists");
            while (rs.next()) {
                // read the result set
//                System.out.println("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
                artistsList.add(rs.getString("name"));

            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return artistsList;
    }

    public static ArrayList<String> getAllAlbums(){
        Connection connection = null;
        ArrayList<String> albumList = new ArrayList<String>();
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("SELECT name FROM albums");
            while (rs.next()) {
                // read the result set
//                System.out.println("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
                albumList.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return albumList;
    }

    public static boolean addNewSong(Song song){
        String query = song.toSQL();
        System.out.println(query);
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(query);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public static boolean addNewArtist(Artist artist){
        String query = artist.toSQL();
        System.out.println(query);
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(query);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public static boolean addNewAlbum(Album album){
        String query = album.toSQL();
        System.out.println(query);
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(query);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public static Artist findArtist(int audioDBID){
        String query = "SELECT * FROM artists WHERE id == " + audioDBID;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query);
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
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public static Album findAlbum(int audioDBID){
        String query = "SELECT * FROM albums WHERE id == " + audioDBID;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                Album newAlbum = new Album("");
                newAlbum.setAudioDbId(rs.getInt("audioDBId"));
                newAlbum.setName(rs.getString("name"));
//                newAlbum.setArtist(rs.getString("country"));
                return newAlbum;
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public static Song findSong(int audioDBID){
        String query = "SELECT * FROM songs WHERE id == " + audioDBID;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music-library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query);
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
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
