package br.com.mercadolivre.notificationproducersystem.repository;

import br.com.mercadolivre.notificationproducersystem.model.AdvertisementNotification;
import br.com.mercadolivre.notificationproducersystem.model.repository.AdvertisementNotificationRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

public class AdvertisementMongoRepository implements AdvertisementNotificationRepository {

  private final MongoTemplate mongoTemplate;

  public AdvertisementMongoRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void save(List<AdvertisementNotification> advertisements) {
    for (var advert : advertisements) {
      mongoTemplate.save(advert);
    }
  }

  @Override
  public List<AdvertisementNotification> findAll() {
    return mongoTemplate.findAll(AdvertisementNotification.class);
  }

  @Override
  public void removeAllByCode(List<String> ids) {
    ids.forEach(id -> {
      var advertisement = AdvertisementNotification.builder().code(id).build();
      mongoTemplate.remove(advertisement);
    });
  }

  @Override
  public int removeAllByCustomerId(String customerId) {
    return 0;
  }
}
