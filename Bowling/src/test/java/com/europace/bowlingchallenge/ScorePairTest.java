package com.europace.bowlingchallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScorePairTest {
    static ScorePair scorePairTest;

    @DisplayName("Setup Resource for Tests")
    @BeforeEach
    void setupClass(){
        scorePairTest = new ScorePair();
    }

    @DisplayName("Test total Score")
    @Test
    public void testTotalScore(){
        scorePairTest.setFirstScore(5);
        scorePairTest.setSecondScore(4);
        assertEquals(9, scorePairTest.getTotalScore(), 0);
    }
}
