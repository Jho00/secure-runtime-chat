package tasks;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import tasks.base.BaseTask;

public class PipeTask extends BaseTask {
    public PipeTask(NettyInbound inbound, NettyOutbound outbound) {
        super(inbound, outbound);
    }

    @Override
    public Mono<Void> execute() {
        Flux<String> result = inbound.receive().asString().flatMap(str -> {
            System.out.println(str);

            return Mono.just(str);
        });

        return outbound.sendString(result).then();
    }
}
