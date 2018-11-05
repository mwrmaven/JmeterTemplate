package bdcsc.auto.kafka;


import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * kafka的消费信息的样例
 * Created by mawenrui on 2018/7/16.
 */
public class ConsumerDemo {
    private final ConsumerConnector consumer;
    private final String topic;
    private ExecutorService executor;

    public ConsumerDemo(String a_topic) {
        consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
        this.topic = a_topic;
    }

    public void shutdown() {
        if (consumer != null)
            consumer.shutdown();
        if (executor != null)
            executor.shutdown();
    }

    public void run(int numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
                .createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        for (KafkaStream<byte[], byte[]> stream : streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {

                System.out.println(new String(it.next().message()));
            }
        }
    }

    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "");
        props.put("group.id", "");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("zookeeper.connection.timeout.ms", "10000");
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("rebalance.backoff.ms", "2000");
        props.put("rebalance.max.retries", "10");
        props.put("auto.offset.reset", "smallest");

        return new ConsumerConfig(props);
    }

    public static void main(String[] arg) {

        ConsumerDemo demo = new ConsumerDemo("");
        demo.run(1);

        try {
            Thread.sleep(100000);
        } catch (InterruptedException ie) {

        }
        demo.shutdown();
    }
}
