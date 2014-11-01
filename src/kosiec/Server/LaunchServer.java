package kosiec.Server;

import jssc.SerialPort;
import jssc.SerialPortException;
import kosiec.Server.Arduino.Commands.ArduinoCommand;
import kosiec.Server.Arduino.SerialPort.SerialPortReader;
import kosiec.Server.Arduino.SerialPort.UserInterfaceSerialPortDirectionWriter;
import kosiec.Server.Arduino.SerialPort.SerialPortDirectionWriter;
import kosiec.Server.Arduino.SerialPort.SerialPortFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

/**
 * Created by Chad on 10/31/2014.
 */
public class LaunchServer {

	public static final int PORT = 5555;
	public static final String[] COMMAND_PACKAGES = {
			"kosiec.Server.Arduino.Commands"
	};

	public static void main(String[] args)
	{
		try
		{
			Container container = loadClassContainer();

			Server server = container.get(Server.class);
			UserInterface ui = container.get(UserInterface.class);

			while (true)
			{
				ui.display("Waiting for a connection...");
				server.acceptAndHandleClient();
				ui.display("Client has been handled.");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// TODO: could export this to a config file
	public static Container loadClassContainer()
	{
		Container container = new ClassContainer();

		try
		{
			container.put(UserInterface.class, new ConsoleUserInterface(System.out));
//			container.put(SerialPortDirectionWriter.class, new JsscSerialPortDirectionWriter(createSerialPort(container.get(UserInterface.class))));
			container.put(SerialPortDirectionWriter.class, new UserInterfaceSerialPortDirectionWriter(container.get(UserInterface.class)));

			container.put(CommandTranslator.class, new CommandTranslator());
			container.put(CommandFactory.class, new CommandFactory(container, COMMAND_PACKAGES));

//			container.put(Handler.class, new ClientHandler(container.get(CommandTranslator.class), container.get(CommandFactory.class)));
			container.put(Handler.class, new ThreadedHandler(Executors.newCachedThreadPool(), new ClientHandler(container.get(CommandTranslator.class), container.get(CommandFactory.class))));

			container.put(ServerSocket.class, new ServerSocket(PORT));
			container.put(Server.class, new Server(container.get(ServerSocket.class), container.get(Handler.class)));

			loadCommands(container);
		}
		catch (Exception e)
		{
			System.err.println("Contained has failed to load:");
			e.printStackTrace();
			container = null;
		}

		return container;
	}

	public static void loadCommands(Container container) throws Exception
	{
		container.put(ArduinoCommand.class, new ArduinoCommand(container.get(SerialPortDirectionWriter.class)));
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
