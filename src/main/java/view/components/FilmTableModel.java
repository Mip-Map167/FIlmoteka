package main.java.view.components;

import main.java.filmoteka.model.Film;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FilmTableModel extends AbstractTableModel {
    private List<Film> films;
    private final String[] columnNames = {
            "№", "Наименование", "Жанр", "Год", "Режиссёр",
            "Рейтинг", "Длительность", "Студия"
    };

    public FilmTableModel(List<Film> films) {
        this.films = films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return films.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class;  // №
            case 3: return Integer.class;  // Год
            case 5: return Double.class;   // Рейтинг
            case 6: return String.class;   // Длительность
            default: return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Film film = films.get(rowIndex);

        switch (columnIndex) {
            case 0: return rowIndex + 1;  // Автонумерация
            case 1: return film.getTitle();
            case 2: return film.getGenre() != null ? film.getGenre().toString() : "";
            case 3: return film.getYear();
            case 4: return film.getDirector();
            case 5: return film.getRating(); // Возвращаем Double
            case 6: return film.getDuration() + " мин";
            case 7: return film.getStudio();
            default: return "";
        }
    }

    public Film getFilmAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < films.size()) {
            return films.get(rowIndex);
        }
        return null;
    }
}