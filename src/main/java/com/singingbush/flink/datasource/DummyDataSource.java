package com.singingbush.flink.datasource;

import com.singingbush.flink.model.UserScore;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class just produces randomised data for testing purposes. In AWS
 * there would be a Kinesis data stream.
 */
public class DummyDataSource implements SourceFunction<UserScore> {

    private static final AtomicBoolean cancelled = new AtomicBoolean(false);
    private static final Random rand = new Random();

    private static final String[] players = new String[] {
            "Steve",
            "Sheila",
            "Sally",
            "Dave",
            "Ben",
            "Olly",
            "Mike"
    };

    @Override
    public void run(SourceContext<UserScore> sourceContext) throws Exception {
        int counter = 0;

        do {
            final String player = players[rand.nextInt(players.length)];

            //final long score= rand.nextLong(35_000L, 100_000L); // needs JDK 17

            final UserScore score = new UserScore(player, rand.nextInt(400), Instant.now());
            sourceContext.collect(score);

            Thread.sleep(100L);

            //log.debug(score.toString());
            counter++;
            if(counter >= 200) {
                this.cancel();
            }
        } while (!cancelled.get());
    }

    @Override
    public void cancel() {
        cancelled.set(true);
    }
}
