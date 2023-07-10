package br.com.mercadolivre.notificationsystem.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class AdvertisementExclusion {
  @Id
  private String customerId;
}
