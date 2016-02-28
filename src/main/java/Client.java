import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {

        String msgEntrada = "";
        String msgSaida = "";

        try (Socket socket = new Socket("127.0.0.1", 12345)) {

            DataInputStream din = new DataInputStream(socket.getInputStream());
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            while (!msgEntrada.equalsIgnoreCase("exit")) {
                msgSaida = bf.readLine();
                dout.writeUTF(msgSaida);
                dout.flush();
                msgEntrada = din.readUTF();
                System.out.println(msgEntrada);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
