package common.stream;

import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

// TODO: make provider as interface
public class InputOutputProvider {
    private NettyInbound inbound;
    private NettyOutbound outbound;

    public InputOutputProvider(NettyInbound inbound, NettyOutbound outbound) {
        this.inbound = inbound;
        this.outbound = outbound;
    }

    public Mono<Void> sendData(Mono<String> json) {
        return outbound.sendString(json).then();
    }
}
