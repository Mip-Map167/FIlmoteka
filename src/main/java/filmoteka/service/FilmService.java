package main.java.filmoteka.service;

import main.java.filmoteka.dao.FilmDAO;
import main.java.filmoteka.model.Film;
import java.util.List;

public class FilmService {
    private FilmDAO filmDAO;

    public FilmService() {
        filmDAO = new FilmDAO();
    }

    public List<Film> getAllFilms() {
        return filmDAO.getAllFilms();
    }

    public void addFilm(Film film) {
        filmDAO.addFilm(film);
    }

    public void updateFilm(Film film) {
        filmDAO.updateFilm(film);
    }

    public void deleteFilm(int id) {
        filmDAO.deleteFilm(id);
    }

    public Film getFilmById(int id) {
        return filmDAO.getFilmById(id);
    }

    public int getTotalFilms() {
        return filmDAO.getTotalCount();
    }
}