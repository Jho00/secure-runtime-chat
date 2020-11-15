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
    private NettyInbound inbound;
    private NettyOutbound outbound;

    private InputOutputProvider provider;

    public ServerHandler(NettyInbound inbound, NettyOutbound outbound) {
        this.inbound = inbound;
        this.outbound = outbound;

        this.provider = new InputOutputProvider(inbound, outbound);
    }

    public Flux<Void> handle() {
        return this.executeTask();
    }

    private Flux<Void> executeTask() {
        return inbound.receive().asString().flatMap(str -> {
            try {
                Mono<Request> requestMono = RequestMapper.parse(str);
                BaseTask task = TasksMapper.map(requestMono);

                if (task == null) {
                    return ErrorTask.createNewErrorTask().execute(this.provider);
                }

                return task.execute(this.provider);
            } catch (JsonProcessingException e) {
                return ErrorTask.createNewErrorTask().execute(this.provider);
            }
        });
    }
}
