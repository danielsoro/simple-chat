import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client {
    private final Executor executorService = Executors.newCachedThreadPool();
    private final Socket socket;
    private final PrintWriter out;

    public Client(String host, int port, boolean console) throws IOException {
        this.socket = new Socket(host, port);
        this.executorService.execute(new ClientMsg(socket));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        if (console) {
            createBuffered();
        }
    }

    public void createBuffered() throws IOException {
        final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            String input = bf.readLine();
            sendMsg(input);
        }
    }

    public void sendMsg(String msg) throws IOException {
        out.println(msg);
    }

    public static void main(String[] args) {
        try {
            new Client("localhost", 8080, true);
        } catch (IOException e) {
            System.out.println("Erro when trying connect: " + e.getMessage());
        }
    }
}
