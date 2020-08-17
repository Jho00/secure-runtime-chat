package tasks.base;

import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

public abstract class BaseTask {
    protected NettyInbound inbound;
    protected NettyOutbound outbound;

    public abstract Mono<Void> execute();

    public BaseTask(NettyInbound inbound, NettyOutbound outbound) {
        this.inbound = inbound;
        this.outbound = outbound;
    }
}
