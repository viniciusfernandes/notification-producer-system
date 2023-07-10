package br.com.mercadolivre.notificationsystem.task;

import br.com.mercadolivre.notificationsystem.mapper.NotificationMapper;
import br.com.mercadolivre.notificationsystem.message.AdvertisementNotificationProducer;
import br.com.mercadolivre.notificationsystem.service.AdvertisementNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdvertisementNotificationTask {
  @Autowired
  private AdvertisementNotificationService advertisementService;
  @Autowired
  private AdvertisementNotificationProducer notificationProducer;

  @Scheduled(cron = "${notification.cron}")
  public void sendAdvertisements() {
    var notifications = NotificationMapper.toDto(advertisementService.findAll());
    var advertisementIds = notificationProducer.sendMessage(notifications);
    advertisementService.removeAllByIds(advertisementIds);
  }
}
