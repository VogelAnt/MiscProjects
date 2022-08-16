package com.europace.bowlingchallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {
    static BowlingGame gameTest;
    @DisplayName("Setup Resource for Tests")
    @BeforeEach
    void setupClass(){
        gameTest = new BowlingGame();
    }

    @DisplayName("Test one Frame score without spares or strikes")
    @Test
    public void testScoreOneNormalGame(){
        gameTest.addFrame();
        gameTest.roll(5);
        gameTest.roll(4);
        assertEquals(9, gameTest.getFrameArray().get(0).getTotalScore(), 0);
    }

    @DisplayName("Test Score of One Spare Game Frame")
    @Test
    public void testScoreOneSpareGame(){
        gameTest.addFrame();
        gameTest.roll(3);
        gameTest.roll(8);
        assertEquals(10, gameTest.getFrameArray().get(0).getTotalScore(), 0);
    }

    @DisplayName("Test Score of One Strike Game Frame")
    @Test
    public void testScoreOneStrikeGame(){
        gameTest.addFrame();
        gameTest.roll(10);
        assertEquals(10, gameTest.getFrameArray().get(0).getTotalScore(), 0);
    }

    @DisplayName("Test score of random 10 Frame Game without spares or strikes")
    @Test
    public void testScoreNormalGame(){
        Random randGen = new Random(System.currentTimeMillis());
        Integer bound = 9;
        Integer totalScore = 0;
        while(gameTest.getFrameCounter() < gameTest.getTotalFrames()){
            while (!gameTest.getFrameArray().get(gameTest.getFrameCounter()).isComplete()){
                Integer tempValue;
                if(gameTest.getCurrentFrame().isFirstScoreSet()){
                    tempValue = randGen.nextInt(bound-gameTest.getCurrentFrame().getFirstScore());
                }else{
                    tempValue = randGen.nextInt(bound);
                }
                gameTest.playScore(tempValue);
                totalScore += tempValue;
            }
            gameTest.setFrameCounter(gameTest.getFrameCounter()+1);
        }
        assertEquals(totalScore, gameTest.getTotalGameScore(), 0);
    }

    @DisplayName("Tests all spares game score")
    @Test
    public void testScoreAllSpares(){
        Integer totalScoreSparesOnly = 190;
        while(gameTest.getFrameCounter() < gameTest.getTotalFrames()){
            while (!gameTest.getFrameArray().get(gameTest.getFrameCounter()).isComplete()){
                Integer tempValue;
                if(gameTest.getCurrentFrame().isFirstScoreSet()
                        && !gameTest.getCurrentFrame().getScoreState().equals(ScoreState.SPARE)){
                    tempValue = 1;
                }else{
                    tempValue = 9;
                }
                gameTest.playScore(tempValue);
            }
            gameTest.setFrameCounter(gameTest.getFrameCounter()+1);
        }
        assertEquals(totalScoreSparesOnly, gameTest.getTotalGameScore(), 0);
    }

    @DisplayName("Test all Strikes game score")
    @Test
    public void testAllStrikes() {
        Integer totalScoreStrikesOnly = 300;
        while (gameTest.getFrameCounter() < gameTest.getTotalFrames()) {
            while (!gameTest.getFrameArray().get(gameTest.getFrameCounter()).isComplete()) {
                gameTest.playScore(10);
            }
            gameTest.setFrameCounter(gameTest.getFrameCounter() + 1);
        }
        assertEquals(totalScoreStrikesOnly, gameTest.getTotalGameScore(), 0);
    }
}
