package handler;

import common.stream.NettyInputOutputProvider;
import handler.dto.RequestMapper;
import reactor.core.publisher.Flux;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import tasks.ErrorTask;
import tasks.utils.TasksMapper;

public class ServerHandler {

    private final NettyInputOutputProvider provider;

    public ServerHandler(NettyInbound inbound, NettyOutbound outbound) {
        this.provider = new NettyInputOutputProvider(inbound, outbound);
    }

    public Flux<Void> handle() {
        return this.executeTask();
    }

    private Flux<Void> executeTask() {
        return provider.receive()
                .flatMap(str ->
                        RequestMapper
                                .parse(str)
                                .flatMap(result -> TasksMapper
                                        .map(result)
                                        .execute(provider))
                )
                .doOnError((Throwable e) -> {
                    e.printStackTrace(); // TODO: inject logger
                    ErrorTask.createNewErrorTask().execute(this.provider);
                });
    }
}
