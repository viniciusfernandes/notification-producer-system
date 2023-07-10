package br.com.mercadolivre.notificationsystem.message;

import br.com.mercadolivre.notificationsystem.message.dto.NotificationDto;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class NotificationSerializer extends JsonSerializer<NotificationDto> {
}
