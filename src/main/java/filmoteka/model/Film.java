package main.java.filmoteka.model;

public class Film {
    private int id;
    private String title;
    private Genre genre;
    private int year;
    private String director;
    private double rating;
    private int duration;
    private String studio;

    public Film() {
    }

    public Film(int id, String title, Genre genre, int year, String director, double rating, int duration, String studio) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.duration = duration;
        this.studio = studio;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title != null ? title : "";
    }

    public void setTitle(String title) {
        this.title = title != null ? title : "";
    }

    public Genre getGenre() {
        return genre != null ? genre : Genre.DRAMA;
    }

    public void setGenre(Genre genre) {
        this.genre = genre != null ? genre : Genre.DRAMA;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director != null ? director : "";
    }

    public void setDirector(String director) {
        this.director = director != null ? director : "";
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStudio() {
        return studio != null ? studio : "";
    }

    public void setStudio(String studio) {
        this.studio = studio != null ? studio : "";
    }

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}