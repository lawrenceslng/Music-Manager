# CS 514 Assignment 7: Music Manager  

## Introduction  
This is my submission for Assignment 7 in CS 514.
This is a simple CLI program that serves as a music manager. Users can store their list of songs, albums, and artists in this application.
Specifically, users can: 
1) display the list of songs, albums, or artists within the library
2) add new songs, albums, or artists
3) generate XML file displaying a list of songs according to specific criteria

This application utilizes the following packages, technologies, and resources:
- Maven
- JSON.simple
- Junit
- SQLite DB
- AudioDB API

The javadocs should be in the `target/site/apidocs/allclasses-index.html`. 

## How To Get Started
- clone the repo using HTTP or SSH
- navigate to the root directory
- execute the following command:
  `java -jar Music-Manager-1.0-SNAPSHOT-jar-with-dependencies.jar`
- the program should run without problem

## Using the Program
The program consists of the main steps.

In the main menu, users can:
- type '1' to display the songs, albums, or artists that exist in the database
- type '2' to input information to add new song, album, or artist to database
- type '3' to generate a XML file containing a list of songs according to minimum number of listeners

If choosing to display songs, albums, or artists (option 1), users are presented with submenu with options:
- type '1' to display just the songs
- type '2' to display just the artists
- type '3' to display just the albums
- type '4' to display the songs, artists, and albums in a tabular format

If choosing to input information (option 2), users are presented with submenu with options:
- type '1' to add new song to library/database
- type '2' to add new artist to library/database
- type '3' to add new album to library/database

Note: to add new song or album requires providing both artist name and song/album name.

Note: adding artist and album only adds the artist and album to the database, no songs. So you can have existing artists and albums with no corresponding songs.

If choosing to generate a XML file (option 3), users are prompted to input the minimum number of listeners they would like as the requirement for all songs in the playlist to be generated. The file is then generated in the root directory with the name `playlist_outputXML.xml`.

At all stages, the user is presented with option '0' to go back or exit the program.

## Improvements to be Made  

There are a number of improvements to be made:
- a number of methods are not used, they can be useful for future iterations
- the classes may not be written in the most OOP way, could use refactoring
- the DB is not being utilized to its full potential as not all information retrieved from AudioDB API is used in this first iteration
- the tabular display of songs/artist/album can be expanded to include information like song duration, number of listeners etc.

## Final Thoughts  
Hopefully this program works for now, there can be plenty of improvements to be made later. I did learn a lot about working with Maven through struggling trying to perform unit tests and creating the JAR.
