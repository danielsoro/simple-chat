import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client {
    private static final Executor executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080)) {
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
