package main.java.controller;

import main.java.view.main.MainFrame;
import javax.swing.*;

public class MainController {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Устанавливаем стиль системы
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Создаем и отображаем главное окно
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}