import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        String msgEntrada = "";
        String msgSaida = "";

        try(ServerSocket serverSocket = new ServerSocket(12345);
            Socket socket = serverSocket.accept();) {

            DataInputStream din = new DataInputStream(socket.getInputStream());
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            while (!msgEntrada.equalsIgnoreCase("exit")) {
                msgEntrada = din.readUTF();
                System.out.println(msgEntrada);
                msgSaida = bf.readLine();
                dout.writeUTF(msgSaida);
                dout.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
