package com.europace.bowlingchallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FrameLastTest {
    static FrameLast testFrameLast;
    @DisplayName("Setup Resource for Tests")
    @BeforeEach
    void setupClass(){
        testFrameLast = new FrameLast();
    }

    @DisplayName("Test values for Normal Frame set")
    @Test
    void testValuesSet(){
        testFrameLast.addScore(4);
        testFrameLast.addScore(5);
        assertEquals(4, testFrameLast.getFirstScore());
        assertEquals(5, testFrameLast.getSecondScore());
    }

    @DisplayName("Test values for Normal Frame set")
    @Test
    void testSecondValueClearingLeftovers(){
        testFrameLast.addScore(4);
        testFrameLast.addScore(10);
        assertEquals(4, testFrameLast.getFirstScore());
        assertEquals(6, testFrameLast.getSecondScore());
    }
    @DisplayName("Test second value set and set to zero after strike")
    @Test
    void testSecondValueAfterStrike(){
        testFrameLast.addScore(10);
        assertEquals(0, testFrameLast.getSecondScore());
        assertTrue(testFrameLast.getScorePair().areBothScoresSet());
    }

    @DisplayName("Normal State Test")
    @Test
    void testFrameStateNormal(){
        testFrameLast.addScore(4);
        testFrameLast.addScore(5);
        assertEquals(ScoreState.NORMAL, testFrameLast.getScoreState());
    }

    @DisplayName("Spare State Test")
    @Test
    void testFrameStateSpare(){
        testFrameLast.addScore(5);
        testFrameLast.addScore(5);
        assertEquals(ScoreState.SPARE, testFrameLast.getScoreState());
    }

    @DisplayName("Strike State Test")
    @Test
    void testFrameStateStrike(){
        testFrameLast.addScore(10);
        assertEquals(ScoreState.STRIKE, testFrameLast.getScoreState());
    }

    @DisplayName("Normal Frame Printout Test")
    @Test
    void testFramePrintsNormal(){
        testFrameLast.addScore(5);
        testFrameLast.addScore(4);
        assertEquals("5|4", testFrameLast.getFrameScores());
    }

    @DisplayName("Strike Printout Test")
    @Test
    void testFramePrintsStrike(){
        testFrameLast.addScore(10);
        assertEquals("X|0|0", testFrameLast.getFrameScores());
    }

    @DisplayName("Spare Printout Test")
    @Test
    void testFramePrintsSpare(){
        testFrameLast.addScore(5);
        testFrameLast.addScore(5);
        assertEquals("5|/|0", testFrameLast.getFrameScores());
    }

    @DisplayName("Test if setting 3 values works for State Normal")
    @Test
    void testThreeValuesAddedNormalState(){
        testFrameLast.addScore(5);
        testFrameLast.addScore(2);
        testFrameLast.addScore(1);
        assertEquals(7, testFrameLast.getTotalScore());
    }

    @DisplayName("Test if setting 3 values adds up to the correct score for a Spare")
    @Test
    void testThreeValuesAddedSpare(){
        testFrameLast.addScore(5);
        testFrameLast.addScore(5);
        testFrameLast.addScore(1);
        assertEquals(11, testFrameLast.getTotalScore());
    }

    // strike
    @DisplayName("Test if setting 3 values adds up to the correct score for a Strike")
    @Test
    void testThreeValuesAddedStrike(){
        testFrameLast.addScore(10);
        testFrameLast.addScore(5);
        testFrameLast.addScore(2);
        assertEquals(17, testFrameLast.getTotalScore());
    }
}
