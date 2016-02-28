import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client {
    private final Executor executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        new Client().createClient("localhost", 8080);
    }

    public void createClient(String hots, int port) {
        try (Socket socket = new Socket(hots, port)) {
            executorService.execute(new ClientMsg(socket));

            final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String input = bf.readLine();
                if (input != null) {
                    out.println(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
