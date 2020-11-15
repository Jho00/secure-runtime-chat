package tasks.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.stream.NettyInputOutputProvider;
import lombok.Setter;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

public abstract class BaseTask {
    @Setter
    protected Object taskData;

    @SneakyThrows
    public Mono<Void> execute(NettyInputOutputProvider provider) {
        return Mono.fromCallable(() -> {
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(this.taskData);
            return Mono.just(json);
        }).flatMap(provider::sendData);
    }
}
