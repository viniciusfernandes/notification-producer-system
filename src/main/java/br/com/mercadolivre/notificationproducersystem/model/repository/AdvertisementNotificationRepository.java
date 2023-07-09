package br.com.mercadolivre.notificationproducersystem.model.repository;

import br.com.mercadolivre.notificationproducersystem.model.AdvertisementNotification;

import java.util.List;

public interface AdvertisementNotificationRepository {
  void save(List<AdvertisementNotification> advertisements);

  List<AdvertisementNotification> findAll();

  void removeAllByCode(List<String> ids);

  int removeAllByCustomerId(String customerId);
}
