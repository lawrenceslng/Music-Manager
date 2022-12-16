package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * This class connects to audioDB API:
 * https://www.theaudiodb.com/api_guide.php
 *
 * This hits up various endpoints in order to retrieve song, artist, and album information.
 */
public class AudioDBConnector {

    private static String baseURL = "https://www.theaudiodb.com/api/v1/json/523532/";

    /**
     * Returns the complete URL given a specific query.
     *
     * @param query the string query to be appended to the base URL of audioDB's API.
     * @return a URL object representing one of audioDB's API endpoint
     */
    private static URL formURL(String query){
        URL u;
        try {
            u = new URL(baseURL + query);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
            return null;
        }
        return u;
    }

    /**
     * Finds by name whether a user-provided artist exists in AudioDB's database.
     *
     * @param artistName a string containing the user-provided artist name
     * @return an Artist object of the artist the user is looking for
     */
    public static Artist artistSearch(String artistName) {
        String query = "search.php?s=" + artistName;
        URL url = formURL(query);
        StringBuilder response = new StringBuilder();

        if(url != null){
            response = connectToAudioDB(url);
        }

        Artist newArtist = new Artist("");
        if(response != null){
            parseArtistSearchResponse(newArtist, response);
        }

        return newArtist;
    }

    /**
     * Finds by AudioDB ID whether an artist exists in AudioDB's database
     *
     * @param audioDBId an integer representing the ID of an artist in AudioDB
     * @return an Artist object of the artist that has the specific audioDB ID
     */
    public static Artist artistIdSearch(int audioDBId) {
        String query = "artist.php?i=" + audioDBId;
        URL url = formURL(query);
        StringBuilder response = new StringBuilder();

        if(url != null){
            response = connectToAudioDB(url);
        }

        Artist newArtist = new Artist("");
        if(response != null){
            parseArtistSearchResponse(newArtist, response);
        }

        return newArtist;
    }

    /**
     * Finds by artist name and album name whether an album exists in AudioDB's database
     *
     * @param artistName a string containing the user-provided artist name
     * @param albumName a string containing the user-provided album name
     * @return an Album object representing the album the user is looking for
     */
    public static Album albumSearch(String artistName, String albumName) {
        String query = "searchalbum.php?s=" + artistName + "&a=" + albumName;
        URL url = formURL(query);
        StringBuilder response = new StringBuilder();

        if(url != null){
            response = connectToAudioDB(url);
        }

        Album newAlbum = new Album("");
        if(response != null){
            parseAlbumSearchResponse(newAlbum, response);
        }

        return newAlbum;
    }

    /**
     * Finds by AudioDB ID whether an album exists in AudioDB's database
     *
     * @param audioDBId an integer representing the ID of an album in AudioDB
     * @return an Album object of the artist that has the specific audioDB ID
     */
    public static Album albumIdSearch(int audioDBId) {
        String query = "album.php?m=" + audioDBId;
        URL url = formURL(query);
        StringBuilder response = new StringBuilder();

        if(url != null){
            response = connectToAudioDB(url);
        }

        Album newAlbum = new Album("");
        if(response != null){
            parseAlbumSearchResponse(newAlbum, response);
        }

        return newAlbum;
    }

    /**
     * Finds by artist name and song name whether a song exists in AudioDB's database
     *
     * @param artistName a string containing the user-provided artist name
     * @param songName a string containing the user-provided song name
     * @return a Song object of the song that the user is looking for
     */
    public static Song songSearch(String artistName, String songName) {
        String query = "searchtrack.php?s=" + artistName +"&t=" + songName;
        URL url = formURL(query);
        StringBuilder response = new StringBuilder();

        if(url != null){
            response = connectToAudioDB(url);
        }

        Song newSong = new Song();
        if(response != null){
            parseSongSearchResponse(newSong, response);
        }
        return newSong;
    }

    /**
     * Performs an HTTP request to AudioDB and returns the response
     *
     * @param url a URL object specifying the endpoint to make the HTTP request to
     * @return a StringBuilder object containing the API response
     */
    private static StringBuilder connectToAudioDB(URL url){
        StringBuilder response = new StringBuilder();
        try {
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int code = httpConnection.getResponseCode();

            String message = httpConnection.getResponseMessage();
            if (code != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream instream = connection.getInputStream();
            Scanner in = new Scanner(instream);
            while (in.hasNextLine()) {
                response.append(in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading response");
            return null;
        }
        return response;
    }

    /**
     * Parses through the response to an artist search and fills in the Artist object
     *
     * @param artist an Artist object to be filled in with information from the API response
     * @param response a StringBuilder object of the response from the API
     */
    private static void parseArtistSearchResponse(Artist artist, StringBuilder response){
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray artists = (JSONArray) jsonObject.get("artists");
            if(artists == null){
                System.out.print("Artist not found online...");
                return;
            }

            JSONObject artistArray = (JSONObject) artists.get(0);

            int audioDBId = Integer.parseInt((String) artistArray.get("idArtist"));
            String country = (String) artistArray.get("strCountryCode");
            String artistName = (String) artistArray.get("strArtist");

            artist.setAudioDbId(audioDBId);
            artist.setNationality(country);
            artist.setName(artistName);
        } catch (ParseException e) {
            System.out.println("Error parsing JSON");
            return;
        }
    }

    /**
     * Parses through the response to an album search and fills in the Album object
     *
     * @param album an Album object to be filled in with information from the API response
     * @param response a StringBuilder object of the response from the API
     */
    private static void parseAlbumSearchResponse(Album album, StringBuilder response){
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray albums = (JSONArray) jsonObject.get("album");
            if(albums == null){
                System.out.print("Album not found online...");
                return;
            }

            JSONObject albumArray = (JSONObject) albums.get(0);

            int audioDBId = Integer.parseInt((String) albumArray.get("idAlbum"));
            int idArtist = Integer.parseInt((String) albumArray.get("idArtist"));
            String albumName = (String) albumArray.get("strAlbum");

            Artist artistExists = DBConnections.findArtist(idArtist);
            if(artistExists != null){
                album.setArtist(artistExists);
            } else {
                // create new artist + insert artist into db
                Artist newArtist = artistIdSearch(idArtist);
                DBConnections.addNewArtist(newArtist);
                album.setArtist(newArtist);
            }

            album.setAudioDbId(audioDBId);
            album.setName(albumName);
        } catch (ParseException e) {
            System.out.println("Error parsing JSON");
            return;
        }
    }

    /**
     * Parses through the response to a song search and fills in the Song object
     *
     * @param song a Song object to be filled in with information from the API response
     * @param response a StringBuilder object of the response from the API
     */
    private static void parseSongSearchResponse(Song song, StringBuilder response){
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray track = (JSONArray) jsonObject.get("track");
            if(track == null){
                System.out.print("Song not found online...");
                return;
            }

            JSONObject trackArray = (JSONObject) track.get(0);

            String songName = (String) trackArray.get("strTrack");
            int audioDBId = Integer.parseInt((String) trackArray.get("idTrack"));
            int idAlbum = Integer.parseInt((String) trackArray.get("idAlbum"));
            int idArtist = Integer.parseInt((String) trackArray.get("idArtist"));
            int numListeners;
            if(trackArray.get("intTotalListeners") != null){
                numListeners = Integer.parseInt((String) trackArray.get("intTotalListeners"));
            } else {
                numListeners = 0;
            }

            // check if artist or album already exists in db
            Artist artistExists = DBConnections.findArtist(idArtist);
            Album albumExists = DBConnections.findAlbum(idAlbum);
//            boolean aExists = artistExists != null;
//            boolean alExists = albumExists != null;

            song.setName(songName);
            if(artistExists != null){
                song.setPerformer(artistExists);
            } else {
                Artist newArtist = artistIdSearch(idArtist);
                DBConnections.addNewArtist(newArtist);
                song.setPerformer(newArtist);
            }

            if(albumExists != null){
                song.setAlbum(albumExists);
            } else {
                Album newAlbum = albumIdSearch(idAlbum);
                DBConnections.addNewAlbum(newAlbum);
                song.setAlbum(newAlbum);
            }

            song.setAudioDBId(audioDBId);
            song.setName(songName);
            song.setListeners(numListeners);
        } catch (ParseException e) {
            System.out.println("Error parsing JSON");
            return;
        }
    }
}
