package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.stream.InputOutputController;
import handler.dto.RequestMapper;
import handler.dto.request.Request;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import tasks.utils.TasksMapper;

public class ServerHandler {
    private NettyInbound inbound;
    private NettyOutbound outbound;

    public ServerHandler(NettyInbound inbound, NettyOutbound outbound) {
        this.inbound = inbound;
        this.outbound = outbound;
    }

    public Flux<Void> handle() {
        InputOutputController.init(inbound, outbound);
        System.out.println(1);
        return this.executeTask();
    }

    private Flux<Void> executeTask() {
        return inbound.receive().asString().flatMap(str -> {
            try {
                Mono<Request> requestMono = RequestMapper.parse(str);
                return TasksMapper.map(requestMono).execute();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return Mono.empty();
        });
    }
}
