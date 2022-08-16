package com.europace.bowlingchallenge;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FrameTest {
    static FrameNormal testFrameNormal;

    @DisplayName("Setup Resource for Tests")
    @BeforeEach
    void setupClass(){
        testFrameNormal = new FrameNormal();
    }

    @DisplayName("Test values for Normal Frame set")
    @Test
    void testValuesSet(){
        testFrameNormal.addScore(4);
        testFrameNormal.addScore(5);
        assertEquals(4, testFrameNormal.getFirstScore());
        assertEquals(5, testFrameNormal.getSecondScore());
    }

    @DisplayName("Test values for Normal Frame set")
    @Test
    void testSecondValueClearingLeftovers(){
        testFrameNormal.addScore(4);
        testFrameNormal.addScore(10);
        assertEquals(4, testFrameNormal.getFirstScore());
        assertEquals(6, testFrameNormal.getSecondScore());
    }

    @DisplayName("Test more than two values possible and original values unchanged")
    @Test
    void testMoreThanTwoAdditions(){
        testFrameNormal.addScore(4);
        testFrameNormal.addScore(10);
        testFrameNormal.addScore(3);
        testFrameNormal.addScore(5);
        assertEquals(4, testFrameNormal.getFirstScore());
        assertEquals(6, testFrameNormal.getSecondScore());
    }

    @DisplayName("Test second value set and set to zero after strike")
    @Test
    void testSecondValueAfterStrike(){
        testFrameNormal.addScore(10);
        assertEquals(0, testFrameNormal.getSecondScore());
        assertTrue(testFrameNormal.getScorePair().areBothScoresSet());
    }

    @DisplayName("Normal State Test")
    @Test
    void testFrameStateNormal(){
        testFrameNormal.addScore(4);
        testFrameNormal.addScore(5);
        assertEquals(ScoreState.NORMAL, testFrameNormal.getScoreState());
    }

    @DisplayName("Spare State Test")
    @Test
    void testFrameStateSpare(){
        testFrameNormal.addScore(5);
        testFrameNormal.addScore(5);
        assertEquals(ScoreState.SPARE, testFrameNormal.getScoreState());
    }

    @DisplayName("Strike State Test")
    @Test
    void testFrameStateStrike(){
        testFrameNormal.addScore(10);
        assertEquals(ScoreState.STRIKE, testFrameNormal.getScoreState());
    }

    @DisplayName("Normal Frame Printout Test")
    @Test
    void testFramePrintsNormal(){
        testFrameNormal.addScore(5);
        testFrameNormal.addScore(4);
        assertEquals("5|4", testFrameNormal.getFrameScores());
    }

    @DisplayName("Strike Printout Test")
    @Test
    void testFramePrintsStrike(){
        testFrameNormal.addScore(10);
        assertEquals("X|", testFrameNormal.getFrameScores());
    }

    @DisplayName("Spare Printout Test")
    @Test
    void testFramePrintsSpare(){
        testFrameNormal.addScore(5);
        testFrameNormal.addScore(5);
        assertEquals("5|/", testFrameNormal.getFrameScores());
    }

    @DisplayName("Spare Printout Test")
    @Test
    void testAddingFrameBonusStrike(){
        testFrameNormal.addScore(10);
        testFrameNormal.addBonus(10);
        testFrameNormal.addBonus(10);
        assertEquals(30, testFrameNormal.getTotalScore());
        assertEquals(2, testFrameNormal.getNumBoniAdded());
    }
}
