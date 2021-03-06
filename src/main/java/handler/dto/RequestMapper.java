package handler.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import handler.dto.request.Request;
import reactor.core.publisher.Mono;

public class RequestMapper {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static Mono<Request> parse(String received) {
        return Mono.fromCallable(() -> mapper.readValue(received, Request.class));
    }
}
