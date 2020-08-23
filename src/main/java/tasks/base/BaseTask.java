package tasks.base;

import lombok.Setter;
import reactor.core.publisher.Mono;

public abstract class BaseTask {
    @Setter
    protected Object taskData;

    public abstract Mono<Void> execute();
}
