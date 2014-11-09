package kosiec.Server.Command;

/**
 * Created by Chad on 10/31/2014.
 */
public class MetaCommand {

	private final String name;
	private final String[] args;

	public MetaCommand(String name, String[] args)
	{
		this.name = name;
		this.args = args;
	}

	public String getCommandName()
	{
		return name;
	}

	public String[] getCommandArgs()
	{
		return args;
	}

}
