package com.singingbush.flink.model;

import java.time.Instant;
import java.util.Objects;

public class UserScore {

    private String name;
    private long score;
    private Instant timestamp;

    @SuppressWarnings("unused")
    public UserScore() {}

    public UserScore(String name) {
        this(name, 0L, Instant.now());
    }

    public UserScore(String name, long score, Instant timestamp) {
        this.name = name;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserScore userScore = (UserScore) o;
        return score == userScore.score && Objects.equals(name, userScore.name) && Objects.equals(timestamp, userScore.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, timestamp);
    }

    @Override
    public String toString() {
        return "UserScore{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", timestamp=" + timestamp +
                '}';
    }
}
