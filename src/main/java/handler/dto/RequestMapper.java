package handler.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import handler.dto.request.Request;
import reactor.core.publisher.Mono;

public class RequestMapper {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static Mono<Request> parse(String received) throws JsonProcessingException {
        Request request = mapper.readValue(received, Request.class);

        return Mono.just(request);
    }
}
