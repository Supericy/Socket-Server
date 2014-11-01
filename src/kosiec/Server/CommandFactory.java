package kosiec.Server;

import kosiec.Server.Arduino.Commands.ArduinoCommand;

/**
 * Created by Chad on 10/31/2014.
 */
public class CommandFactory {

	private final Container container;

	public CommandFactory(Container container)
	{
		this.container = container;
	}

	public Command make(String commandName)
	{
		// if there were more than 1 command, then use reflection or something but since theres just 1...

		Command command = null;

		if ("ArduinoCommand".equals(commandName))
		{
			command = container.get(ArduinoCommand.class);
		}

		return command;
	}

}
