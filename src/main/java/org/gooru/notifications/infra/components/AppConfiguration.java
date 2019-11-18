package org.gooru.notifications.infra.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * @author ashish.
 */
public final class AppConfiguration implements Initializer {
  private static final String APP_CONFIG_KEY = "app.configuration";
  private static final String LIMIT_MAX = "limit.max";
  private static final String LIMIT_DEFAULT = "limit.default";
  private static final String CONSUMERS_CONFIG = "consumers.config";
  private static final String CONSUMERS_DEPLOY = "consumers.deploy";
  private static final String PATHID_HACK_ENABLED = "pathid.hack.enabled";
  private static final String API_MODE = "api.mode";
  private JsonObject configuration;
  private JsonNode globalConfiguration;
  private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

  public static AppConfiguration getInstance() {
    return Holder.INSTANCE;
  }

  private volatile boolean initialized = false;

  private AppConfiguration() {}

  @Override
  public void initializeComponent(Vertx vertx, JsonObject config) {
    if (!initialized) {
      synchronized (Holder.INSTANCE) {
        if (!initialized) {
          try {
            globalConfiguration = new ObjectMapper().readTree(config.toString());
          } catch (IOException e) {
            LOGGER.warn("Not able to store global configuration");
            throw new IllegalArgumentException("Not able to parse global configuration");
          }
          JsonObject appConfiguration = config.getJsonObject(APP_CONFIG_KEY);
          if (appConfiguration == null || appConfiguration.isEmpty()) {
            LOGGER.warn("App configuration is not available");
          } else {
            configuration = appConfiguration.copy();
            initialized = true;
          }
        }
      }
    }
  }

  public boolean isApiOnlyMode() {
    return configuration.getBoolean(API_MODE, false);
  }

  public boolean isPathIdHackEnabled() {
    return configuration.getBoolean(PATHID_HACK_ENABLED, false);
  }

  public int getDefaultLimit() {
    return configuration.getInteger(LIMIT_DEFAULT);
  }

  public int getMaxLimit() {
    return configuration.getInteger(LIMIT_MAX);
  }

  public int getConfigAsInt(String key) {
    return configuration.getInteger(key);
  }

  public boolean getConfigAsBoolean(String key) {
    return configuration.getBoolean(key);
  }

  public String getConfigAsString(String key) {
    return configuration.getString(key);
  }

  public Object getConfigAsRawObject(String key) {
    return configuration.getValue(key);
  }

  public JsonNode fetchConsumersToDeploy() {
    return globalConfiguration.get(CONSUMERS_DEPLOY);
  }

  public JsonNode fetchConsumerConfigForDeployment() {
    return globalConfiguration.get(CONSUMERS_CONFIG);
  }

  private static final class Holder {
    private static final AppConfiguration INSTANCE = new AppConfiguration();
  }

}
