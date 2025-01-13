package org.example;

import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.exporter.pushgateway.PushGateway;

import java.util.Random;

public class Main {

    private static final PushGateway pushGateway = PushGateway.builder()
            .address("localhost:9091") // not needed as localhost:9091 is the default
            .job("example")
            .build();

    private static final Counter counter = Counter.builder()
            .name("lkzc19")
            .help("example counter")
            .labelNames("status")
            .register();

    static {
        counter.initLabelValues("ok");
        counter.initLabelValues("fail");
    }

    public static void main(String[] args) throws Exception {
        push();
        try {
            counter.labelValues("ok").inc(195);
            counter.labelValues("fail").inc(458);
        } finally {
            pushGateway.push();
        }
    }

    private static void push() throws Exception {
        while (true) {
            counter.labelValues("ok").inc(new Random().nextInt(100));
            counter.labelValues("fail").inc(new Random().nextInt(100));
            pushGateway.push();
        }
    }
}