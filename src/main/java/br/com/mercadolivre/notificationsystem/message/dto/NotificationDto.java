package br.com.mercadolivre.notificationsystem.message.dto;

import lombok.Data;

@Data
public class NotificationDto {
  private String code;
  private String userId;
  private String title;
  private String description;
  private String channel;

  public NotificationDto() {
  }

  public NotificationDto(String code, String userId, String title, String description, String channel) {
    this.code = code;
    this.userId = userId;
    this.title = title;
    this.description = description;
    this.channel = channel;
  }
}
