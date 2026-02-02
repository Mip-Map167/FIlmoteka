package main.java.view.dialogs;

import main.java.filmoteka.model.Genre;
import main.java.view.components.RoundedButton;

import javax.swing.*;
import java.awt.*;

public class FilterDialog extends JDialog {
    private JComboBox<Genre> genreComboBox;
    private JTextField yearFromField;
    private JTextField yearToField;
    private JTextField ratingFromField;
    private JTextField ratingToField;
    private JTextField studioField;

    private RoundedButton applyButton;
    private RoundedButton resetButton;
    private RoundedButton cancelButton;

    public FilterDialog(Frame owner) {
        super(owner, "Фильтр фильмов", true);
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 500);
        setLocationRelativeTo(getOwner());

        // Главная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Панель формы
        JPanel formPanel = createFormPanel();

        // Панель кнопок
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Жанр
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel genreLabel = new JLabel("Жанр:");
        genreLabel.setFont(labelFont);
        formPanel.add(genreLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        genreComboBox = new JComboBox<>();
        genreComboBox.addItem(null); // Пустой элемент для "все жанры"
        for (Genre genre : Genre.values()) {
            genreComboBox.addItem(genre);
        }
        genreComboBox.setFont(fieldFont);
        formPanel.add(genreComboBox, gbc);

        // Год от
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel yearFromLabel = new JLabel("Год от:");
        yearFromLabel.setFont(labelFont);
        formPanel.add(yearFromLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        yearFromField = new JTextField();
        yearFromField.setFont(fieldFont);
        formPanel.add(yearFromField, gbc);

        // Год до
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel yearToLabel = new JLabel("Год до:");
        yearToLabel.setFont(labelFont);
        formPanel.add(yearToLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        yearToField = new JTextField();
        yearToField.setFont(fieldFont);
        formPanel.add(yearToField, gbc);

        // Рейтинг от
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel ratingFromLabel = new JLabel("Рейтинг от:");
        ratingFromLabel.setFont(labelFont);
        formPanel.add(ratingFromLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        ratingFromField = new JTextField();
        ratingFromField.setFont(fieldFont);
        formPanel.add(ratingFromField, gbc);

        // Рейтинг до
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        JLabel ratingToLabel = new JLabel("Рейтинг до:");
        ratingToLabel.setFont(labelFont);
        formPanel.add(ratingToLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        ratingToField = new JTextField();
        ratingToField.setFont(fieldFont);
        formPanel.add(ratingToField, gbc);

        // Студия
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        JLabel studioLabel = new JLabel("Студия:");
        studioLabel.setFont(labelFont);
        formPanel.add(studioLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        studioField = new JTextField();
        studioField.setFont(fieldFont);
        formPanel.add(studioField, gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        applyButton = new RoundedButton("Применить");
        applyButton.setBackgroundColor(new Color(39, 174, 96));
        applyButton.setHoverColor(new Color(46, 204, 113));

        resetButton = new RoundedButton("Сбросить");
        resetButton.setBackgroundColor(new Color(241, 196, 15));
        resetButton.setHoverColor(new Color(243, 156, 18));

        cancelButton = new RoundedButton("Отмена");
        cancelButton.setBackgroundColor(new Color(231, 76, 60));
        cancelButton.setHoverColor(new Color(192, 57, 43));

        buttonPanel.add(applyButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    public JComboBox<Genre> getGenreComboBox() {
        return genreComboBox;
    }

    public JTextField getYearFromField() {
        return yearFromField;
    }

    public JTextField getYearToField() {
        return yearToField;
    }

    public JTextField getRatingFromField() {
        return ratingFromField;
    }

    public JTextField getRatingToField() {
        return ratingToField;
    }

    public JTextField getStudioField() {
        return studioField;
    }

    public RoundedButton getApplyButton() {
        return applyButton;
    }

    public RoundedButton getResetButton() {
        return resetButton;
    }

    public RoundedButton getCancelButton() {
        return cancelButton;
    }
}