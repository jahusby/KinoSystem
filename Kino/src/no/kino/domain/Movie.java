package no.kino.domain;

public class Movie {
    int movieNumber;
    String movieName;

    public Movie(int movieNumber, String movieName) {
        this.movieNumber = movieNumber;
        this.movieName = movieName;
    }





    public Movie createMovie(int movieNumber, String movieName) {
        return new Movie(movieNumber, movieName);

    }

    public int getMovieNumber() {
        return movieNumber;
    }

    public void setMovieNumber(int movieNumber) {
        this.movieNumber = movieNumber;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieNumber=" + movieNumber +
                ", movieName='" + movieName + '\'' +
                '}';
    }
}
