package test.java.main.filmoteka;

import main.java.filmoteka.model.Film;
import main.java.filmoteka.model.Genre;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    @Test
    void testFilmCreation() {
        Film film = new Film(1, "Интерстеллар", Genre.MYSTERY, 2014,
                "Кристофер Нолан", 8.6, 169, "Warner Bros.");

        assertEquals(1, film.getId());
        assertEquals("Интерстеллар", film.getTitle());
        assertEquals(Genre.MYSTERY, film.getGenre());
        assertEquals(2014, film.getYear());
        assertEquals("Кристофер Нолан", film.getDirector());
        assertEquals(8.6, film.getRating(), 0.01);
        assertEquals(169, film.getDuration());
        assertEquals("Warner Bros.", film.getStudio());
    }

    @Test
    void testSettersAndGetters() {
        Film film = new Film();
        film.setId(99);
        film.setTitle("Тест");
        film.setGenre(Genre.DRAMA);
        film.setYear(2025);
        film.setDirector("Режиссер");
        film.setRating(7.5);
        film.setDuration(120);
        film.setStudio("Студия");

        assertEquals(99, film.getId());
        assertEquals("Тест", film.getTitle());
        assertEquals(Genre.DRAMA, film.getGenre());
        assertEquals(2025, film.getYear());
        assertEquals("Режиссер", film.getDirector());
        assertEquals(7.5, film.getRating(), 0.01);
        assertEquals(120, film.getDuration());
        assertEquals("Студия", film.getStudio());
    }

    @Test
    void testToString() {
        Film film = new Film(1, "Матрица", Genre.MYSTERY, 1999,
                "Братья Вачовски", 8.7, 136, "Warner Bros.");
        assertEquals("Матрица (1999)", film.toString());
    }

    @Test
    void testNullSafety() {
        Film film = new Film();
        film.setTitle(null);
        film.setDirector(null);
        film.setStudio(null);

        assertEquals("", film.getTitle());
        assertEquals("", film.getDirector());
        assertEquals("", film.getStudio());
    }
}