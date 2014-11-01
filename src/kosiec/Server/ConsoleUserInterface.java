package kosiec.Server;

import java.io.PrintStream;

/**
 * Created by Chad on 10/31/2014.
 */
public class ConsoleUserInterface implements UserInterface {

	private final PrintStream output;

	public ConsoleUserInterface(PrintStream output)
	{
		this.output = output;
	}

	@Override
	public void display(String msg)
	{
		output.println(msg);
	}

}
