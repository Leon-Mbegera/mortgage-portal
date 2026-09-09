package com.mortgageportal.event;

import com.mortgageportal.dto.event.ApplicationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventPublisher {

  private static final String TOPIC = "application-events";

  private final KafkaTemplate<String, ApplicationEvent> kafkaTemplate;

  public ApplicationEventPublisher(KafkaTemplate<String, ApplicationEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publish(ApplicationEvent event) {
    kafkaTemplate.send(TOPIC, event.applicationId().toString(), event);
  }
}
