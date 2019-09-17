package org.gooru.notifications.infra.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import org.gooru.notifications.infra.components.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ashish on 17/4/18.
 */
public class ConsumersDeployer {

  private final JsonNode consumersToDeployArray;
  private final JsonNode consumersConfigForDeployment;
  private DeployableRegistry deployableRegistry;
  private final List<ConsumerTemplate> deployments = new ArrayList<>();
  private ExecutorService executorService;

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumersDeployer.class);

  public ConsumersDeployer(JsonNode consumersToDeployArray, JsonNode consumersConfigForDeployment) {
    this.consumersToDeployArray = consumersToDeployArray;
    this.consumersConfigForDeployment = consumersConfigForDeployment;
  }

  public void deploy() {
    if (!AppConfiguration.getInstance().isApiOnlyMode()) {
      LOGGER.info("Working in non API mode, will deploy Kafka consumers");
      initializeDeploymentRegistry();

      initializeDeployments();

      doDeploy();

      setupShutdown();
    } else {
      LOGGER.info("Working in API mode only, will not deploy Kafka consumers");
    }
  }

  private void setupShutdown() {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      for (ConsumerTemplate consumer : deployments) {
        consumer.shutdown();
      }
      executorService.shutdown();
      try {
        executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        LOGGER.warn("Exception awaiting termination of executor service", e);
      }
    }));
  }

  private void doDeploy() {
    executorService = Executors.newFixedThreadPool(deployments.size());
    for (ConsumerTemplate consumerTemplate : deployments) {
      LOGGER.info("Deploying '{}'", consumerTemplate.getDeploymentName());
      consumerTemplate.init();
      executorService.submit(consumerTemplate);
    }
  }

  private void initializeDeployments() {
    for (Iterator<Map.Entry<String, JsonNode>> it = consumersConfigForDeployment.fields(); it
        .hasNext();) {
      Map.Entry<String, JsonNode> property = it.next();

      List<ConsumerTemplate> templates = ConsumerFactory.manufacture(property.getKey(),
          KafkaConsumerConfig.build(property.getValue()));

      if (deployableRegistry.canDeploy(templates.get(0))) {
        LOGGER.info("Will deploy: '{}' with '{}' instances", property.getKey(), templates.size());
        deployments.addAll(templates);
      } else {
        LOGGER.info("Won't deploy '{}'", property.getKey());
      }
    }
  }

  private void initializeDeploymentRegistry() {
    int countOfDistinctConsumersToDeploy = getCountOfDistinctConsumersToDeploy();
    List<String> deploymentCandidates = new ArrayList<>();
    for (int index = 0; index < countOfDistinctConsumersToDeploy; index++) {
      deploymentCandidates.add(consumersToDeployArray.get(index).textValue());
    }
    deployableRegistry = DeployableRegistry.build(deploymentCandidates);
  }

  private int getCountOfDistinctConsumersToDeploy() {
    final int countOfDistinctConsumersToDeploy = consumersToDeployArray.size();
    if (countOfDistinctConsumersToDeploy <= 0) {
      throw new IllegalStateException("No consumers to deploy");
    }
    LOGGER.debug("Total number of consumers to deploy: {}", countOfDistinctConsumersToDeploy);
    return countOfDistinctConsumersToDeploy;
  }
}
