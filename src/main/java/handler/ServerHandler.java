package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.stream.InputOutputProvider;
import handler.dto.RequestMapper;
import handler.dto.request.Request;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import tasks.ErrorTask;
import tasks.base.BaseTask;
import tasks.utils.TasksMapper;

public class ServerHandler {
    private final NettyInbound inbound;

    private final InputOutputProvider provider;

    public ServerHandler(NettyInbound inbound, NettyOutbound outbound) {
        this.inbound = inbound;

        this.provider = new InputOutputProvider(inbound, outbound);
    }

    public Flux<Void> handle() {
        return this.executeTask();
    }

    private Flux<Void> executeTask() {
        return inbound.receive().asString().flatMap(str ->
                RequestMapper.parse(str)
                             .flatMap(result -> TasksMapper.map(result)
                             .execute(provider)))
        .doOnError((Throwable e) -> {
            e.printStackTrace(); // TODO: inject logger
            ErrorTask.createNewErrorTask().execute(this.provider);
        });
    }
}
