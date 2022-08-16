package com.europace.bowlingchallenge;

public class FrameNormal extends Frame{
    FrameNormal(){
        super();
    }

    @Override
    public Integer getTotalScore(){
        return totalScore;
    }

    @Override
    public Integer getTotalScoreAfterStrike(){return totalScore;}
}
