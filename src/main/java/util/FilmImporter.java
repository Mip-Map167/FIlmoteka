package main.java.util;

import main.java.filmoteka.model.Film;
import main.java.filmoteka.model.Genre;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FilmImporter {

    // Импорт из JSON
    public static List<Film> importFromJson(String filePath) throws Exception {
        List<Film> films = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject root = (JSONObject) parser.parse(reader);
            JSONArray filmArray = (JSONArray) root.get("films");

            for (Object obj : filmArray) {
                JSONObject filmJson = (JSONObject) obj;
                Film film = jsonToFilm(filmJson);
                films.add(film);
            }
        }

        return films;
    }

    // Импорт из CSV
    public static List<Film> importFromCsv(String filePath) throws Exception {
        List<Film> films = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Пропускаем заголовок
                }

                Film film = parseCsvLine(line);
                if (film != null) {
                    films.add(film);
                }
            }
        }

        return films;
    }

    // Импорт из XML
    public static List<Film> importFromXml(String filePath) throws Exception {
        List<Film> films = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new java.io.File(filePath));

        doc.getDocumentElement().normalize();
        NodeList filmNodes = doc.getElementsByTagName("film");

        for (int i = 0; i < filmNodes.getLength(); i++) {
            Node node = filmNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Film film = xmlToFilm(element);
                films.add(film);
            }
        }

        return films;
    }

    private static Film jsonToFilm(JSONObject json) {
        Film film = new Film();

        try {
            Long id = (Long) json.get("id");
            if (id != null) {
                film.setId(id.intValue());
            }

            film.setTitle((String) json.get("title"));

            String genreStr = (String) json.get("genre");
            if (genreStr != null && !genreStr.isEmpty()) {
                try {
                    film.setGenre(Genre.valueOf(genreStr));
                } catch (IllegalArgumentException e) {
                    // Оставляем null, если жанр не распознан
                }
            }

            Long year = (Long) json.get("year");
            if (year != null) {
                film.setYear(year.intValue());
            }

            film.setDirector((String) json.get("director"));

            Double rating = (Double) json.get("rating");
            if (rating != null) {
                film.setRating(rating);
            }

            Long duration = (Long) json.get("duration");
            if (duration != null) {
                film.setDuration(duration.intValue());
            }

            film.setStudio((String) json.get("studio"));

        } catch (Exception e) {
            System.err.println("Ошибка при чтении JSON записи: " + e.getMessage());
        }

        return film;
    }

    private static Film parseCsvLine(String line) {
        try {
            List<String> values = new ArrayList<>();
            boolean inQuotes = false;
            StringBuilder buffer = new StringBuilder();

            for (char c : line.toCharArray()) {
                if (c == '"') {
                    inQuotes = !inQuotes;
                } else if (c == ',' && !inQuotes) {
                    values.add(buffer.toString());
                    buffer.setLength(0);
                } else {
                    buffer.append(c);
                }
            }
            values.add(buffer.toString());

            if (values.size() >= 8) {
                Film film = new Film();

                // Пропускаем первый столбец (№)
                film.setTitle(values.get(1).trim());

                String genreStr = values.get(2).trim();
                if (!genreStr.isEmpty()) {
                    try {
                        film.setGenre(Genre.valueOf(genreStr.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        // Игнорируем неверный жанр
                    }
                }

                try {
                    film.setYear(Integer.parseInt(values.get(3).trim()));
                } catch (NumberFormatException e) {
                    film.setYear(0);
                }

                film.setDirector(values.get(4).trim());

                try {
                    film.setRating(Double.parseDouble(values.get(5).trim()));
                } catch (NumberFormatException e) {
                    film.setRating(0.0);
                }

                try {
                    film.setDuration(Integer.parseInt(values.get(6).trim()));
                } catch (NumberFormatException e) {
                    film.setDuration(0);
                }

                film.setStudio(values.get(7).trim());

                return film;
            }
        } catch (Exception e) {
            System.err.println("Ошибка при чтении CSV строки: " + e.getMessage());
        }

        return null;
    }

    private static Film xmlToFilm(Element element) {
        Film film = new Film();

        try {
            String idStr = getElementText(element, "id");
            if (idStr != null && !idStr.isEmpty()) {
                film.setId(Integer.parseInt(idStr));
            }

            film.setTitle(getElementText(element, "title"));

            String genreStr = getElementText(element, "genre");
            if (genreStr != null && !genreStr.isEmpty()) {
                try {
                    film.setGenre(Genre.valueOf(genreStr));
                } catch (IllegalArgumentException e) {
                    // Игнорируем неверный жанр
                }
            }

            String yearStr = getElementText(element, "year");
            if (yearStr != null && !yearStr.isEmpty()) {
                try {
                    film.setYear(Integer.parseInt(yearStr));
                } catch (NumberFormatException e) {
                    film.setYear(0);
                }
            }

            film.setDirector(getElementText(element, "director"));

            String ratingStr = getElementText(element, "rating");
            if (ratingStr != null && !ratingStr.isEmpty()) {
                try {
                    film.setRating(Double.parseDouble(ratingStr));
                } catch (NumberFormatException e) {
                    film.setRating(0.0);
                }
            }

            String durationStr = getElementText(element, "duration");
            if (durationStr != null && !durationStr.isEmpty()) {
                try {
                    film.setDuration(Integer.parseInt(durationStr));
                } catch (NumberFormatException e) {
                    film.setDuration(0);
                }
            }

            film.setStudio(getElementText(element, "studio"));

        } catch (Exception e) {
            System.err.println("Ошибка при чтении XML элемента: " + e.getMessage());
        }

        return film;
    }

    private static String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }
}