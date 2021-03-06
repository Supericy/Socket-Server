package pkg;

import jssc.SerialPort;
import jssc.SerialPortException;
import pkg.Server.*;
import pkg.Server.Arduino.SerialPort.*;
import pkg.Server.Command.CommandFactory;
import pkg.Server.Command.CommandTranslator;
import pkg.Server.Command.Commands.ArduinoCommand;
import pkg.Server.Command.Commands.AuthCommand;
import pkg.Server.Command.Commands.ConnectCommand;
import pkg.Server.Command.Commands.DisconnectCommand;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

/**
 * Created by Chad on 10/31/2014.
 */
public class LaunchServer {

	public static final int PORT = 5555;
	public static final String[] COMMAND_PACKAGES = {
			"pkg.Server.Command.Commands"
	};

	public static void main(String[] args)
	{
		try
		{
			final Container container = loadClassContainer();

			final Server server = container.get(Server.class);
			final UserInterface ui = container.get(UserInterface.class);
			final Thread serverThread = createAndStartServerThread(server, ui);

			// just spams the current client list, this will be extracted to a ServerCommand eventually
//			new Thread(new Runnable() {
//				@Override
//				public void run()
//				{
//					while (true)
//					{
//						ui.display("-------------------");
//						ui.display("|  Client List:   |");
//						ui.display("-------------------");
//						for (Client client : server.getClientList())
//						{
//							ui.display(client.getInetAddress().toString());
//						}
//						ui.display("-------------------");
//						try
//						{
//							Thread.sleep(2000);
//						}
//						catch (InterruptedException e)
//						{
//							e.printStackTrace();
//						}
//					}
//				}
//			}).start();

			serverThread.join();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Thread createAndStartServerThread(final Server server, final UserInterface ui)
	{
		ui.display("Server::Ready");
		ui.display("Server::Accepting Connections => Port(" + PORT + ")");

		Thread t = new Thread(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						ui.display("Server::Waiting");
						server.acceptAndHandleClient();
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});

		t.setDaemon(true);
		t.start();

		return t;
	}

	public static void loadCommands(Container container) throws Exception
	{
		container.put(AuthCommand.class, new AuthCommand());
		container.put(ArduinoCommand.class, new ArduinoCommand(container.get(SerialPortDirectionWriter.class)));
		container.put(ConnectCommand.class, new ConnectCommand(container.get(UserInterface.class), container.get(Server.class)));
		container.put(DisconnectCommand.class, new DisconnectCommand(container.get(UserInterface.class), container.get(Server.class)));
	}

	// TODO: could export this to a config file
	public static Container loadClassContainer() throws Exception
	{
		Container container = new ClassContainer();

		try
		{
			container.put(UserInterface.class, new ConsoleUserInterface(System.out, System.err));

//			container.put(SerialPortDirectionWriter.class, new JsscSerialPortDirectionWriter(createSerialPort(container.get(UserInterface.class))));
			container.put(SerialPortDirectionWriter.class, new UserInterfaceSerialPortDirectionWriter(container.get(UserInterface.class)));

			container.put(ClientAuthorizationService.class, new ClientAuthorizationService());

			container.put(CommandTranslator.class, new CommandTranslator());
			container.put(CommandFactory.class, new CommandFactory(container, COMMAND_PACKAGES));

			container.put(Handler.class,
					new ThreadedClientHandler(Executors.newCachedThreadPool(),
						new ClientHandler(
								container.get(CommandTranslator.class),
								container.get(CommandFactory.class),
								container.get(UserInterface.class),
								container.get(ClientAuthorizationService.class))));

			container.put(ServerSocket.class, new ServerSocket(PORT));
			container.put(Server.class, new Server(container.get(ServerSocket.class), container.get(Handler.class)));

			loadCommands(container);
		}
		catch (Exception e)
		{
			System.err.println("Container has failed to load:");
			System.err.println(e.getMessage());
			throw e;
		}

		return container;
	}

	public static SerialPort createSerialPort(UserInterface ui) throws SerialPortException
	{
		SerialPortFactory serialPortFactory = new SerialPortFactory();

		SerialPort serialPort = serialPortFactory.make();

		// init our mask for displaying data returned from the serial port
		int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
		serialPort.setEventsMask(mask);//Set mask
		serialPort.addEventListener(new SerialPortReader(serialPort, ui));//Add SerialPortEventListener

		return serialPort;
	}

}
