package com.singingbush.flink.functions;

import com.singingbush.flink.model.UserScore;
import com.singingbush.flink.model.UserStats;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class CalculateUserStats extends KeyedProcessFunction<String, UserScore, UserStats> {

    private ValueState<UserStats> userStats;
    private ValueState<Long> totalPoints;

    @Override
    public void open(Configuration parameters) throws Exception {
        final RuntimeContext ctx = getRuntimeContext();

        userStats = ctx.getState(new ValueStateDescriptor<>("userStats", UserStats.class));
        totalPoints = ctx.getState(new ValueStateDescriptor<>("totalPoints", Types.LONG));
    }

    @Override
    public void processElement(UserScore value, KeyedProcessFunction<String, UserScore, UserStats>.Context ctx, Collector<UserStats> out) throws Exception {
        if(userStats.value() == null) {
            // first record
            final UserStats firstStats = new UserStats(value.getName(), 1, value, value.getScore());
            this.userStats.update(firstStats);
            totalPoints.update(value.getScore());
            out.collect(firstStats);
        } else {
            // update user stats
            final UserStats stats = userStats.value();

            stats.setTimesPlayed(stats.getTimesPlayed() + 1);

            if(value.getScore() > stats.getHighscore().getScore()) {
                stats.setHighscore(value);
            }

            totalPoints.update(totalPoints.value() + value.getScore());
            final long average = totalPoints.value() / stats.getTimesPlayed();

            stats.setAverage(average);

            userStats.update(stats);

            out.collect(stats);
        }

    }
}
