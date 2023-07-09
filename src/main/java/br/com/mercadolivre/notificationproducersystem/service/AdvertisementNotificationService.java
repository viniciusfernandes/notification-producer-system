package br.com.mercadolivre.notificationproducersystem.service;

import br.com.mercadolivre.notificationproducersystem.exception.RequiredFieldsException;
import br.com.mercadolivre.notificationproducersystem.model.AdvertisementExclusion;
import br.com.mercadolivre.notificationproducersystem.model.AdvertisementNotification;
import br.com.mercadolivre.notificationproducersystem.model.repository.AdvertisementExclusionRepository;
import br.com.mercadolivre.notificationproducersystem.model.repository.AdvertisementNotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdvertisementNotificationService {
  private AdvertisementNotificationRepository advertisementNotificationRepository;
  private AdvertisementExclusionRepository advertisementExclusionRepository;
  private CacheManager cacheManager;

  public AdvertisementNotificationService(
      @Autowired AdvertisementNotificationRepository advertisementNotificationRepository,
      @Autowired AdvertisementExclusionRepository advertisementExclusionRepository,
      @Autowired CacheManager cacheManager) {
    this.advertisementNotificationRepository = advertisementNotificationRepository;
    this.advertisementExclusionRepository = advertisementExclusionRepository;
    this.cacheManager = cacheManager;
  }

  @CacheEvict(cacheNames = "${redis.cache.exclusion-customer}", key = "#customerId")
  public int enableCustomerNotification(String customerId) throws RequiredFieldsException {
    if (customerId == null || customerId.isBlank()) {
      throw new RequiredFieldsException("To include an user from the advertisements you should send a nonempty customerId");
    }
    return advertisementExclusionRepository.deleteById(customerId);
  }

  public int disableCustomerNotification(String customerId) throws RequiredFieldsException {
    if (customerId == null || customerId.isBlank()) {
      throw new RequiredFieldsException("To exclude an user from the advertisements you should send a nonempty customerId");
    }
    var exclusion = AdvertisementExclusion.builder().customerId(customerId).build();
    advertisementExclusionRepository.save(exclusion);
    return advertisementNotificationRepository.removeAllByCustomerId(customerId);
  }

  public void save(List<AdvertisementNotification> advertisements) throws RequiredFieldsException {
    if (advertisements == null || advertisements.isEmpty()) {
      return;
    }
    var errorMessage = new ArrayList<String>();
    for (var advertisement : advertisements) {
      if (advertisement != null && !advertisement.isValid()) {
        errorMessage.add(advertisement.toString());
      }
    }
    if (!errorMessage.isEmpty()) {
      throw new RequiredFieldsException("The following advertisements have empty fields, but they are mandatory: ", errorMessage);
    }
    var advertisementsToPublish = advertisements.stream().filter(advertisement ->
            !advertisementExclusionRepository.isCustomerExcluded(advertisement.getUserId()))
        .collect(Collectors.toList());

    advertisementNotificationRepository.save(advertisementsToPublish);
  }

  public List<AdvertisementNotification> findAll() {
    return advertisementNotificationRepository.findAll();
  }

  public void removeAllByIds(List<String> ids) {
    if (ids == null || ids.isEmpty()) {
      return;
    }
    advertisementNotificationRepository.removeAllByCode(ids);
  }
}
