package kosiec.Server;

import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public interface Handler {
	void handle(Socket socket);
}
