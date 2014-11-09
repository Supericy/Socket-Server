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
			Container container = loadClassContainer();

			Server server = container.get(Server.class);
			UserInterface ui = container.get(UserInterface.class);

			ui.display("Server::Ready");
			ui.display("Server::Accepting Connections => Port(" + PORT + ")");

			while (true)
			{
				ui.display("Server::Waiting");
				server.acceptAndHandleClient();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void loadCommands(Container container) throws Exception
	{
		container.put(AuthCommand.class, new AuthCommand());
		container.put(ArduinoCommand.class, new ArduinoCommand(container.get(SerialPortDirectionWriter.class)));
		container.put(ConnectCommand.class, new ConnectCommand(container.get(UserInterface.class)));
		container.put(DisconnectCommand.class, new DisconnectCommand(container.get(UserInterface.class)));
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

			container.put(CommandTranslator.class, new CommandTranslator());
			container.put(CommandFactory.class, new CommandFactory(container, COMMAND_PACKAGES));

			container.put(Handler.class,
					new ThreadedClientHandler(Executors.newCachedThreadPool(),
						new ClientHandler(
								container.get(CommandTranslator.class),
								container.get(CommandFactory.class),
								container.get(UserInterface.class))));

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
