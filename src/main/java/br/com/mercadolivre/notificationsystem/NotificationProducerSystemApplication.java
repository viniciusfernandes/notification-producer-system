package br.com.mercadolivre.notificationsystem;

import br.com.mercadolivre.notificationsystem.model.AdvertisementNotification;
import br.com.mercadolivre.notificationsystem.service.AdvertisementNotificationService;
import br.com.mercadolivre.notificationsystem.task.AdvertisementNotificationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class NotificationProducerSystemApplication implements CommandLineRunner {
  @Autowired
  private AdvertisementNotificationTask notificationTask;
  @Autowired
  private AdvertisementNotificationService notificationService;

  public static void main(String[] args) {
    SpringApplication.run(NotificationProducerSystemApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    var notifications = List.of(AdvertisementNotification.builder()
            .code("14").
            userId("222").
            title("Promocao de Notebook").
            description("Notebook Samsung X221 I8 por R$1299,99").
            channel("WEB").build(),
        AdvertisementNotification.builder()
            .code("11").
            userId("111").
            title("Máquina de Lavar").
            description("Brastem máquina de lavar R$278,99 até o Natal").
            channel("WEB").build());
    notificationService.save(notifications);
    notificationTask.sendAdvertisements();
  }
}
