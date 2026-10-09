package com.voloshenko.dialogs;

/**
 * Тест «Ночь в музее».
 * Верный ответ — отметить только музыкальные инструменты:
 * скрипку, рояль, флейту и виолончель.
 */
public final class Quiz {

    public static final String[] ITEMS = {
            "Скрипка",
            "Мольберт",
            "Рояль",
            "Палитра",
            "Флейта",
            "Кисть",
            "Виолончель"
    };

    /** true — пункт должен быть отмечен. */
    public static final boolean[] CORRECT = {
            true,
            false,
            true,
            false,
            true,
            false,
            true
    };

    private Quiz() {
    }

    public static boolean isCorrect(boolean[] selected) {
        if (selected == null || selected.length != CORRECT.length) {
            return false;
        }
        for (int i = 0; i < CORRECT.length; i++) {
            if (selected[i] != CORRECT[i]) {
                return false;
            }
        }
        return true;
    }
}
