package kosiec.Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public class TestClient {

	public static void main(String[] args) throws IOException
	{
		Socket socket = new Socket("127.0.0.1", 5555);

		PrintStream out = new PrintStream(socket.getOutputStream());

		out.println("ArduinoCommand;FORWARD;25");
	}

}
