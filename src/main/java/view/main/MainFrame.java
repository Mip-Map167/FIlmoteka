package main.java.view.main;

import main.java.filmoteka.model.Film;
import main.java.filmoteka.service.FilmService;
import main.java.view.components.FilmTableModel;
import main.java.view.components.StyledTable;
import main.java.view.dialogs.AddEditFilmDialog;
import main.java.util.FilmExporter;
import main.java.util.FilmImporter;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame {
    private FilmService filmService;
    private FilmTableModel tableModel;
    private JTable filmTable;
    private JLabel statsLabel;
    private JTextField searchField;
    private JComboBox<String> searchComboBox;
    private TableRowSorter<FilmTableModel> sorter;

    public MainFrame() {
        filmService = new FilmService();
        initializeUI();
        loadFilms();
    }

    private void initializeUI() {
        setTitle("Фильмотека - Каталог фильмов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // Создаем меню
        createMenuBar();

        // Главная панель
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(248, 249, 250));

        // Панель заголовка и кнопки обновления
        JPanel topPanel = createTopPanel();

        // Панель поиска
        JPanel searchPanel = createSearchPanel();

        // Панель с таблицей
        JPanel tablePanel = createTablePanel();

        // Панель статистики
        JPanel statsPanel = createStatsPanel();

        // Создаем центральную панель
        JPanel centerPanel = new JPanel(new BorderLayout(0, 0));
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(248, 249, 250));

        // Меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem exportItem = new JMenuItem("Экспорт каталога");
        exportItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        exportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exportItem.addActionListener(e -> exportFilms());

        JMenuItem importItem = new JMenuItem("Импорт каталога");
        importItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        importItem.addActionListener(e -> importFilms());

        fileMenu.add(exportItem);
        fileMenu.add(importItem);
        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(exitItem);

        // Меню "Редактировать"
        JMenu editMenu = new JMenu("Редактировать");
        editMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        editMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem addItem = new JMenuItem("Добавить фильм");
        addItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        addItem.addActionListener(e -> addFilm());

        JMenuItem editItem = new JMenuItem("Редактировать фильм");
        editItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        editItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        editItem.addActionListener(e -> editFilm());

        JMenuItem deleteItem = new JMenuItem("Удалить фильм");
        deleteItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        deleteItem.addActionListener(e -> deleteFilm());

        editMenu.add(addItem);
        editMenu.add(editItem);
        editMenu.add(deleteItem);

        // Меню "Помощь"
        JMenu helpMenu = new JMenu("Помощь");
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem helpItem = new JMenuItem("Справка");
        helpItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        helpItem.addActionListener(e -> showHelp());

        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        aboutItem.addActionListener(e -> showAbout());

        helpMenu.add(helpItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(248, 249, 250));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Заголовок слева
        JLabel titleLabel = new JLabel("ФИЛЬМОТЕКА");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));

        // Панель для кнопки обновления (справа)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setBackground(new Color(248, 249, 250));

        // Кнопка "Обновить" - простая кнопка без эффектов
        JButton refreshButton = new JButton("Обновить");
        refreshButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        refreshButton.setBackground(new Color(66, 133, 244));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        refreshButton.addActionListener(e -> refreshFilms());
        refreshButton.setToolTipText("Обновить список фильмов");

        buttonPanel.add(refreshButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(255, 255, 255));
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(232, 234, 237)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel searchLabel = new JLabel("Поиск фильмов:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchLabel.setForeground(new Color(95, 99, 104));

        searchField = new JTextField(25);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(new Color(32, 33, 36));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 220, 224), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        searchComboBox = new JComboBox<>(new String[] {
                "Все колонки", "Название", "Жанр", "Год", "Режиссёр", "Рейтинг", "Студия"
        });
        searchComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchComboBox.setBackground(Color.WHITE);
        searchComboBox.setForeground(new Color(32, 33, 36));
        searchComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 220, 224), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Кнопки поиска - простые кнопки без эффектов
        JButton searchButton = new JButton("Найти");
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchButton.setBackground(new Color(66, 133, 244));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> performSearch());

        JButton clearButton = new JButton("Очистить");
        clearButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clearButton.setBackground(new Color(95, 99, 104));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearSearch());

        searchField.addActionListener(e -> performSearch());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchComboBox);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        return searchPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Создаем таблицу
        filmTable = new StyledTable();

        // Создаем модель
        tableModel = new FilmTableModel(filmService.getAllFilms());
        filmTable.setModel(tableModel);

        // Добавляем сортировку
        sorter = new TableRowSorter<>(tableModel);
        filmTable.setRowSorter(sorter);

        // Настройка ширины колонок
        if (filmTable.getColumnCount() > 0) {
            filmTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // №
            filmTable.getColumnModel().getColumn(1).setPreferredWidth(250); // Наименование
            filmTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Жанр
            filmTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Год
            filmTable.getColumnModel().getColumn(4).setPreferredWidth(200); // Режиссёр
            filmTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Рейтинг
            filmTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Длительность
            filmTable.getColumnModel().getColumn(7).setPreferredWidth(150); // Студия
        }

        // Прокрутка для таблицы
        JScrollPane scrollPane = new JScrollPane(filmTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(232, 234, 237)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statsLabel.setForeground(new Color(95, 99, 104));
        updateStats();

        statsPanel.add(statsLabel);
        return statsPanel;
    }

    private void performSearch() {
        String searchText = searchField.getText().trim();
        String searchType = (String) searchComboBox.getSelectedItem();

        if (searchText.isEmpty()) {
            sorter.setRowFilter(null);
            updateStats();
            return;
        }

        try {
            RowFilter<FilmTableModel, Object> rowFilter = null;

            switch (searchType) {
                case "Все колонки":
                    rowFilter = RowFilter.regexFilter("(?i)" + searchText);
                    break;
                case "Название":
                    rowFilter = RowFilter.regexFilter("(?i)" + searchText, 1);
                    break;
                case "Жанр":
                    rowFilter = RowFilter.regexFilter("(?i)" + searchText, 2);
                    break;
                case "Год":
                    rowFilter = RowFilter.regexFilter(searchText, 3);
                    break;
                case "Режиссёр":
                    rowFilter = RowFilter.regexFilter("(?i)" + searchText, 4);
                    break;
                case "Рейтинг":
                    rowFilter = RowFilter.regexFilter(searchText, 5);
                    break;
                case "Студия":
                    rowFilter = RowFilter.regexFilter("(?i)" + searchText, 7);
                    break;
            }

            sorter.setRowFilter(rowFilter);

            // Обновляем статистику с учетом найденных фильмов
            updateSearchStats();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка при поиске: " + e.getMessage(),
                    "Ошибка поиска",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearSearch() {
        searchField.setText("");
        sorter.setRowFilter(null);
        updateStats();
        searchField.requestFocus();
    }

    private void updateSearchStats() {
        int total = filmService.getTotalFilms();
        int filtered = filmTable.getRowCount();

        if (filtered == total) {
            statsLabel.setText("Всего фильмов: " + total);
        } else {
            statsLabel.setText("Найдено: " + filtered + " из " + total + " фильмов");
        }
    }

    private void loadFilms() {
        tableModel.setFilms(filmService.getAllFilms());
        clearSearch();
    }

    private void updateStats() {
        int total = filmService.getTotalFilms();
        statsLabel.setText("Всего фильмов: " + total);
    }

    private void showHelp() {
        String helpText = "<html><div style='font-family: Segoe UI; font-size: 12pt;'>" +
                "<h2 style='color: #4285F4;'>Справка по программе \"Фильмотека\"</h2>" +
                "<p><b>Как использовать поиск:</b></p>" +
                "<ol>" +
                "<li>Введите текст в поле поиска выше таблицы</li>" +
                "<li>Выберите колонку для поиска из выпадающего списка</li>" +
                "<li>Нажмите кнопку <span style='color: #4285F4;'>Найти</span> или клавишу Enter</li>" +
                "<li>Для отмены поиска нажмите кнопку <span style='color: #5F6368;'>Очистить</span></li>" +
                "</ol>" +
                "<p><b>Основные функции (доступны через меню):</b></p>" +
                "<ul>" +
                "<li><b>Файл → Экспорт каталога</b> - сохранение каталога в файл (Ctrl+E)</li>" +
                "<li><b>Файл → Импорт каталога</b> - загрузка фильмов из файла (Ctrl+I)</li>" +
                "<li><b>Редактировать → Добавить фильм</b> - добавление нового фильма (Ctrl+A)</li>" +
                "<li><b>Редактировать → Редактировать фильм</b> - изменение данных о фильме (Ctrl+R)</li>" +
                "<li><b>Редактировать → Удалить фильм</b> - удаление фильма из каталога (Ctrl+D)</li>" +
                "</ul>" +
                "<p><b>Кнопка \"Обновить\":</b></p>" +
                "<p>Обновляет список фильмов и сбрасывает поиск.</p>" +
                "<p><b>Горячие клавиши:</b></p>" +
                "<ul>" +
                "<li><b>Ctrl+A</b> - добавить фильм</li>" +
                "<li><b>Ctrl+R</b> - редактировать фильм</li>" +
                "<li><b>Ctrl+D</b> - удалить фильм</li>" +
                "<li><b>Ctrl+E</b> - экспорт каталога</li>" +
                "<li><b>Ctrl+I</b> - импорт каталога</li>" +
                "<li><b>F1</b> - справка</li>" +
                "<li><b>Ctrl+Q</b> - выход</li>" +
                "</ul>" +
                "</div></html>";

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(helpText);
        textPane.setEditable(false);
        textPane.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Справка", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        String aboutText = "<html><div style='font-family: Segoe UI; font-size: 12pt; text-align: center;'>" +
                "<h2 style='color: #4285F4;'>Фильмотека</h2>" +
                "<p><b>Версия 1.0</b></p>" +
                "<p>Программа для управления каталогом фильмов</p>" +
                "<p>Разработано для удобного хранения и поиска информации о фильмах</p>" +
                "<hr style='color: #E8EAED;'>" +
                "<p>© 2024 Все права защищены</p>" +
                "</div></html>";

        JOptionPane.showMessageDialog(this, aboutText, "О программе", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addFilm() {
        AddEditFilmDialog dialog = new AddEditFilmDialog(this, null, false);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Film newFilm = dialog.getFilm();
            filmService.addFilm(newFilm);
            loadFilms();
            JOptionPane.showMessageDialog(this,
                    "Фильм \"" + newFilm.getTitle() + "\" успешно добавлен!",
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editFilm() {
        int selectedRow = filmTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Пожалуйста, выберите фильм для редактирования",
                    "Внимание",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Конвертируем индекс с учетом сортировки
        int modelRow = filmTable.convertRowIndexToModel(selectedRow);
        Film selectedFilm = tableModel.getFilmAt(modelRow);

        if (selectedFilm == null) {
            JOptionPane.showMessageDialog(this,
                    "Не удалось получить данные о фильме",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        AddEditFilmDialog dialog = new AddEditFilmDialog(this, selectedFilm, true);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Film updatedFilm = dialog.getFilm();
            filmService.updateFilm(updatedFilm);
            loadFilms();
            JOptionPane.showMessageDialog(this,
                    "Фильм \"" + updatedFilm.getTitle() + "\" успешно обновлен!",
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteFilm() {
        int selectedRow = filmTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Пожалуйста, выберите фильм для удаления",
                    "Внимание",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = filmTable.convertRowIndexToModel(selectedRow);
        Film selectedFilm = tableModel.getFilmAt(modelRow);

        if (selectedFilm == null) {
            JOptionPane.showMessageDialog(this,
                    "Не удалось получить данные о фильме",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить фильм \"" + selectedFilm.getTitle() + "\"?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            filmService.deleteFilm(selectedFilm.getId());
            loadFilms();
            JOptionPane.showMessageDialog(this,
                    "Фильм успешно удален",
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void exportFilms() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Экспорт каталога");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Фильтры для разных форматов
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "JSON файлы (*.json)", "json"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "CSV файлы (*.csv)", "csv"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "XML файлы (*.xml)", "xml"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String format = getFileFormat(fileChooser.getFileFilter());

            try {
                List<Film> films = filmService.getAllFilms();

                switch (format) {
                    case "json":
                        if (!fileToSave.getName().toLowerCase().endsWith(".json")) {
                            fileToSave = new File(fileToSave.getAbsolutePath() + ".json");
                        }
                        FilmExporter.exportToJson(films, fileToSave.getAbsolutePath());
                        break;
                    case "csv":
                        if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                            fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
                        }
                        FilmExporter.exportToCsv(films, fileToSave.getAbsolutePath());
                        break;
                    case "xml":
                        if (!fileToSave.getName().toLowerCase().endsWith(".xml")) {
                            fileToSave = new File(fileToSave.getAbsolutePath() + ".xml");
                        }
                        FilmExporter.exportToXml(films, fileToSave.getAbsolutePath());
                        break;
                }

                JOptionPane.showMessageDialog(this,
                        "Каталог успешно экспортирован в файл:\n" + fileToSave.getAbsolutePath(),
                        "Экспорт завершен",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка при экспорте: " + e.getMessage(),
                        "Ошибка экспорта",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void importFilms() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Импорт каталога");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Фильтры для разных форматов
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "JSON файлы (*.json)", "json"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "CSV файлы (*.csv)", "csv"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "XML файлы (*.xml)", "xml"));
        fileChooser.setAcceptAllFileFilterUsed(true);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToImport = fileChooser.getSelectedFile();
            String fileName = fileToImport.getName().toLowerCase();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Импортировать фильмы из файла?\n" +
                            "Существующие фильмы будут сохранены.",
                    "Подтверждение импорта",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                List<Film> importedFilms;

                if (fileName.endsWith(".json")) {
                    importedFilms = FilmImporter.importFromJson(fileToImport.getAbsolutePath());
                } else if (fileName.endsWith(".csv")) {
                    importedFilms = FilmImporter.importFromCsv(fileToImport.getAbsolutePath());
                } else if (fileName.endsWith(".xml")) {
                    importedFilms = FilmImporter.importFromXml(fileToImport.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Неподдерживаемый формат файла",
                            "Ошибка импорта",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Добавляем импортированные фильмы
                int addedCount = 0;
                for (Film film : importedFilms) {
                    try {
                        filmService.addFilm(film);
                        addedCount++;
                    } catch (Exception e) {
                        // Пропускаем дубликаты или некорректные данные
                    }
                }

                loadFilms();

                JOptionPane.showMessageDialog(this,
                        "Импорт завершен. Добавлено " + addedCount + " фильмов.",
                        "Импорт завершен",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка при импорте: " + e.getMessage(),
                        "Ошибка импорта",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private String getFileFormat(javax.swing.filechooser.FileFilter filter) {
        if (filter.getDescription().contains("JSON")) {
            return "json";
        } else if (filter.getDescription().contains("CSV")) {
            return "csv";
        } else if (filter.getDescription().contains("XML")) {
            return "xml";
        }
        return "json"; // По умолчанию
    }

    private void refreshFilms() {
        loadFilms();
        JOptionPane.showMessageDialog(this,
                "Список фильмов обновлен",
                "Обновление",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Устанавливаем системный Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Настройки в стиле браузера
                UIManager.put("Table.foreground", new Color(32, 33, 36));
                UIManager.put("TableHeader.foreground", new Color(32, 33, 36));
                UIManager.put("TableHeader.background", new Color(248, 249, 250));
                UIManager.put("Table.gridColor", new Color(232, 234, 237));
                UIManager.put("Table.selectionForeground", new Color(32, 33, 36));
                UIManager.put("Table.selectionBackground", new Color(232, 240, 254));

                UIManager.put("MenuBar.background", new Color(248, 249, 250));
                UIManager.put("Menu.background", new Color(248, 249, 250));
                UIManager.put("MenuItem.background", new Color(248, 249, 250));
                UIManager.put("MenuItem.selectionBackground", new Color(232, 240, 254));

            } catch (Exception e) {
                e.printStackTrace();
            }

            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}