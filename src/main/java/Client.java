import javax.swing.text.JTextComponent;
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

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.executorService.execute(new ClientConsoleMsg(socket));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        createBuffered();
    }

    public Client(String host, int port, JTextComponent target) throws IOException {
        this.socket = new Socket(host, port);
        this.executorService.execute(new ClientX11Msg(socket, target));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void createBuffered() throws IOException {
        final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
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
            new Client("localhost", 8080);
        } catch (IOException e) {
            System.out.println("Erro when trying connect: " + e.getMessage());
        }
    }
}
