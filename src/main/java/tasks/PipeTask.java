package tasks;

import common.stream.NettyInputOutputProvider;
import reactor.core.publisher.Mono;
import tasks.base.BaseTask;

public class PipeTask extends BaseTask {
    @Override
    public Mono<Void> execute(NettyInputOutputProvider provider) {
        return this.sendDataToOutChannel(provider);
    }
}
