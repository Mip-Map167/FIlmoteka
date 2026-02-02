package main.java.view.dialogs;

import main.java.filmoteka.model.Film;
import main.java.filmoteka.model.Genre;
import main.java.util.DateValidator;
import main.java.util.RatingValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEditFilmDialog extends JDialog {
    private Film film;
    private boolean saved = false;

    private JTextField titleField;
    private JComboBox<Genre> genreComboBox;
    private JTextField yearField;
    private JTextField directorField;
    private JTextField ratingField;
    private JTextField durationField;
    private JTextField studioField;

    private JButton saveButton;
    private JButton cancelButton;

    public AddEditFilmDialog(Frame owner, Film film, boolean isEdit) {
        super(owner, isEdit ? "Редактирование фильма" : "Добавление фильма", true);
        this.film = film;

        initComponents();
        setupLayout();
        setupListeners();
        loadFilmData();

        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        Font font = new Font("Arial", Font.PLAIN, 14);

        // Поля ввода
        titleField = new JTextField(20);
        titleField.setFont(font);
        titleField.setForeground(Color.BLACK);

        genreComboBox = new JComboBox<>(Genre.values());
        genreComboBox.setFont(font);

        yearField = new JTextField(10);
        yearField.setFont(font);
        yearField.setForeground(Color.BLACK);

        directorField = new JTextField(20);
        directorField.setFont(font);
        directorField.setForeground(Color.BLACK);

        ratingField = new JTextField(10);
        ratingField.setFont(font);
        ratingField.setForeground(Color.BLACK);

        durationField = new JTextField(10);
        durationField.setFont(font);
        durationField.setForeground(Color.BLACK);

        studioField = new JTextField(20);
        studioField.setFont(font);
        studioField.setForeground(Color.BLACK);

        // Кнопки
        saveButton = new JButton("Сохранить");
        saveButton.setBackground(new Color(39, 174, 96));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setFocusPainted(false);

        cancelButton = new JButton("Отмена");
        cancelButton.setBackground(new Color(192, 57, 43));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);

        // Стилизация полей
        JTextField[] fields = {titleField, yearField, directorField, ratingField, durationField, studioField};
        for (JTextField field : fields) {
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }
    }

    private void setupLayout() {
        // Главная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Панель формы
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // Название
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel titleLabel = new JLabel("Название*:");
        titleLabel.setFont(labelFont);
        titleLabel.setForeground(Color.BLACK);
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(titleField, gbc);

        // Жанр
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel genreLabel = new JLabel("Жанр:");
        genreLabel.setFont(labelFont);
        genreLabel.setForeground(Color.BLACK);
        formPanel.add(genreLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(genreComboBox, gbc);

        // Год
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel yearLabel = new JLabel("Год выпуска*:");
        yearLabel.setFont(labelFont);
        yearLabel.setForeground(Color.BLACK);
        formPanel.add(yearLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(yearField, gbc);

        // Режиссёр
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel directorLabel = new JLabel("Режиссёр:");
        directorLabel.setFont(labelFont);
        directorLabel.setForeground(Color.BLACK);
        formPanel.add(directorLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(directorField, gbc);

        // Рейтинг
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        JLabel ratingLabel = new JLabel("Рейтинг (0-10):");
        ratingLabel.setFont(labelFont);
        ratingLabel.setForeground(Color.BLACK);
        formPanel.add(ratingLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(ratingField, gbc);

        // Продолжительность
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        JLabel durationLabel = new JLabel("Продолжительность (мин):");
        durationLabel.setFont(labelFont);
        durationLabel.setForeground(Color.BLACK);
        formPanel.add(durationLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(durationField, gbc);

        // Студия
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        JLabel studioLabel = new JLabel("Студия:");
        studioLabel.setFont(labelFont);
        studioLabel.setForeground(Color.BLACK);
        formPanel.add(studioLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(studioField, gbc);

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void setupListeners() {
        // Валидация полей ввода
        yearField.addActionListener(e -> {
            String yearText = yearField.getText().trim();
            if (!yearText.isEmpty()) {
                if (DateValidator.isValidYear(yearText)) {
                    yearField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                } else {
                    yearField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            }
        });

        ratingField.addActionListener(e -> {
            String ratingText = ratingField.getText().trim();
            if (!ratingText.isEmpty()) {
                if (RatingValidator.isValidRating(ratingText)) {
                    ratingField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                } else {
                    ratingField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            }
        });

        // Кнопка сохранения
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Film newFilm = createFilmFromForm();
                if (newFilm != null) {
                    film = newFilm;
                    saved = true;
                    dispose();
                }
            }
        });

        // Кнопка отмены
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saved = false;
                dispose();
            }
        });
    }

    private void loadFilmData() {
        if (film != null) {
            titleField.setText(film.getTitle());
            genreComboBox.setSelectedItem(film.getGenre());
            yearField.setText(String.valueOf(film.getYear()));
            directorField.setText(film.getDirector());
            ratingField.setText(String.valueOf(film.getRating()));
            durationField.setText(String.valueOf(film.getDuration()));
            studioField.setText(film.getStudio());
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public Film getFilm() {
        return film;
    }

    private Film createFilmFromForm() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Название фильма не может быть пустым",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            titleField.requestFocus();
            return null;
        }

        try {
            Film newFilm = new Film();
            newFilm.setTitle(title);
            newFilm.setGenre((Genre) genreComboBox.getSelectedItem());

            // Проверка года
            String yearText = yearField.getText().trim();
            if (yearText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Год выпуска не может быть пустым",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                yearField.requestFocus();
                return null;
            }
            try {
                newFilm.setYear(DateValidator.parseYear(yearText));
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка в годе выпуска: " + e.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                yearField.requestFocus();
                return null;
            }

            String director = directorField.getText().trim();
            newFilm.setDirector(director.isEmpty() ? "Не указан" : director);

            // Проверка рейтинга
            String ratingText = ratingField.getText().trim();
            if (!ratingText.isEmpty()) {
                try {
                    newFilm.setRating(RatingValidator.parseRating(ratingText));
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this,
                            "Ошибка в рейтинге: " + e.getMessage(),
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    ratingField.requestFocus();
                    return null;
                }
            } else {
                newFilm.setRating(0.0);
            }

            // Проверка продолжительности
            String durationText = durationField.getText().trim();
            if (!durationText.isEmpty()) {
                try {
                    int duration = Integer.parseInt(durationText);
                    if (duration <= 0) {
                        JOptionPane.showMessageDialog(this,
                                "Продолжительность должна быть положительным числом",
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                        durationField.requestFocus();
                        return null;
                    }
                    newFilm.setDuration(duration);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "Продолжительность должна быть целым числом",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    durationField.requestFocus();
                    return null;
                }
            } else {
                newFilm.setDuration(0);
            }

            String studio = studioField.getText().trim();
            newFilm.setStudio(studio.isEmpty() ? "Не указана" : studio);

            if (film != null) {
                newFilm.setId(film.getId());
            }

            return newFilm;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Неизвестная ошибка: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}