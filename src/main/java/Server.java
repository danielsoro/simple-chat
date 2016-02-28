import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {
    private final Executor executorService = Executors.newCachedThreadPool();
    private final HashSet<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) {
        new Server().createServer(8080);
    }

    public void createServer(int port) {
        System.out.println("The chat is running.");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Handler request = new Handler(serverSocket.accept(), writers, UUID.randomUUID().toString());
                executorService.execute(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}