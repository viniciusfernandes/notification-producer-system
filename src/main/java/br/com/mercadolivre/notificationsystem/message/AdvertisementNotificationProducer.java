package br.com.mercadolivre.notificationsystem.message;

import br.com.mercadolivre.notificationsystem.message.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AdvertisementNotificationProducer {
  @Autowired
  private KafkaTemplate<String, NotificationDto> notificationKafkaTemplate;
  @Value(value = "${kafka.topic.notification}")
  private String topicName;

  public List<String> sendMessage(List<NotificationDto> notifications) {
    var processedNotifications = new ArrayList<String>();
    for (var notification : notifications) {
      try {
        var key = notification.getCode() + ":" + notification.getUserId();
        notificationKafkaTemplate.send(topicName, key, notification);
        processedNotifications.add(notification.getCode());
      } catch (Exception e) {
        var message = String.format("Failure on checking if the user=%s was excluded from the notifications",
            notification.getUserId());
        log.error(message, e);
      }
    }
    return processedNotifications;
  }

}
