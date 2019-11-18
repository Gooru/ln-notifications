package org.gooru.notifications.infra.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Properties;

/**
 * @author ashish on 17/4/18.
 */
public interface KafkaConsumerConfig {

  int getInstances();

  List<String> getTopics();

  Properties getProperties();

  static KafkaConsumerConfig build(JsonNode config) {
    return new KafkaConsumerConfigImpl(config);
  }

}
