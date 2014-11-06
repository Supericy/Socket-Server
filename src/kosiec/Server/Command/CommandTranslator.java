package kosiec.Server.Command;

import kosiec.Server.MetaCommand;
import kosiec.Server.SimpleConsoleHandler;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Chad on 10/31/2014.
 */
public class CommandTranslator {

	private static final Logger log = Logger.getLogger( CommandTranslator.class.getName() );
	static {
		log.addHandler(new SimpleConsoleHandler());
		log.setLevel(Level.OFF);
	}

	public static final String DELIMITER = ";";
	public static final String[] NO_ARGS = new String[0];

	public MetaCommand decode(String commandString) throws TranslationException
	{
		if (commandString == null)
			throw new TranslationException("CommandTranslator: Can't translate a null command string");

		String[] tokens = commandString.split(DELIMITER);

		String commandName = tokens[0];
		String[] commandArgs = tokens.length == 1 ? NO_ARGS : Arrays.copyOfRange(tokens, 1, tokens.length);

		log.log(Level.FINE, "MetaCommand", new Object[] { commandName, commandArgs});

		if (commandName == null || commandName.isEmpty())
			throw new TranslationException("CommandTranslator: Command name can't be null or empty");

		return new MetaCommand(commandName, commandArgs);
	}

}
