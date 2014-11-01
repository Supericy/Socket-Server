package kosiec.Server;

import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public interface Command {

	public void execute(Socket socket, String[] args);

}
