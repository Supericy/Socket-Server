package kosiec.Server.Command;

import kosiec.Server.Client;

import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public interface Command {

	public void execute(Client client, String[] args);

}
