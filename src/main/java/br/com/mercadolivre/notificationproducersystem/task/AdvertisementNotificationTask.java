package br.com.mercadolivre.notificationproducersystem.task;

import br.com.mercadolivre.notificationproducersystem.mapper.NotificationMapper;
import br.com.mercadolivre.notificationproducersystem.message.AdvertisementNotificationProducer;
import br.com.mercadolivre.notificationproducersystem.service.AdvertisementNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
