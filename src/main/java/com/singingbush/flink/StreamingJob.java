/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.singingbush.flink;

import com.singingbush.flink.datasource.DummyDataSource;
import com.singingbush.flink.filters.NewHighScore;
import com.singingbush.flink.functions.CalculateUserStats;
import com.singingbush.flink.model.UserScore;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		//final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

		//env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		/*
		 * Here, you can start creating your execution plan for Flink.
		 *
		 * Start with getting some data from the environment, like
		 * 	env.readTextFile(textPath);
		 */
//		env.fromElements(
//				new UserScore(),
//				new UserScore(),
//				new UserScore(),
//				new UserScore()
//		);
		final DataStreamSource<UserScore> dataSource = env.addSource(new DummyDataSource());

		/* then, transform the resulting DataStream<String> using operations
		 * like
		 * 	.filter()
		 * 	.flatMap()
		 * 	.join()
		 * 	.coGroup()
		 *
		 * and many more.
		 * Have a look at the programming guide for the Java API:
		 *
		 * https://flink.apache.org/docs/latest/apis/streaming/index.html
		 *
		 */
		dataSource
				.keyBy((KeySelector<UserScore, String>) UserScore::getName)
				.process(new CalculateUserStats())
				.print();

//		dataSource
//				.keyBy((KeySelector<UserScore, String>) UserScore::getName)
//				//.window(TumblingProcessingTimeWindows.of(Time.seconds(6)))
//				.filter(new NewHighScore())
//				.keyBy((KeySelector<UserScore, String>) UserScore::getName)
//				.max("score")
//				.print() // same as .addSink(new PrintSinkFunction<>())
//				;

		// execute program
		env.execute("Flink Streaming Java API Skeleton");
	}
}
