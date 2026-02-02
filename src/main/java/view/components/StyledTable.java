package main.java.view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StyledTable extends JTable {

    public StyledTable() {
        // Настройка внешнего вида таблицы в стиле браузера
        setRowHeight(36);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setSelectionBackground(new Color(232, 240, 254));
        setSelectionForeground(new Color(32, 33, 36));
        setGridColor(new Color(232, 234, 237));
        setShowGrid(true);
        setIntercellSpacing(new Dimension(0, 0));

        // Включаем отображение линий сетки
        setShowHorizontalLines(true);
        setShowVerticalLines(true);

        // Отключаем перетаскивание колонок
        getTableHeader().setReorderingAllowed(false);

        // Настройка внешнего вида ячеек
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                // Форматируем значение перед отображением
                Object displayValue = value;
                if (column == 5 && value instanceof Number) {
                    // Форматируем рейтинг до одного знака после запятой
                    displayValue = String.format("%.1f", ((Number) value).doubleValue());
                }

                Component c = super.getTableCellRendererComponent(table, displayValue, isSelected, hasFocus, row, column);

                // Устанавливаем цвет текста в стиле браузера
                c.setForeground(new Color(32, 33, 36));

                // Устанавливаем белый фон для всех ячеек
                c.setBackground(Color.WHITE);

                // Если строка выделена
                if (isSelected) {
                    c.setBackground(new Color(232, 240, 254));
                }

                // Центрирование для определенных колонок (№, Год, Рейтинг, Длительность)
                if (column == 0 || column == 3 || column == 5 || column == 6) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
                }

                // Устанавливаем границу для ячейки
                ((JLabel) c).setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(232, 234, 237)),
                        BorderFactory.createEmptyBorder(0, 12, 0, 12)
                ));

                return c;
            }
        });
    }

    @Override
    public void setModel(javax.swing.table.TableModel dataModel) {
        super.setModel(dataModel);

        // Настройка заголовка после установки модели
        if (getTableHeader() != null) {
            JTableHeader header = getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 13));
            header.setBackground(new Color(248, 249, 250));
            header.setForeground(new Color(95, 99, 104));
            header.setReorderingAllowed(false);

            // Устанавливаем высоту заголовка
            header.setPreferredSize(new Dimension(header.getWidth(), 40));

            // Настраиваем рендерер для заголовка
            header.setDefaultRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus,
                                                               int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Центрирование заголовков для колонок с центрированным содержимым
                    if (column == 0 || column == 3 || column == 5 || column == 6) {
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                    } else {
                        label.setHorizontalAlignment(SwingConstants.LEFT);
                    }

                    // Стиль заголовка в стиле браузера
                    label.setBackground(new Color(248, 249, 250));
                    label.setForeground(new Color(95, 99, 104));
                    label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    label.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(232, 234, 237)),
                            BorderFactory.createEmptyBorder(12, 12, 12, 12)
                    ));

                    return label;
                }
            });
        }
    }
}