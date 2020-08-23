import io.netty.channel.ChannelOption;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import handler.ServerHandler;

public class Application {
    private final static String HOST = "localhost";
    private final static Integer PORT = 8080;

    public static void main(String[] args) {
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
        System.out.println("Server listening on " + url);
    }
}
