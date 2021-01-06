package tasks.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import libs.netty.NettyInputOutputProvider;
import lombok.Setter;
import reactor.core.publisher.Mono;

public abstract class BaseTask {
    @Setter
    protected Object taskData;

    public abstract Mono<Void> execute(NettyInputOutputProvider provider);

    protected Mono<Void> sendDataToOutChannel(NettyInputOutputProvider provider) {
        return Mono.fromCallable(() -> {
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(this.taskData);
            return Mono.just(json);
        }).flatMap(provider::sendData);
    }
}
