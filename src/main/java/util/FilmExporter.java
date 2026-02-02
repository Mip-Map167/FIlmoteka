package main.java.util;

import main.java.filmoteka.model.Film;
import main.java.filmoteka.model.Genre;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FilmExporter {

    // Экспорт в JSON
    public static void exportToJson(List<Film> films, String filePath) throws Exception {
        JSONArray filmArray = new JSONArray();

        for (Film film : films) {
            JSONObject filmJson = new JSONObject();
            filmJson.put("id", film.getId());
            filmJson.put("title", film.getTitle());
            filmJson.put("genre", film.getGenre() != null ? film.getGenre().name() : "");
            filmJson.put("year", film.getYear());
            filmJson.put("director", film.getDirector());
            filmJson.put("rating", film.getRating());
            filmJson.put("duration", film.getDuration());
            filmJson.put("studio", film.getStudio());

            filmArray.add(filmJson);
        }

        JSONObject root = new JSONObject();
        root.put("films", filmArray);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(root.toJSONString());
            file.flush();
        }
    }

    // Экспорт в CSV
    public static void exportToCsv(List<Film> films, String filePath) throws Exception {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(filePath)))) {
            // Заголовок CSV
            writer.println("№,Название,Жанр,Год,Режиссёр,Рейтинг,Длительность (мин),Студия");

            // Данные
            int index = 1;
            for (Film film : films) {
                writer.printf("%d,\"%s\",\"%s\",%d,\"%s\",%.1f,%d,\"%s\"%n",
                        index++,
                        escapeCsv(film.getTitle()),
                        escapeCsv(film.getGenre() != null ? film.getGenre().toString() : ""),
                        film.getYear(),
                        escapeCsv(film.getDirector()),
                        film.getRating(),
                        film.getDuration(),
                        escapeCsv(film.getStudio()));
            }
        }
    }

    // Экспорт в XML
    public static void exportToXml(List<Film> films, String filePath) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("filmoteka");
        doc.appendChild(rootElement);

        for (Film film : films) {
            Element filmElement = doc.createElement("film");
            rootElement.appendChild(filmElement);

            addElement(doc, filmElement, "id", String.valueOf(film.getId()));
            addElement(doc, filmElement, "title", film.getTitle());
            addElement(doc, filmElement, "genre", film.getGenre() != null ? film.getGenre().name() : "");
            addElement(doc, filmElement, "year", String.valueOf(film.getYear()));
            addElement(doc, filmElement, "director", film.getDirector());
            addElement(doc, filmElement, "rating", String.valueOf(film.getRating()));
            addElement(doc, filmElement, "duration", String.valueOf(film.getDuration()));
            addElement(doc, filmElement, "studio", film.getStudio());
        }

        // Записываем в файл
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new java.io.File(filePath));
        transformer.transform(source, result);
    }

    private static void addElement(Document doc, Element parent, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        parent.appendChild(element);
    }

    private static String escapeCsv(String input) {
        if (input == null) return "";
        return input.replace("\"", "\"\"");
    }
}