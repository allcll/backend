package kr.allcll.seatfinder.sse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

public class SseEventBuilderFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final long RECONNECT_TIME_MILLIS = 1000L;

    public static SseEventBuilder create(String eventName, Object value) {
        try {
            String sseEvent = objectMapper.writeValueAsString(value);
            return baseEvent()
                .name(eventName)
                .data(sseEvent, MediaType.APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static SseEventBuilder createInitialEvent() {
        return baseEvent()
            .name("connection")
            .data("success", MediaType.TEXT_PLAIN);
    }

    private static SseEventBuilder baseEvent() {
        return SseEmitter.event()
            .reconnectTime(RECONNECT_TIME_MILLIS);
    }
}
