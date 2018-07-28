package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
	final int port= 1041;
     ServerSocket server = new ServerSocket(port);
     Socket socket = server.accept();
     System.out.println("client is ready");
	}

}
