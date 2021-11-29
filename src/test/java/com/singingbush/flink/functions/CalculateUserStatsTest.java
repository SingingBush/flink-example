package com.singingbush.flink.functions;

import com.singingbush.flink.model.UserScore;
import com.singingbush.flink.model.UserStats;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.util.KeyedOneInputStreamOperatorTestHarness;
import org.apache.flink.streaming.util.ProcessFunctionTestHarnesses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateUserStatsTest {

    private KeyedOneInputStreamOperatorTestHarness<String, UserScore, UserStats> testHarness;

    @BeforeEach
    public void setupTestHarness() throws Exception {
        final CalculateUserStats calculateUserStats = new CalculateUserStats();

        this.testHarness = ProcessFunctionTestHarnesses.forKeyedProcessFunction(
                calculateUserStats,
                (KeySelector<UserScore, String>) UserScore::getName,
                Types.STRING
        );

        testHarness.open();
    }

    @Test
    void userFirstScoreShouldCreateInitialStats() throws Exception {
        final UserScore userScore = new UserScore("test user", 1_000, Instant.now());

        testHarness.processElement(userScore, Instant.now().getEpochSecond());

        final UserStats userStats = new UserStats(userScore.getName(), 1, userScore, userScore.getScore());

        assertEquals(Collections.singletonList(userStats), testHarness.extractOutputValues());
    }

    @Test
    void consecutiveUserScoresShouldUpdateExistingStats() throws Exception {
        final Instant now = Instant.now();

        final UserScore score1 = new UserScore("test user", 400, now.minus(10, ChronoUnit.SECONDS));
        final UserScore score2 = new UserScore("test user", 200, now.minus(8, ChronoUnit.SECONDS));
        final UserScore score3 = new UserScore("test user", 800, now.minus(6, ChronoUnit.SECONDS));
        final UserScore score4 = new UserScore("test user", 600, now.minus(4, ChronoUnit.SECONDS));

        testHarness.processElement(score1, now.getEpochSecond());
        testHarness.processElement(score2, now.getEpochSecond());
        testHarness.processElement(score3, now.getEpochSecond());
        testHarness.processElement(score4, now.getEpochSecond());

        final UserStats stats1 = new UserStats("test user", 1, score1, 400L);
        final UserStats stats2 = new UserStats("test user", 2, score1, 300L);
        final UserStats stats3 = new UserStats("test user", 3, score3, 466L);
        final UserStats stats4 = new UserStats("test user", 4, score3, 500L);

        final List<UserStats> expectedOutput = Arrays.asList(stats1, stats2, stats3, stats4);

        assertEquals(expectedOutput, testHarness.extractOutputValues());
    }
}