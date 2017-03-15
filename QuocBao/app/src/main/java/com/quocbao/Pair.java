package com.quocbao;

/**
 * Created by Administrator on 3/8/2017.
 */

public class Pair {
    public int resultTeam1 = 0;
    public int resultTeam2 = 0;
    public String round;
    public int team1;
    public int team2;

    public Pair(int resultTeam1, int resultTeam2, int team1, int team2, String round) {
        this.resultTeam1 = resultTeam1;
        this.resultTeam2 = resultTeam2;
        this.team1 = team1;
        this.team2 = team2;
        this.round = round;
    }

    public Pair() {
    }

}
