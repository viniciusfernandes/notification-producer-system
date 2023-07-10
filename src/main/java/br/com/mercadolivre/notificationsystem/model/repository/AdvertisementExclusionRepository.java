package br.com.mercadolivre.notificationsystem.model.repository;

import br.com.mercadolivre.notificationsystem.model.AdvertisementExclusion;

import java.util.List;

public interface AdvertisementExclusionRepository {
  void save(AdvertisementExclusion advertisementExclusion);

  int deleteById(String id);

  AdvertisementExclusion findById(String id);

  Boolean isCustomerExcluded(String id);

  List<String> findAllExcludedIdCustomers();

}
