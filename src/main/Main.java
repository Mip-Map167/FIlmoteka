package main;

import main.java.view.main.MainFrame;
import javax.swing.*;

class MainController {
    public static void main(String[] args) {
        // Устанавливаем стиль системы
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Запускаем главное окно
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}