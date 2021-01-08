import com.google.inject.Guice;
import com.google.inject.Injector;
import common.config.AppConfig;
import io.netty.channel.ChannelOption;
import libs.guice.BasicModule;
import org.apache.logging.log4j.Logger;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import handler.ServerHandler;

public class Application {
    private final static String HOST = AppConfig.getConfig().getServerHost();
    private final static String PORT = AppConfig.getConfig().getServerPort();

    private static Logger logger;

    public static void main(String[] args) {
        initInjector();

        DisposableServer server =
                TcpServer.create()
                        .host(HOST)
                        .port(Integer.parseInt(PORT))
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                        .doOnConnection(connection -> {
                            connection.addHandler(
                                    new ServerHandler(connection.inbound(), connection.outbound())
                            );
                        })
                        .doOnBind(any -> printServerUrl())
                        .bindNow();

        server.onDispose()
                .block();
    }

    private static void printServerUrl() {
        String url = HOST + ":" + PORT;

        logger.info("Server listening on " + url);
    }

    private static void initInjector() {
        Injector injector = Guice.createInjector(new BasicModule());
        logger = injector.getInstance(Logger.class);
    }
}
