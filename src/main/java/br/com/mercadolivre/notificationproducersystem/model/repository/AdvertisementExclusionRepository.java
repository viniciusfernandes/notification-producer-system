package br.com.mercadolivre.notificationproducersystem.model.repository;

import br.com.mercadolivre.notificationproducersystem.model.AdvertisementExclusion;

import java.util.List;

public interface AdvertisementExclusionRepository {
  void save(AdvertisementExclusion advertisementExclusion);

  int deleteById(String id);

  AdvertisementExclusion findById(String id);

  Boolean isCustomerExcluded(String id);

  List<String> findAllExcludedIdCustomers();

}
