package pkg.Server.Command;

import pkg.Server.Client;

/**
 * Created by Chad on 10/31/2014.
 */
public interface Command {

	public void execute(Client client, String[] args);

}
