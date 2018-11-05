package bdcsc.auto.kafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 解析获取到的kafka的消息
 * Created by mawenrui on 2018/7/16.
 */
public class OutboundConsumer implements Runnable {
    private static Logger LOGGER = LoggerFactory.getLogger(OutboundConsumer.class);

    private KafkaStream<byte[], byte[]> m_stream;

    public OutboundConsumer(KafkaStream<byte[], byte[]> a_stream) {
        m_stream = a_stream;
    }

    @Override
    public void run() {
        LOGGER.info("这里进到了run方法");
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        LOGGER.info("获取数据的长度为：" + it.length());
        while (it.hasNext()) {
            try {
                byte[] message = it.next().message();
                if (message != null) {
                    LOGGER.info("kafka msg:{}", Arrays.toString(message));
                }
            } catch (Exception e) {
                LOGGER.error("kafka_fkjz_consumer_error:{}", e);
                e.printStackTrace();
            }
        }
    }
}
