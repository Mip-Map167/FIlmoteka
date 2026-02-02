package main.java.filmoteka.dao;

import main.java.filmoteka.model.Film;
import java.util.List;

public class FilmDAO {
    private List<Film> films;
    private JsonDataManager jsonDataManager;
    private int nextId = 1;

    public FilmDAO() {
        jsonDataManager = new JsonDataManager();
        films = jsonDataManager.loadFilms();
        updateNextId();
    }

    private void updateNextId() {
        if (films.isEmpty()) {
            nextId = 1;
        } else {
            nextId = films.stream()
                    .mapToInt(Film::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    public List<Film> getAllFilms() {
        return films;
    }

    public void addFilm(Film film) {
        if (film.getId() == 0) {
            film.setId(nextId++);
        }
        films.add(film);
        jsonDataManager.saveFilms(films);
    }

    public void updateFilm(Film updatedFilm) {
        for (int i = 0; i < films.size(); i++) {
            if (films.get(i).getId() == updatedFilm.getId()) {
                films.set(i, updatedFilm);
                jsonDataManager.saveFilms(films);
                return;
            }
        }
    }

    public void deleteFilm(int id) {
        films.removeIf(film -> film.getId() == id);
        jsonDataManager.saveFilms(films);
        updateNextId();
    }

    public Film getFilmById(int id) {
        return films.stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public int getTotalCount() {
        return films.size();
    }
}