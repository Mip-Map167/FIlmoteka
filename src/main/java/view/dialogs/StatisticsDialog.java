package main.java.view.dialogs;

import main.java.filmoteka.model.Film;
import main.java.filmoteka.model.Genre;
import main.java.view.components.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsDialog extends JDialog {
    private List<Film> films;
    private JLabel totalFilmsLabel;
    private JLabel averageRatingLabel;
    private JLabel averageDurationLabel;
    private JLabel topGenreLabel;
    private JLabel topStudioLabel;

    public StatisticsDialog(Frame owner, List<Film> films) {
        super(owner, "Статистика фильмотеки", true);
        this.films = films;
        initializeUI();
        calculateStatistics();
    }

    private void initializeUI() {
        setSize(500, 400);
        setLocationRelativeTo(getOwner());

        // Главная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Панель статистики
        JPanel statsPanel = createStatsPanel();

        // Панель кнопок
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridBagLayout());
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font valueFont = new Font("Arial", Font.PLAIN, 16);

        // Общее количество фильмов
        gbc.gridy = 0;
        JLabel totalLabel = new JLabel("Общее количество фильмов:");
        totalLabel.setFont(labelFont);
        statsPanel.add(totalLabel, gbc);

        gbc.gridy = 1;
        totalFilmsLabel = new JLabel();
        totalFilmsLabel.setFont(valueFont);
        totalFilmsLabel.setForeground(new Color(52, 152, 219));
        statsPanel.add(totalFilmsLabel, gbc);

        // Средний рейтинг
        gbc.gridy = 2;
        JLabel ratingLabel = new JLabel("Средний рейтинг:");
        ratingLabel.setFont(labelFont);
        statsPanel.add(ratingLabel, gbc);

        gbc.gridy = 3;
        averageRatingLabel = new JLabel();
        averageRatingLabel.setFont(valueFont);
        averageRatingLabel.setForeground(new Color(39, 174, 96));
        statsPanel.add(averageRatingLabel, gbc);

        // Средняя продолжительность
        gbc.gridy = 4;
        JLabel durationLabel = new JLabel("Средняя продолжительность:");
        durationLabel.setFont(labelFont);
        statsPanel.add(durationLabel, gbc);

        gbc.gridy = 5;
        averageDurationLabel = new JLabel();
        averageDurationLabel.setFont(valueFont);
        averageDurationLabel.setForeground(new Color(155, 89, 182));
        statsPanel.add(averageDurationLabel, gbc);

        // Самый популярный жанр
        gbc.gridy = 6;
        JLabel genreLabel = new JLabel("Самый популярный жанр:");
        genreLabel.setFont(labelFont);
        statsPanel.add(genreLabel, gbc);

        gbc.gridy = 7;
        topGenreLabel = new JLabel();
        topGenreLabel.setFont(valueFont);
        topGenreLabel.setForeground(new Color(230, 126, 34));
        statsPanel.add(topGenreLabel, gbc);

        // Самая популярная студия
        gbc.gridy = 8;
        JLabel studioLabel = new JLabel("Самая популярная студия:");
        studioLabel.setFont(labelFont);
        statsPanel.add(studioLabel, gbc);

        gbc.gridy = 9;
        topStudioLabel = new JLabel();
        topStudioLabel.setFont(valueFont);
        topStudioLabel.setForeground(new Color(231, 76, 60));
        statsPanel.add(topStudioLabel, gbc);

        return statsPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        RoundedButton closeButton = new RoundedButton("Закрыть");
        closeButton.setBackgroundColor(new Color(52, 152, 219));
        closeButton.setHoverColor(new Color(41, 128, 185));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);
        return buttonPanel;
    }

    private void calculateStatistics() {
        if (films.isEmpty()) {
            totalFilmsLabel.setText("0");
            averageRatingLabel.setText("0.0");
            averageDurationLabel.setText("0 мин");
            topGenreLabel.setText("Нет данных");
            topStudioLabel.setText("Нет данных");
            return;
        }

        // Общее количество
        totalFilmsLabel.setText(String.valueOf(films.size()));

        // Средний рейтинг
        double avgRating = films.stream()
                .mapToDouble(Film::getRating)
                .average()
                .orElse(0.0);
        averageRatingLabel.setText(String.format("%.2f", avgRating));

        // Средняя продолжительность
        double avgDuration = films.stream()
                .mapToInt(Film::getDuration)
                .average()
                .orElse(0.0);
        averageDurationLabel.setText(String.format("%.1f мин", avgDuration));

        // Самый популярный жанр
        Map<Genre, Long> genreCounts = films.stream()
                .collect(Collectors.groupingBy(Film::getGenre, Collectors.counting()));

        if (!genreCounts.isEmpty()) {
            Map.Entry<Genre, Long> topGenre = genreCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .orElse(null);
            if (topGenre != null) {
                topGenreLabel.setText(String.format("%s (%d фильмов)",
                        topGenre.getKey().toString(), topGenre.getValue()));
            }
        } else {
            topGenreLabel.setText("Нет данных");
        }

        // Самая популярная студия
        Map<String, Long> studioCounts = films.stream()
                .collect(Collectors.groupingBy(Film::getStudio, Collectors.counting()));

        if (!studioCounts.isEmpty()) {
            Map.Entry<String, Long> topStudio = studioCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .orElse(null);
            if (topStudio != null) {
                topStudioLabel.setText(String.format("%s (%d фильмов)",
                        topStudio.getKey(), topStudio.getValue()));
            }
        } else {
            topStudioLabel.setText("Нет данных");
        }
    }
}