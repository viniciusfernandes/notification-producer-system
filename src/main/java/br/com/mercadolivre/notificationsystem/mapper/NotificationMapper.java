package br.com.mercadolivre.notificationsystem.mapper;

import br.com.mercadolivre.notificationsystem.message.dto.NotificationDto;
import br.com.mercadolivre.notificationsystem.model.AdvertisementNotification;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {
  public static NotificationDto toDto(AdvertisementNotification model) {
    return new NotificationDto(
        model.getCode(),
        model.getUserId(),
        model.getTitle(),
        model.getDescription(),
        model.getChannel());
  }

  public static List<NotificationDto> toDto(List<AdvertisementNotification> models) {
    return models.stream().map(model -> toDto(model)).collect(Collectors.toList());
  }

  public static AdvertisementNotification toModel(NotificationDto dto) {
    return AdvertisementNotification.builder()
        .title(dto.getTitle())
        .code(dto.getCode())
        .description(dto.getDescription())
        .channel(dto.getChannel())
        .userId(dto.getUserId())
        .build();
  }

  public static List<AdvertisementNotification> toModel(List<NotificationDto> dtos) {
    return dtos.stream().map(dto -> toModel(dto)).collect(Collectors.toList());
  }
}
