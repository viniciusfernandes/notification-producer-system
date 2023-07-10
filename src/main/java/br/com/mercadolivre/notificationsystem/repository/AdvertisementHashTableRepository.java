package br.com.mercadolivre.notificationsystem.repository;

import br.com.mercadolivre.notificationsystem.model.AdvertisementNotification;
import br.com.mercadolivre.notificationsystem.model.repository.AdvertisementNotificationRepository;

import java.util.*;

public class AdvertisementHashTableRepository implements AdvertisementNotificationRepository {
  private final Map<String, AdvertisementNotification> table = new HashMap<>();

  @Override
  public void save(List<AdvertisementNotification> advertisements) {
    for (var advert : advertisements) {
      table.put(advert.getCode(), advert);
    }
  }

  @Override
  public List<AdvertisementNotification> findAll() {
    if (table.isEmpty()) {
      return Collections.emptyList();
    }
    return new ArrayList<>(table.values());
  }

  @Override
  public void removeAllByCode(List<String> ids) {
    ids.forEach(id -> table.remove(id));
  }

  @Override
  public int removeAllByCustomerId(String customerId) {
    var advertisements = table.values();
    int total = 0;
    for (var advertisement : advertisements) {
      if (advertisement.getUserId().equals(customerId) && table.remove(advertisement.getCode()) != null) {
        total++;
      }
    }
    return total;
  }
}
