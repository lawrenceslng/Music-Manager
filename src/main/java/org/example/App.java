package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    static Scanner input = new Scanner(System.in);
    static int userInput = 0;
    static boolean invalidInput = true;

    static Library library;
    private static int mainMenu(){
        userInput = 0;
        invalidInput = true;
        do {
        System.out.println( "Please select from the following options:" );

        System.out.println( "Type '1' to display songs, artists, or albums in the library" );
        System.out.println( "Type '2' to add music entry to your library" );
        System.out.println( "Type '3' to generate a random playlist based on ..." );

        System.out.println( "Type '0' to exit" );

        System.out.println( "--------------------------------" );

        try {
                System.out.print(">> ");
                userInput = input.nextInt();
                if(userInput == 1 || userInput == 2 || userInput == 3 || userInput == 0) {
                    invalidInput = false;
                } else {
                    System.out.println("Invalid input, please try again");
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid Input, please enter only '1', '2', '3', or '0'.");

            }
            input.nextLine();
            System.out.println( "--------------------------------" );
        } while(invalidInput);

        return userInput;
    }

    private static int displayMenu(){
        userInput = 0;
        invalidInput = true;

        do {
            System.out.println("Please select from the following options:");

            System.out.println("Type '1' to display songs in the library");
            System.out.println("Type '2' to display artists in the library");
            System.out.println("Type '3' to display albums in the library");
            System.out.println("Type '4' to display songs+artists+albums in the library");

            System.out.println("Type '0' to go back");

            System.out.println("--------------------------------");

            try {
                System.out.print(">> ");
                userInput = input.nextInt();
                if(userInput == 1 || userInput == 2 || userInput == 3 || userInput == 4 || userInput == 0) {
                    invalidInput = false;
                } else {
                    System.out.println("Invalid input, please try again");
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid Input, please enter only '1', '2', '3', or '0'.");

            }
            input.nextLine();
            System.out.println( "--------------------------------" );

        } while(invalidInput);

        return userInput;
    }

    private static int musicEntryMenu(){
        userInput = 0;
        invalidInput = true;

        do {
            System.out.println("Please select from the following options:");

            System.out.println("Type '1' to add new songs to the library");
            System.out.println("Type '2' to add new artists to the library");
            System.out.println("Type '3' to add new albums to the library");

            System.out.println("Type '0' to go back");

            System.out.println("--------------------------------");

            try {
                System.out.print(">> ");
                userInput = input.nextInt();
                if(userInput == 1 || userInput == 2 || userInput == 3 || userInput == 0) {
                    invalidInput = false;
                } else {
                    System.out.println("Invalid input, please try again");
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid Input, please enter only '1', '2', '3', or '0'.");

            }
            input.nextLine();
            System.out.println( "--------------------------------" );

        } while(invalidInput);

        return userInput;
    }

    private static void generatePlaylistMenu(){
        System.out.println( "This will generate a playlist based on number of likes." );

        Playlist generatedPlaylist = library.generateRandomPlaylist();

        generatedPlaylist.outputXML();

        System.out.println( "Playlist generated." );

        System.out.println( "--------------------------------" );
    }

    private static void displayAllSongs(){
        ArrayList<Song> songList = library.getSongs();
        int numOfSongs = songList.size();

        System.out.println("There are " + numOfSongs + " songs in your library.");
        for(int i = 0; i < numOfSongs; i++){
            System.out.println(songList.get(i).getName());
        }
        System.out.println( "--------------------------------" );
    }

    private static void displayAllArtists(){
        ArrayList<String> artistList = DBConnections.getAllArtists();
        int numOfArtists = artistList.size();

        System.out.println("There are " + numOfArtists + " artists in your library.");
        for(int i = 0; i < numOfArtists; i++){
            System.out.println(artistList.get(i));
        }
        System.out.println( "--------------------------------" );
    }

    private static void displayAllAlbums(){
        ArrayList<String> albumList = DBConnections.getAllAlbums();
        int numOfAlbums = albumList.size();

        System.out.println("There are " + numOfAlbums + " albums in your library.");
        for(int i = 0; i < numOfAlbums; i++){
            System.out.println(albumList.get(i));
        }
        System.out.println( "--------------------------------" );
    }

    private static void displayComprehensive() {

        ArrayList<Song> songList = library.getSongs();
        ArrayList<Artist> artistList = new ArrayList<>();
        ArrayList<Album> albumList = new ArrayList<>();
        for(Song song : songList){
            Artist songArtist = DBConnections.findArtist(song.getPerformer().getAudioDbId());
            artistList.add(songArtist);
//            library.addArtist(songArtist);
            Album songAlbum = DBConnections.findAlbum(song.getAlbum().getAudioDbId());
            albumList.add(songAlbum);
//            library.addAlbum(songAlbum);
        }

        String leftAlignFormat = "| %-28s | %-22s | %-22s |%n";
        System.out.format("+------------------------------+------------------------+------------------------+%n");
        System.out.format("| Song name                    | Artist                 |  Album                 |%n");
        System.out.format("+------------------------------+------------------------+------------------------+%n");

        for (int i = 0; i < songList.size(); i++) {
            String songName = songList.get(i).getName();
            String artistName = artistList.get(i).getName();
            String albumName = albumList.get(i).getName();
            System.out.format(leftAlignFormat, songName, artistName, albumName);
        }
        System.out.format("+------------------------------+------------------------+------------------------+%n");

        System.out.println("--------------------------------");
    }

    private static void addNewSong(){
        Scanner input = new Scanner(System.in);
        System.out.print("Type in artist name here: ");
        String artistName = input.nextLine();
        System.out.println(artistName);
        System.out.print("Type in song name here: ");
        String songName = input.nextLine();
        System.out.println(songName);
        // hit up api

        Song newSong = AudioDBConnector.songSearch(artistName, songName);
        // use Return track details from artist/track name

        // if found match, then populate DB, otherwise ask to type again

        // get additional info
        System.out.println("new song DB ID: " + newSong.getAudioDBId());
//        System.out.println(DBConnections.findSong(newSong.getAudioDBId()));
        if(DBConnections.findSong(newSong.getAudioDBId()) != null){
            System.out.println("Song already in DB!");
        } else {
            // store in DB
            System.out.println("Storing song in DB ... ");
            library.addSong(newSong);
            boolean success = DBConnections.addNewSong(newSong);
            if(success){
                System.out.println("Song added to DB");
            } else {
                System.out.println("System Error... returning to last menu...");
            }
        }


        System.out.println("--------------------------------");
    }

    private static void addNewArtist(){
        Scanner input = new Scanner(System.in);
        System.out.print("Type in artist name here: ");
        String artistName = input.nextLine();
        System.out.println(artistName);
        // hit up api

        // use Return Artist details from artist name + Return Discography for an Artist with Album names and year only
        Artist newArtist = AudioDBConnector.artistSearch(artistName);

        // see if artist already exists

        if(DBConnections.findArtist(newArtist.getAudioDbId()) != null){
            System.out.println("Artist already in DB!");
        } else {
            // store in DB
            System.out.println("Storing artist in DB ... ");
            boolean success = DBConnections.addNewArtist(newArtist);
            if(success){
                System.out.println("Artist added to DB!");
            } else {
                System.out.println("System Error... returning to last menu...");
            }
        }
        System.out.println("--------------------------------");
    }

    private static void addNewAlbum(){
        Scanner input = new Scanner(System.in);
        System.out.print("Type in artist name here: ");
        String artistName = input.nextLine();
        System.out.println(artistName);
        System.out.print("Type in Album name here: ");
        String albumName = input.nextLine();


        // hit up api
        Album newAlbum = AudioDBConnector.albumSearch(artistName, albumName);

        // see if artist already exists

        if(DBConnections.findAlbum(newAlbum.getAudioDbId()) != null){
            System.out.println("Album already in DB!");
        } else {
            // store in DB
            System.out.println("Storing Album in DB ... ");
            boolean success = DBConnections.addNewAlbum(newAlbum);
            if(success){
                System.out.println("Album added to DB!");
            } else {
                System.out.println("System Error... returning to last menu...");
            }
        }
        System.out.println("--------------------------------");
    }

    private static void mainMenuNavigation(int input) {
        switch (input){
            case 1: // go to display menu
                int displayMenuInput = displayMenu();
                switch (displayMenuInput) {
                    case 1:
                        displayAllSongs();
                        mainMenuNavigation(mainMenu());
                        break;
                    case 2:
                        displayAllArtists();
                        mainMenuNavigation(mainMenu());
                        break;
                    case 3:
                        displayAllAlbums();
                        mainMenuNavigation(mainMenu());
                        break;
                    case 4:
                        displayComprehensive();
                        mainMenuNavigation(mainMenu());
                        break;
                    default:
                        mainMenuNavigation(mainMenu());
                        break;
                }
                break;
            case 2: // go to add music entry menu
                int musicEntryInput = musicEntryMenu();
                switch (musicEntryInput){
                    case 1:
                        addNewSong();
                        mainMenuNavigation(mainMenu());
                        break;
                    case 2:
                        addNewArtist();
                        mainMenuNavigation(mainMenu());
                        break;
                    case 3:
                        addNewAlbum();
                        mainMenuNavigation(mainMenu());
                        break;
                    default:
                        mainMenuNavigation(mainMenu());
                        break;
                }
                break;
            case 3: // go to generate playlist menu
                generatePlaylistMenu();
                mainMenuNavigation(mainMenu());
                break;
            default: // unknown failure, exit app
                System.out.println("exiting app");
                System.exit(0);
        }
    }

    public static void main( String[] args ){
        // welcome user to music library
        System.out.println( "--------------------------------" );
        System.out.println( "Welcome to your music library!" );
        System.out.println( "--------------------------------" );

        // create objects for all data from db
        library = new Library();

        DBConnections.populateLibrary(library);

        // UI Interface

        int mainMenuInput = mainMenu();
        System.out.println(mainMenuInput);

        mainMenuNavigation(mainMenuInput);
    }
}
