package pkg.Server;

import pkg.Server.Command.Permission;

import java.net.InetAddress;

/**
 * Created by Chad on 11/9/2014.
 */
public class ClientAuthorizationService {

	public static final String UNAUTHORIZED_MESSAGE = "Incorrect Authorization Code";

	public boolean authorize(Client client, String authorizationCode)
	{
		boolean verified = false;

		try
		{
			if (client.isConnected())
			{
				int serverIp = convertAddressToInt(client.getServerInetAddress());
				int clientIp = convertAddressToInt(client.getInetAddress());
				int received = Integer.parseInt(authorizationCode);

				verified = (serverIp - clientIp) == received;
			}
		}
		catch (NumberFormatException e)
		{
			verified = false;
		}

		return verified;
	}

	private int convertAddressToInt(InetAddress address)
	{
		return Integer.parseInt(address.toString().replaceAll("/", "").replaceAll("\\.", ""));
	}

}
