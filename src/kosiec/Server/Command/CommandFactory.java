package kosiec.Server.Command;

import kosiec.Server.Container;
import kosiec.Server.SimpleConsoleHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Chad on 10/31/2014.
 */
public class CommandFactory {

	private static final Logger log = Logger.getLogger( CommandFactory.class.getName() );
	static {
		log.addHandler(new SimpleConsoleHandler());
		log.setLevel(Level.OFF);
	}

	public static final String CLASS_SUFFIX = "Command";

	private final Container container;
	private final String[] commandPackages;

	public CommandFactory(Container container, String[] commandPackages)
	{
		this.container = container;
		this.commandPackages = commandPackages;
	}

	public Command make(String commandName)
	{
		// if there were more than 1 command, then use reflection or something but since theres just 1...

		String className = commandName + CLASS_SUFFIX;

		log.log(Level.FINE, "ClassName", new Object[]{ className });

		Command command = getCommand(className);

		log.log(Level.FINE, "Command", new Object[]{ command });

		if (command == null)
			throw new CommandException("CommandFactory: \"" + className + "\" command does not exist");

		return command;
	}

	private Command getCommand(String className)
	{
		Command command = null;

		Class clazz = getCommandClass(className);

		if (clazz != null)
		{
			Object cmdobj = container.get(clazz);

			if (cmdobj instanceof Command)
				command = (Command) cmdobj;
		}

		return command;
	}

	private Class getCommandClass(String className)
	{
		Class clazz = null;

		for (String commandPackage : commandPackages)
		{
			try
			{
				clazz = Class.forName(commandPackage + "." + className);

				// class found
				break;
			}
			catch (ClassNotFoundException e)
			{
				// ignore, cause we'll return null
			}
		}

		return clazz;
	}

}
