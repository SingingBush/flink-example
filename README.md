Flink Example
=============

[![Maven](https://github.com/SingingBush/flink-example/actions/workflows/maven.yml/badge.svg)](https://github.com/SingingBush/flink-example/actions/workflows/maven.yml)

Example project to demonstrate how to use Apache Flink for processing a stream of data using a stateful function.

This project was put together to better understand how to make use of [Apache Flink](https://flink.apache.org/) [Amazon Kinesis Data Analytics](https://aws.amazon.com/kinesis/data-analytics/).

The project was generated using the archetype:

```bash
mvn archetype:generate                \
  -DarchetypeGroupId=org.apache.flink   \
  -DarchetypeArtifactId=flink-quickstart-java \
  -DarchetypeVersion=1.14.0
```