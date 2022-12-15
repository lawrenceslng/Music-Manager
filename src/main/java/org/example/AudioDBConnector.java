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

    public static void artistSearch(String artistName) {
        String requestURL = "https://www.theaudiodb.com/api/v1/json/523532/search.php?s=";
        String artist = artistName;
        StringBuilder response = new StringBuilder();
        URL u;
        try {
            u = new URL(requestURL + artist);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
            return;
        }
        try {
            URLConnection connection = u.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int code = httpConnection.getResponseCode();

            String message = httpConnection.getResponseMessage();
            System.out.println(code + " " + message);
            if (code != HttpURLConnection.HTTP_OK) {
                return;
            }
            InputStream instream = connection.getInputStream();
            Scanner in = new Scanner(instream);
            while (in.hasNextLine()) {
                response.append(in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading response");
            return;
        }
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray artists = (JSONArray) jsonObject.get("artists"); // get the list of all artists returned.
            JSONObject beatles = (JSONObject) artists.get(0);  // I happen to know that the beatles is the first entry. Otherwise I'd iterate.
            String mood = (String) beatles.get("mood");
            System.out.println("Mood: " + mood);
            String bio = (String) beatles.get("strBiographyEN");
            System.out.println("Biography: " + bio);

        } catch (ParseException e) {
            System.out.println("Error parsing JSON");
            return;
        }
    }
}
