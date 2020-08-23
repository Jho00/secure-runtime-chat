package common.stream;

import lombok.Getter;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

public class InputOutputController {
    // TODO: make it in per request SCOPE
    @Getter
    private static InputOutputController controller;

    public static void init(NettyInbound inbound, NettyOutbound outbound) {
        controller = new InputOutputController(inbound, outbound);
    }

    private NettyInbound inbound;
    private NettyOutbound outbound;

    public InputOutputController(NettyInbound inbound, NettyOutbound outbound) {
        this.inbound = inbound;
        this.outbound = outbound;
    }

    public Mono<Void> sendData(Mono<String> json) {
        return outbound.sendString(json).then();
    }
}
