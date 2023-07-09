package br.com.mercadolivre.notificationproducersystem.message;

import br.com.mercadolivre.notificationproducersystem.message.dto.NotificationDto;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class NotificationSerializer extends JsonSerializer<NotificationDto> {
}
