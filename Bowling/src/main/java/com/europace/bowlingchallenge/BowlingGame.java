package com.europace.bowlingchallenge;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BowlingGame {
    private static final String welcomeStr =
            "Hello And welcome to EUROPACE bowling - press any key to play !";
    private static final Integer totalFrames = 10;
    private static final Integer bound = 11;
    private Integer frameCounter = 0;
    private ArrayList<Frame> frameArray;
    private Random randGen;

    BowlingGame(){
        randGen = new Random(System.currentTimeMillis());
        frameArray = new ArrayList<>();
        while(getFrameArray().size()<9){
            getFrameArray().add(new FrameNormal());
        }
        getFrameArray().add(new FrameLast());
    }

    public static Integer getTotalFrames() {
        return totalFrames;
    }

    public void addFrame(){
        FrameNormal currentFrameNormal = new FrameNormal();
        getFrameArray().add(currentFrameNormal);
    }

    public void roll(int score) throws NullPointerException{
        System.out.println("Threw a " + score);
        frameArray.get(getFrameCounter()).addScore(score);
    }

    public void printFrames() throws NullPointerException{
        for(int i = 0; i < getFrameArray().size(); ++i){
            switch (i){
                case 9 -> System.out.print(" "+ getFrameArray().get(i).getFrameScores());
                case 10 -> System.out.print("|"+ getFrameArray().get(i).getFrameScores());
                default -> System.out.print(getFrameArray().get(i).getFrameScores() + "\t\t");
            }
        }System.out.print("\n");
        for(int i = 0; i < getFrameArray().size(); ++i){
                System.out.print(getFrameArray().get(i).getTotalScore() + "\t\t");
        }System.out.print("\n");
    }

    public Frame getCurrentFrame() throws NullPointerException{
        if(!getFrameArray().isEmpty()){
            return getFrameArray().get(getFrameCounter());
        }
        return new FrameNormal();
    }

    public void computeFrameScores() throws IndexOutOfBoundsException,NullPointerException{
        for(int i = 0; i < frameArray.size(); ++i){
            if (i+1 < frameArray.size() && frameArray.get(i).getScoreState().equals(ScoreState.SPARE)
                    && Boolean.TRUE.equals(frameArray.get(i+1).isFirstScoreSet())) {
                        frameArray.get(i).addBonus(frameArray.get(i + 1).getFirstScore());
                } else if(frameArray.get(i).getScoreState().equals(ScoreState.STRIKE)){
                    if(i+1 < frameArray.size() && Boolean.TRUE.equals(frameArray.get(i+1).isComplete())
                            && frameArray.get(i).getNumBoniAdded()<1){
                        if(i+2 ==frameArray.size() && frameArray.get(i+1).getScoreState().equals(ScoreState.STRIKE)){
                            frameArray.get(i).addBonus(frameArray.get(i+1).getTotalScoreAfterStrike());
                        }else{
                            frameArray.get(i).addBonus(frameArray.get(i+1).getTotalScore());
                        }
                    }else if(i+2< frameArray.size() && Boolean.TRUE.equals(frameArray.get(i+2).isComplete())
                            && frameArray.get(i).getNumBoniAdded()<2){
                        frameArray.get(i).addBonus(frameArray.get(i+2).getFirstScore());
                    }
            }
        }
    }

    public void computeSuccessiveScores(){
        for(int i = 0; i < getTotalFrames(); ++i){
            if (i > 0 && getFrameArray().get(i).isComplete() && !getFrameArray().get(i).isPreviousFrameScoreAdded()){
                if(frameArray.get(i).getScoreState().equals(ScoreState.STRIKE) && frameArray.get(i).isBonusComplete()){
                    getFrameArray().get(i).sumPreviousScore(getFrameArray().get(i-1).getTotalScore());
                    getFrameArray().get(i).setPreviousFrameScoreAdded(true);
                }else if(!frameArray.get(i).getScoreState().equals(ScoreState.STRIKE)){
                    getFrameArray().get(i).sumPreviousScore(getFrameArray().get(i-1).getTotalScore());
                    getFrameArray().get(i).setPreviousFrameScoreAdded(true);
                }
            }
        }
    }

    public Integer getTotalGameScore() throws NullPointerException{
        return frameArray.get(9).getTotalScore();
    }

    public void computeStikeBonusStates(){
        for(int i = 0; i< frameArray.size(); ++i){
            if(frameArray.get(i).getScoreState().equals(ScoreState.STRIKE) && i +1 < frameArray.size()){
                switch (frameArray.get(i).getScoreState()){
                    case NORMAL, SPARE -> {
                        if(frameArray.get(i).getNumBoniAdded() == 1){
                            frameArray.get(i).setBonusComplete(true);
                        }
                    }
                    case STRIKE -> {
                        if(frameArray.get(i).getNumBoniAdded() == 2){
                            frameArray.get(i).setBonusComplete(true);
                        }else if(i+2 == frameArray.size() && frameArray.get(i+1).getScoreState().equals(ScoreState.STRIKE)
                                && frameArray.get(i).getNumBoniAdded()>0){
                            frameArray.get(i).setBonusComplete(true);
                        }
                    }
                }
            }
        }
    }

    public void playScore(Integer score){
        roll(score);
        computeFrameScores(); // add Bonus from previous scores, why 40 in Frame 8
        computeStikeBonusStates();
        computeSuccessiveScores();
        printFrames();
    }

    public void play() throws InputMismatchException, NullPointerException{
        System.out.print(welcomeStr);
        while(getFrameCounter() < getTotalFrames()){
            while (Boolean.FALSE.equals(getFrameArray().get(getFrameCounter()).isComplete())){
                Scanner scanner = new Scanner(System.in);
                if(scanner.hasNext()){
                    playScore(randGen.nextInt(bound));
                }
            }
            setFrameCounter(getFrameCounter() + 1);
        }
        System.out.println("GAME OVER (please hire me)");
    }

    public Integer getFrameCounter() {
        return frameCounter;
    }

    public ArrayList<Frame> getFrameArray() {
        return frameArray;
    }

    public void setFrameCounter(Integer frameCounter) {
        this.frameCounter = frameCounter;
    }
}