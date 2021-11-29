package com.singingbush.flink.model;

import java.util.Objects;

public class UserStats {

    private String name;
    private int timesPlayed;
    private UserScore highscore;
    private Long average;


    @SuppressWarnings("unused")
    public UserStats() {}

    public UserStats(String name) {
        this(name, 0, null, null);
    }

    public UserStats(String name, int timesPlayed, UserScore highscore, Long average) {
        this.name = name;
        this.timesPlayed = timesPlayed;
        this.highscore = highscore;
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public UserScore getHighscore() {
        return highscore;
    }

    public void setHighscore(UserScore highscore) {
        this.highscore = highscore;
    }

    public Long getAverage() {
        return average;
    }

    public void setAverage(Long average) {
        this.average = average;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStats userStats = (UserStats) o;
        return timesPlayed == userStats.timesPlayed && Objects.equals(name, userStats.name) && Objects.equals(highscore, userStats.highscore) && Objects.equals(average, userStats.average);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, timesPlayed, highscore, average);
    }

    @Override
    public String toString() {
        return "UserStats{" +
                "name='" + name + '\'' +
                ", timesPlayed=" + timesPlayed +
                ", highscore=" + highscore +
                ", average=" + average +
                '}';
    }
}
