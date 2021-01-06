import com.google.inject.Guice;
import com.google.inject.Injector;
import io.netty.channel.ChannelOption;
import libs.guice.BasicModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import handler.ServerHandler;

public class Application {
    // TODO: move server params to config
    private final static String HOST = "localhost";
    private final static Integer PORT = 8080;

    private static Logger logger;

    public static void main(String[] args) {
        initInjector();

        DisposableServer server =
                TcpServer.create()
                        .host(HOST)
                        .port(PORT)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                        .doOnBind(any -> printServerUrl())
                        .handle((in, out) -> {
                            ServerHandler handler = new ServerHandler(in, out);
                            return handler.handle();
                        })
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
