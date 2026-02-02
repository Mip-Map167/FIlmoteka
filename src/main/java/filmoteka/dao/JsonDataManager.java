package main.java.filmoteka.dao;

import main.java.filmoteka.model.Film;
import main.java.filmoteka.model.Genre;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JsonDataManager {
    private static final String FILE_PATH = "src/main/resources/data/films.txt";

    /**
     * Загружает список фильмов из файла
     */
    public List<Film> loadFilms() {
        List<Film> films = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("Файл данных не найден. Будет создан новый файл.");
            return initializeSampleData();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Film film = parseFilm(line);
                    if (film != null) {
                        films.add(film);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных: " + e.getMessage());
            return initializeSampleData();
        }

        return films;
    }

    /**
     * Сохраняет список фильмов в файл
     */
    public boolean saveFilms(List<Film> films) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Film film : films) {
                writer.write(formatFilm(film));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных: " + e.getMessage());
            return false;
        }
    }

    /**
     * Парсит строку в объект Film
     */
    private Film parseFilm(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 8) {
                Film film = new Film();
                film.setId(Integer.parseInt(parts[0].trim()));
                film.setTitle(parts[1].trim());

                try {
                    film.setGenre(Genre.valueOf(parts[2].trim()));
                } catch (IllegalArgumentException e) {
                    System.err.println("Неизвестный жанр: " + parts[2].trim());
                    film.setGenre(Genre.DRAMA); // Жанр по умолчанию
                }

                film.setYear(Integer.parseInt(parts[3].trim()));
                film.setDirector(parts[4].trim());

                // Исправление: замена запятой на точку для парсинга double
                String ratingStr = parts[5].trim().replace(',', '.');
                film.setRating(Double.parseDouble(ratingStr));

                film.setDuration(Integer.parseInt(parts[6].trim()));
                film.setStudio(parts[7].trim());
                return film;
            }
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге фильма: " + line);
            System.err.println("Ошибка: " + e.getMessage());
        }
        return null;
    }

    /**
     * Форматирует объект Film в строку
     */
    private String formatFilm(Film film) {
        // Используем Locale.US для гарантированного использования точки в десятичных числах
        return String.format(Locale.US, "%d|%s|%s|%d|%s|%.1f|%d|%s",
                film.getId(),
                film.getTitle(),
                film.getGenre().name(),
                film.getYear(),
                film.getDirector(),
                film.getRating(),
                film.getDuration(),
                film.getStudio());
    }

    /**
     * Инициализирует примерные данные
     */
    private List<Film> initializeSampleData() {
        List<Film> films = new ArrayList<>();

        // Данные с точками в рейтингах
        films.add(new Film(1, "Интерстеллар", Genre.SCI_FI, 2014, "Кристофер Нолан", 8.6, 169, "Warner Bros."));
        films.add(new Film(2, "Побег из Шоушенка", Genre.DRAMA, 1994, "Фрэнк Дарабонт", 9.3, 142, "Columbia Pictures"));
        films.add(new Film(3, "Крёстный отец", Genre.CRIME, 1972, "Фрэнсис Форд Коппола", 9.2, 175, "Paramount Pictures"));
        films.add(new Film(4, "Тёмный рыцарь", Genre.ACTION, 2008, "Кристофер Нолан", 9.0, 152, "Warner Bros."));
        films.add(new Film(5, "Форрест Гамп", Genre.DRAMA, 1994, "Роберт Земекис", 8.8, 142, "Paramount Pictures"));
        films.add(new Film(6, "Начало", Genre.SCI_FI, 2010, "Кристофер Нолан", 8.8, 148, "Warner Bros."));
        films.add(new Film(7, "Список Шиндлера", Genre.DRAMA, 1993, "Стивен Спилберг", 8.9, 195, "Universal Pictures"));
        films.add(new Film(8, "Властелин колец: Возвращение короля", Genre.FANTASY, 2003, "Питер Джексон", 8.9, 201, "New Line Cinema"));
        films.add(new Film(9, "Криминальное чтиво", Genre.CRIME, 1994, "Квентин Тарантино", 8.9, 154, "Miramax Films"));
        films.add(new Film(10, "Хороший, плохой, злой", Genre.WESTERN, 1966, "Серджио Леоне", 8.8, 161, "United Artists"));
        films.add(new Film(11, "Матрица", Genre.ACTION, 1999, "Братья Вачовски", 8.7, 136, "Warner Bros."));
        films.add(new Film(12, "Титаник", Genre.ROMANCE, 1997, "Джеймс Кэмерон", 7.8, 195, "Paramount Pictures"));
        films.add(new Film(13, "Аватар", Genre.SCI_FI, 2009, "Джеймс Кэмерон", 7.8, 162, "20th Century Fox"));
        films.add(new Film(14, "Звёздные войны: Новая надежда", Genre.SCI_FI, 1977, "Джордж Лукас", 8.6, 121, "20th Century Fox"));
        films.add(new Film(15, "Пираты Карибского моря: Проклятие Черной жемчужины", Genre.ADVENTURE, 2003, "Гор Вербински", 8.0, 143, "Walt Disney Pictures"));
        films.add(new Film(16, "Гарри Поттер и философский камень", Genre.FANTASY, 2001, "Крис Коламбус", 7.6, 152, "Warner Bros."));
        films.add(new Film(17, "Мстители: Финал", Genre.ACTION, 2019, "Энтони и Джо Руссо", 8.4, 181, "Marvel Studios"));
        films.add(new Film(18, "Парк Юрского периода", Genre.ADVENTURE, 1993, "Стивен Спилберг", 8.1, 127, "Universal Pictures"));
        films.add(new Film(19, "Король Лев", Genre.ANIMATION, 1994, "Роджер Аллерс, Роб Минкофф", 8.5, 88, "Walt Disney Pictures"));
        films.add(new Film(20, "Назад в будущее", Genre.SCI_FI, 1985, "Роберт Земекис", 8.5, 116, "Universal Pictures"));

        saveFilms(films);
        return films;
    }
}