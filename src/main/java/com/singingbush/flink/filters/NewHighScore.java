package com.singingbush.flink.filters;

import com.singingbush.flink.model.UserScore;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;

/**
 * Used to filter data stream to only pass on the pojo if the score is a new record for the given user
 */
public class NewHighScore extends RichFilterFunction<UserScore> {

    private ValueState<UserScore> lastScore;

    @Override
    public void open(Configuration parameters) throws Exception {
        lastScore = getRuntimeContext()
                .getState(new ValueStateDescriptor<>("highScore", UserScore.class));
    }

    @Override
    public boolean filter(UserScore userScore) throws Exception {
        if(lastScore.value() == null || userScore.getScore() > lastScore.value().getScore()) {
            lastScore.update(userScore);
            return true;
        } else {
            return false;
        }
    }

}
