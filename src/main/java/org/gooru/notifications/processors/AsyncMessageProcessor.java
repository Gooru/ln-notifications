package org.gooru.notifications.processors;

import io.vertx.core.Future;
import org.gooru.notifications.responses.MessageResponse;

/**
 * @author ashish.
 */
public interface AsyncMessageProcessor {

  Future<MessageResponse> process();

}
