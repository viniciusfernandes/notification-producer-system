package br.com.mercadolivre.notificationproducersystem;

import br.com.mercadolivre.notificationsystem.exception.RequiredFieldsException;
import br.com.mercadolivre.notificationsystem.model.AdvertisementExclusion;
import br.com.mercadolivre.notificationsystem.model.AdvertisementNotification;
import br.com.mercadolivre.notificationsystem.model.repository.AdvertisementExclusionRepository;
import br.com.mercadolivre.notificationsystem.model.repository.AdvertisementNotificationRepository;
import br.com.mercadolivre.notificationsystem.service.AdvertisementNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AdvertisementNotificationServiceTest {

  @Mock
  private AdvertisementNotificationRepository advertisementNotificationRepository;
  @Mock
  private AdvertisementExclusionRepository advertisementExclusionRepository;
  @Mock
  private CacheManager cacheManager;
  private AdvertisementNotificationService advertisementNotificationService;

  private final String CUSTOMER_ID_1 = "111";
  private final String CUSTOMER_ID_2 = "222";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    advertisementNotificationService = new AdvertisementNotificationService(
        advertisementNotificationRepository, advertisementExclusionRepository, cacheManager);
  }

  @Test
  void disablingCustomerNotificationSuccessfully() throws RequiredFieldsException {
    advertisementNotificationService.disableCustomerNotification(CUSTOMER_ID_1);
    verify(advertisementExclusionRepository, times(1)).save(any(AdvertisementExclusion.class));
    verify(advertisementNotificationRepository, times(1)).removeAllByCustomerId(CUSTOMER_ID_1);
  }

  @Test
  void validatingDisablementCustomer() {
    assertThrows(RequiredFieldsException.class, () -> advertisementNotificationService.disableCustomerNotification(null));
    assertThrows(RequiredFieldsException.class, () -> advertisementNotificationService.disableCustomerNotification(" "));
    verify(advertisementExclusionRepository, times(0)).save(any(AdvertisementExclusion.class));
    verify(advertisementNotificationRepository, times(0)).removeAllByCustomerId(CUSTOMER_ID_1);
  }

  @Test
  void enablingCustomerNotificationSuccessfully() throws RequiredFieldsException {
    advertisementNotificationService.enableCustomerNotification(CUSTOMER_ID_1);
    verify(advertisementExclusionRepository, times(1)).deleteById(CUSTOMER_ID_1);
  }

  @Test
  void validatingEnablementCustomer() {
    assertThrows(RequiredFieldsException.class, () -> advertisementNotificationService.enableCustomerNotification(null));
    assertThrows(RequiredFieldsException.class, () -> advertisementNotificationService.enableCustomerNotification(" "));
    verify(advertisementExclusionRepository, times(0)).deleteById(anyString());
  }

  @Test
  void savingAdvertisementsSuccessfully() throws RequiredFieldsException {
    var advertisements = buildAdvertisementNotifications();
    advertisementNotificationService.save(advertisements);
    verify(advertisementExclusionRepository, times(2)).isCustomerExcluded(anyString());
    verify(advertisementNotificationRepository, times(1)).save(anyList());
  }

  @Test
  void validatingEmptyListSavingAdvertisements() throws RequiredFieldsException {
    advertisementNotificationService.save(Collections.emptyList());
    verify(advertisementExclusionRepository, times(0)).isCustomerExcluded(anyString());
    verify(advertisementNotificationRepository, times(0)).save(anyList());
  }

  @Test
  void validatingEmptyUserIdAdvertisementBeforeSaving() {
    var advertisements = buildAdvertisementNotifications();
    advertisements.get(0).setUserId(null);
    assertThrows(RequiredFieldsException.class, () -> advertisementNotificationService.save(advertisements));
    verify(advertisementExclusionRepository, times(0)).isCustomerExcluded(anyString());
    verify(advertisementNotificationRepository, times(0)).save(anyList());

  }

  private List<AdvertisementNotification> buildAdvertisementNotifications() {
    var advert1 = AdvertisementNotification.builder()
        .code("14")
        .userId(CUSTOMER_ID_1)
        .title("Maquina de Lavar")
        .description("Brastem máquina de lavar R$278,99 até o Natal")
        .channel("WEB")
        .build();
    var advert2 = AdvertisementNotification.builder()
        .code("15")
        .userId(CUSTOMER_ID_2)
        .title("Promocao de Notebook")
        .description("Notebook Samsung X221 I8 por R$1299,99")
        .channel("WEB")
        .build();
    return List.of(advert1, advert2);
  }
}
