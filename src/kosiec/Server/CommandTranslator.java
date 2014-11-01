package kosiec.Server;

import java.util.Arrays;

/**
 * Created by Chad on 10/31/2014.
 */
public class CommandTranslator {

	public static final String DELIMITER = ";";
	public static final String[] NO_ARGS = new String[0];

	public MetaCommand decode(String commandString)
	{
		String[] tokens = commandString.split(DELIMITER);

		String commandName = tokens[0];
		String[] commandArgs = tokens.length == 1 ? NO_ARGS : Arrays.copyOfRange(tokens, 1, tokens.length);

		return new MetaCommand(commandName, commandArgs);
	}

}
