import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
        private static final int PORT = 8080;
        public static void main(String[] args) {
            try {
                ServerSocket server = new ServerSocket(PORT);
                System.out.println("localhost" + PORT);
                while (true) {
                    new ThreadSocket(server.accept());
                }
            } catch (Exception e) {
            }
    }
}
