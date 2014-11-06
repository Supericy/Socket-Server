package kosiec.Server;

import java.io.PrintStream;

/**
 * Created by Chad on 10/31/2014.
 */
public class ConsoleUserInterface implements UserInterface {

	private final PrintStream output;
	private final PrintStream error;

	public ConsoleUserInterface(PrintStream output, PrintStream error)
	{
		this.output = output;
		this.error = error;
	}

	@Override
	public void display(String msg)
	{
		output.println(msg);
	}

	@Override
	public void displayError(String msg)
	{
		error.println(msg);
	}

}
