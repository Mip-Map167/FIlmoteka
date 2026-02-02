package main.java.filmoteka.model;

public enum Genre {
    SCI_FI("Научная фантастика"),
    DRAMA("Драма"),
    CRIME("Криминал"),
    ACTION("Боевик"),
    FANTASY("Фэнтези"),
    WESTERN("Вестерн"),
    ROMANCE("Мелодрама"),
    ADVENTURE("Приключения"),
    COMEDY("Комедия"),
    HORROR("Ужасы"),
    THRILLER("Триллер"),
    ANIMATION("Анимация"),
    DOCUMENTARY("Документальный"),
    MUSICAL("Мюзикл"),
    BIOGRAPHY("Биография"),
    HISTORY("Исторический"),
    FAMILY("Семейный"),
    WAR("Военный"),
    MYSTERY("Детектив"),
    SPORT("Спорт");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}