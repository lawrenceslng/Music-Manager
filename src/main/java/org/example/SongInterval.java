package org.example;

/**
 * This class represents the song duration/length.
 */
public class SongInterval {
    private int length;

    /**
     * Class Constructor
     *
     * @param l an Integer representing the number of seconds a song has
     */
    public SongInterval(int l) {
        this.length = l;
    }

    /**
     * Returns a String containing the length of the song in mm:ss format
     *
     * @return a String showing the length of the song in mm:ss format
     */
    public String toString() {
        int minutes = length / 60;
        int seconds = length % 60;
        String secondsString = String.valueOf(seconds);
        if(seconds < 10){
            secondsString = "0" + secondsString;
        }
        return String.format("%d:" + secondsString, minutes);

    }

}
