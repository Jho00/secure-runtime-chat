package handler;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import libs.guice.BasicModule;
import libs.guice.InjectableClass;
import libs.netty.NettyInputOutputProvider;
import handler.dto.RequestMapper;
import reactor.core.publisher.Flux;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import tasks.ErrorTask;
import tasks.utils.TasksMapper;
import org.apache.logging.log4j.Logger;

public class ServerHandler extends InjectableClass {
    @Inject
    public Logger logger;

    @Inject
    public TasksMapper mapper;

    private final NettyInputOutputProvider provider;

    public ServerHandler(NettyInbound inbound, NettyOutbound outbound) {
        super();
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
                                .flatMap(result -> mapper
                                        .map(result)
                                        .execute(provider))
                )
                .doOnError((Throwable e) -> {
                    logger.error(e.getMessage());
                    ErrorTask.createNewErrorTask().execute(this.provider);
                });
    }
}
