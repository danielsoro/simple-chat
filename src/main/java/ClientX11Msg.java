import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientX11Msg implements Runnable {
    private final BufferedReader in;
    private final TextComponent target;

    public ClientX11Msg(Socket socket, TextComponent target) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.target = target;
    }

    @Override
    public void run() {
        try {
            String message = "";
            while ((message = in.readLine()) != null)
                target.setText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
