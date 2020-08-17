package handler;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import tasks.PipeTask;
import tasks.base.BaseTask;

public class GlobalHandler {
    private NettyInbound inbound;
    private NettyOutbound outbound;

    public GlobalHandler(NettyInbound inbound, NettyOutbound outbound) {
        this.inbound = inbound;
        this.outbound = outbound;
    }

    public Mono<Void> handle() {
        return this.executeTask();
    }

    private Mono<Void> executeTask() {
        BaseTask task = new PipeTask(inbound, outbound);

        return task.execute();
    }
}
