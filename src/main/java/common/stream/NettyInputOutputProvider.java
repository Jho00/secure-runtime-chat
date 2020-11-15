package common.stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

// TODO: make provider as interface
public class NettyInputOutputProvider {
    private NettyInbound inbound;
    private NettyOutbound outbound;

    public NettyInputOutputProvider(NettyInbound inbound, NettyOutbound outbound) {
        this.inbound = inbound;
        this.outbound = outbound;
    }

    public Mono<Void> sendData(Mono<String> json) {
        return outbound.sendString(json).then();
    }

    public Flux<String> receive() {
        return inbound.receive().asString();
    }
}
