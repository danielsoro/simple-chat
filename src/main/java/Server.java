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
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("The chat is running.");
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                Handler request = new Handler(serverSocket.accept(), this.writers, UUID.randomUUID().toString());
                this.executorService.execute(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(8080).start();
    }
}