import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {
    private static final Executor executorService = Executors.newCachedThreadPool();
    private static final HashSet<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("The chat is running.");
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                RequestHandler request = new RequestHandler(serverSocket.accept(), writers, UUID.randomUUID().toString());
                executorService.execute(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}