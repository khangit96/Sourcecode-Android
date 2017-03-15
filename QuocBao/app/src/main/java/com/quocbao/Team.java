package com.quocbao;

/**
 * Created by Administrator on 3/8/2017.
 */

public class Team implements Comparable<Team> {
    public Team() {
    }
    public Team(String logo, String name, int point, String province) {
        this.logo = logo;
        this.name = name;
        this.point = point;
        this.province = province;
    }

    public String logo;
    public String name;
    public int point;
    public String province;
    public int pos;


    @Override
    public int compareTo(Team team) {
        return (int) (this.point - team.point);
    }
}
