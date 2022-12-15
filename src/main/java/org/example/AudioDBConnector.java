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

public class AudioDBConnector {

    private static String baseURL = "https://www.theaudiodb.com/api/v1/json/523532/";

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

    private static StringBuilder connectToAudioDB(URL url){
        StringBuilder response = new StringBuilder();
        try {
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int code = httpConnection.getResponseCode();

            String message = httpConnection.getResponseMessage();
//            System.out.println(code + " " + message);
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
            int numListeners = Integer.parseInt((String) trackArray.get("intTotalListeners"));

            // check if artist or album already exists in db
            Artist artistExists = DBConnections.findArtist(idArtist);
            Album albumExists = DBConnections.findAlbum(idAlbum);
            boolean aExists = artistExists != null;
            boolean alExists = albumExists != null;

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
