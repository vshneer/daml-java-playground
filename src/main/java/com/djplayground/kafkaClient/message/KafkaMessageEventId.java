package com.djplayground.kafkaClient.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaMessageEventId {
    @NotBlank(message = "EventId should not be blank")
    private String eventId;
}