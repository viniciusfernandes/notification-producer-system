package br.com.mercadolivre.notificationproducersystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementNotification {
  private String code;
  private String userId;
  private String title;
  private String description;
  private String channel;

  public boolean isValid() {
    return code != null && !code.isBlank()
        && userId != null && !userId.isBlank()
        && title != null && !title.isBlank()
        && description != null && !description.isBlank()
        && channel != null && !channel.isBlank();
  }

  @Override
  public String toString() {
    return "{" +
        "id='" + code + '\'' +
        ", userId='" + userId + '\'' +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", type='" + channel + '\'' +
        '}';
  }
}
