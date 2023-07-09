package br.com.mercadolivre.notificationproducersystem.controller;

import br.com.mercadolivre.notificationproducersystem.exception.RequiredFieldsException;
import br.com.mercadolivre.notificationproducersystem.message.dto.NotificationDto;
import br.com.mercadolivre.notificationproducersystem.service.AdvertisementNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.mercadolivre.notificationproducersystem.mapper.NotificationMapper.toModel;

@Slf4j
@RestController
public class AdvertisementNotificationController {
  @Autowired
  private AdvertisementNotificationService advertisementService;

  @PostMapping(value = "/advertisement-notifications", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> saveNotifications(@RequestBody List<NotificationDto> advertisementDtos)
      throws RequiredFieldsException {
    var advertisementNotifications = toModel(advertisementDtos);
    advertisementService.save(advertisementNotifications);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/advertisement-notifications/customers/{idCustomer}")
  public ResponseEntity<Void> disableCustomerNotifications(@PathVariable String idCustomer)
      throws RequiredFieldsException {
    var total = advertisementService.disableCustomerNotification(idCustomer);
    if (total <= 0) {
      ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/advertisement-notifications/customers/{idCustomer}")
  public ResponseEntity<Void> enableCustomerNotifications(@PathVariable String idCustomer)
      throws RequiredFieldsException {
    advertisementService.enableCustomerNotification(idCustomer);
    return ResponseEntity.ok().build();
  }
}