package pkg.Server.Command;

import pkg.Server.SimpleConsoleHandler;

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
		checkNull("CommandString", commandString);

		commandString = commandString.trim();

		checkEmpty("CommandString", commandString);

		String[] tokens = commandString.split(DELIMITER);

		String commandName = tokens[0];
		String[] commandArgs = tokens.length == 1 ? NO_ARGS : Arrays.copyOfRange(tokens, 1, tokens.length);

		// don't think this can be null
		checkNull("CommandName", commandName);
		checkEmpty("CommandName", commandName);

		log.log(Level.FINE, "MetaCommand", new Object[] { commandName, commandArgs});

		return new MetaCommand(commandName, commandArgs);
	}

	private void checkNull(String indentifier, String str) throws TranslationException
	{
		if (str == null)
			throw new TranslationException("CommandTranslator: " + indentifier + " can't be null");
	}

	private void checkEmpty(String indentifier, String commandName) throws TranslationException
	{
		if (commandName.isEmpty())
			throw new TranslationException("CommandTranslator: " + indentifier + " can't be an empty string");
	}

}
