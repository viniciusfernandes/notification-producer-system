package br.com.mercadolivre.notificationsystem.config;

import br.com.mercadolivre.notificationsystem.model.repository.AdvertisementExclusionRepository;
import br.com.mercadolivre.notificationsystem.model.repository.AdvertisementNotificationRepository;
import br.com.mercadolivre.notificationsystem.repository.AdvertisementExclusionHashTableRepository;
import br.com.mercadolivre.notificationsystem.repository.AdvertisementExclusionMongoRepository;
import br.com.mercadolivre.notificationsystem.repository.AdvertisementHashTableRepository;
import br.com.mercadolivre.notificationsystem.repository.AdvertisementMongoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
  @Value("${database.strategy}")
  private String database;

  @Bean
  //public AdvertisementExclusionRepository advertisementExclusionRepository(MongoTemplate mongoTemplate) {
  public AdvertisementExclusionRepository advertisementExclusionRepository() {
    if (database.equals("MONGODB")) {
      return new AdvertisementExclusionMongoRepository(null);
    }
    return new AdvertisementExclusionHashTableRepository();
  }

  @Bean
  //public AdvertisementRepository advertisementRepository(MongoTemplate mongoTemplate) {
  public AdvertisementNotificationRepository advertisementRepository() {
    if (database.equals("MONGODB")) {
      return new AdvertisementMongoRepository(null);
    }
    return new AdvertisementHashTableRepository();
  }
}
