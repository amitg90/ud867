package com.example.jokeproviderlib;

public class JokeProvider {
    public String getJoke(int randomNum) {
        switch (randomNum) {
            case 0:
                return ("Showing Joke at index 0");
            case 1:
                return ("Showing Joke at index 1");
            case 2:
                return ("Showing Joke at index 2");
            case 3:
                return ("Showing Joke at index 3");
            case 4:
                return ("Showing Joke at index 4");
            default:
                return ("Showing Joke at default index");
        }
    }
}
