import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

public class RequestHandler implements Runnable {
    private final String name;
    private final Socket socket;
    private final HashSet<PrintWriter> writers;


    public RequestHandler(Socket accept, HashSet<PrintWriter> serverWrites, String name) {
        this.socket = accept;
        this.writers = serverWrites;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Joined: " + name);
            writers.add(out);

            while (true) {
                String input = in.readLine();
                if (input == null) {
                    return;
                }

                for (PrintWriter writer : writers) {
                    writer.println("<" + name + "> " + input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

