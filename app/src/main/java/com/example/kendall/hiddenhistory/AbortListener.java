package com.example.kendall.hiddenhistory;

/**
 * Created by Owner on 4/20/2016.
 */
public class AbortListener implements ScoreResponse {
    public Integer score;

    @Override
    public void processFinish(Integer score) {
        this.score = score;
    }
}
