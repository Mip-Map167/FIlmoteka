package main.java.view.dialogs;

import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {

    public HelpDialog(JFrame parent) {
        super(parent, "Справка", true);
        initializeUI();
    }

    private void initializeUI() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());

        // Создаем текстовую панель с HTML содержимым
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);

        String helpText = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial; font-size: 12pt; }" +
                "h1 { color: #333; }" +
                "h2 { color: #555; margin-top: 20px; }" +
                "ul { margin: 10px 0; }" +
                "li { margin: 5px 0; }" +
                ".section { margin: 20px 0; }" +
                ".hotkey { color: #0066cc; font-weight: bold; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>Справка по программе \"Фильмотека\"</h1>" +

                "<div class='section'>" +
                "<h2>Основные функции</h2>" +
                "<ul>" +
                "<li><b>Добавить фильм</b> - добавление нового фильма в каталог</li>" +
                "<li><b>Редактировать фильм</b> - изменение данных о фильме</li>" +
                "<li><b>Удалить фильм</b> - удаление фильма из каталога</li>" +
                "<li><b>Экспорт каталога</b> - сохранение каталога в файл (JSON, CSV, XML)</li>" +
                "<li><b>Импорт каталога</b> - загрузка фильмов из файла</li>" +
                "<li><b>Поиск фильмов</b> - поиск фильмов по различным критериям</li>" +
                "<li><b>Обновить список</b> - обновление списка фильмов</li>" +
                "</ul>" +
                "</div>" +

                "<div class='section'>" +
                "<h2>Горячие клавиши</h2>" +
                "<ul>" +
                "<li><span class='hotkey'>Ctrl + A</span> - добавить фильм</li>" +
                "<li><span class='hotkey'>Ctrl + R</span> - редактировать фильм</li>" +
                "<li><span class='hotkey'>Ctrl + D</span> - удалить фильм</li>" +
                "<li><span class='hotkey'>Ctrl + E</span> - экспорт каталога</li>" +
                "<li><span class='hotkey'>Ctrl + I</span> - импорт каталога</li>" +
                "<li><span class='hotkey'>F1</span> - открыть справку</li>" +
                "<li><span class='hotkey'>Ctrl + Q</span> - выход из программы</li>" +
                "</ul>" +
                "</div>" +

                "<div class='section'>" +
                "<h2>Работа с поиском</h2>" +
                "<p>Для поиска фильмов:</p>" +
                "<ol>" +
                "<li>Введите текст в поле поиска</li>" +
                "<li>Выберите колонку для поиска из выпадающего списка</li>" +
                "<li>Нажмите кнопку \"Найти\" или клавишу Enter</li>" +
                "<li>Для отмены поиска нажмите кнопку \"Очистить\"</li>" +
                "</ol>" +
                "<p>Поиск поддерживает следующие варианты:</p>" +
                "<ul>" +
                "<li><b>Все колонки</b> - поиск по всем полям таблицы</li>" +
                "<li><b>Название</b> - поиск по названию фильма</li>" +
                "<li><b>Жанр</b> - поиск по жанру фильма</li>" +
                "<li><b>Год</b> - поиск по году выпуска</li>" +
                "<li><b>Режиссёр</b> - поиск по имени режиссёра</li>" +
                "<li><b>Рейтинг</b> - поиск по рейтингу</li>" +
                "<li><b>Студия</b> - поиск по студии производства</li>" +
                "</ul>" +
                "</div>" +

                "<div class='section'>" +
                "<h2>Форматы файлов</h2>" +
                "<p>Программа поддерживает три формата файлов:</p>" +
                "<ul>" +
                "<li><b>JSON</b> - универсальный формат для обмена данными</li>" +
                "<li><b>CSV</b> - табличный формат, совместимый с Excel</li>" +
                "<li><b>XML</b> - структурированный формат данных</li>" +
                "</ul>" +
                "</div>" +

                "<div class='section'>" +
                "<h2>Советы по использованию</h2>" +
                "<ul>" +
                "<li>Для редактирования или удаления фильма необходимо сначала выбрать его в таблице</li>" +
                "<li>При импорте файлов программа автоматически определяет формат по расширению</li>" +
                "<li>Для сортировки таблицы нажмите на заголовок колонки</li>" +
                "<li>Поиск не чувствителен к регистру букв</li>" +
                "</ul>" +
                "</div>" +

                "<div class='section' style='text-align: center; margin-top: 30px;'>" +
                "<p><b>Версия программы: 1.0</b></p>" +
                "<p>© 2026 Все права защищены</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        textPane.setText(helpText);
        textPane.setCaretPosition(0); // Прокрутка в начало

        // Добавляем панель прокрутки
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Кнопка закрытия
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(41, 128, 185));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
}