package com.example.demoquartz;

public class Test {

    public static void main(String[] args) {
        System.out.println("CREATE TABLE t_student (\n" +
                "      id INT,\n" +
                "      name STRING\n" +
                "    ) WITH (\n" +
                "       'connector' = 'kafka',\n" +
                "       'topic' = 't_student',\n" +
                "       'properties.bootstrap.servers' = '10.194.188.94:9092',\n" +
                "       'properties.group.id' = 'flink-cdc-mysql-kafka',\n" +
                "       'scan.startup.mode' = 'latest-offset',\n" +
                "       'format' = 'json'\n" +
                "    )");

        System.out.println("CREATE TABLE t_student_copy (\n" +
                "           id INT,\n" +
                "           name STRING,\n" +
                "           PRIMARY KEY (id) NOT ENFORCED\n" +
                "    ) WITH (\n" +
                "       'connector' = 'print'\n" +
                "    )");
    }
}
