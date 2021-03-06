import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConsoleMsg implements Runnable {
    private final BufferedReader in;

    public ClientConsoleMsg(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String message = "";
            while ((message = in.readLine()) != null)
                System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
