package org.gooru.notifications.writers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.gooru.notifications.infra.kafka.ConsumerTemplate;
import org.gooru.notifications.infra.kafka.KafkaConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

public class NotificationsConsumer extends ConsumerTemplate<String, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationsConsumer.class);

    public NotificationsConsumer(int id, KafkaConsumerConfig kafkaConsumerConfig) {
        super(id, kafkaConsumerConfig);
    }

    @Override
    public void init() {
        super.initialize(new StringDeserializer(), new StringDeserializer());
    }

    @Override
    public void processingRecordExceptionHandler(ConsumerRecord record, Exception e) {
        LOGGER.warn("Failure in handling message for topic '{}' offset '{}' partition '{}", record.topic(),
            record.offset(), record.partition(), e);
    }

    @Override
    public void commitExceptionHandler(Exception e) {
        LOGGER.warn("Failed to do commit.", e);
    }

    @Override
    public void processRecord(ConsumerRecord<String, String> record) {
        // TODO: Provide implementation
        LOGGER.debug("Processing record on topic: '{}", record.topic());
        LOGGER.debug("Key : '{}' === Value: '{}'", record.key(), record.value());
        LOGGER.debug("Done processing.");
    }

    @Override
    public String getDeploymentName() {
        return "org.gooru.notifications.writers.NotificationsConsumer";
    }
}
