package br.com.mercadolivre.notificationproducersystem.repository;

import br.com.mercadolivre.notificationproducersystem.model.AdvertisementExclusion;
import br.com.mercadolivre.notificationproducersystem.model.repository.AdvertisementExclusionRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementExclusionMongoRepository implements AdvertisementExclusionRepository {
  private final MongoTemplate mongoTemplate;

  public AdvertisementExclusionMongoRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void save(AdvertisementExclusion advertisementExclusion) {
    mongoTemplate.save(advertisementExclusion);
  }

  @Override
  public int deleteById(String id) {
    return 0;
  }

  @Override
  public AdvertisementExclusion findById(String id) {
    return null;
  }

  @Override
  public Boolean isCustomerExcluded(String id) {
    return true;
  }

  @Override
  public List<String> findAllExcludedIdCustomers() {
    var ids = new ArrayList<String>();
    mongoTemplate.findAll(AdvertisementExclusion.class)
        .forEach(exclusion -> ids.add(exclusion.getCustomerId()));
    return ids;
  }
}
