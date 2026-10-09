package com.voloshenko.dialogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QuizTest {

    @Test
    public void correctSelectionPasses() {
        assertTrue(Quiz.isCorrect(Quiz.CORRECT.clone()));
    }

    @Test
    public void emptySelectionFails() {
        assertFalse(Quiz.isCorrect(new boolean[Quiz.ITEMS.length]));
    }

    @Test
    public void missingInstrumentFails() {
        boolean[] selected = Quiz.CORRECT.clone();
        selected[0] = false;
        assertFalse(Quiz.isCorrect(selected));
    }

    @Test
    public void paintingToolFails() {
        boolean[] selected = Quiz.CORRECT.clone();
        selected[1] = true;
        assertFalse(Quiz.isCorrect(selected));
    }

    @Test
    public void nullAndShortSelectionFail() {
        assertFalse(Quiz.isCorrect(null));
        assertFalse(Quiz.isCorrect(new boolean[]{true, true}));
    }

    @Test
    public void meetsAssignmentSize() {
        assertTrue(Quiz.ITEMS.length >= 5);
        int correctCount = 0;
        for (boolean item : Quiz.CORRECT) {
            if (item) {
                correctCount++;
            }
        }
        assertTrue(correctCount >= 2);
        assertEquals(Quiz.ITEMS.length, Quiz.CORRECT.length);
    }
}
