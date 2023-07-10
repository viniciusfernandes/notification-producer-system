package br.com.mercadolivre.notificationsystem.model.repository;

import br.com.mercadolivre.notificationsystem.model.AdvertisementNotification;

import java.util.List;

public interface AdvertisementNotificationRepository {
  void save(List<AdvertisementNotification> advertisements);

  List<AdvertisementNotification> findAll();

  void removeAllByCode(List<String> ids);

  int removeAllByCustomerId(String customerId);
}
