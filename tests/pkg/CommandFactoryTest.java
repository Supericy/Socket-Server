package pkg;

import pkg.Server.Command.Commands.ArduinoCommand;
import pkg.Server.Command.CommandException;
import pkg.Server.Command.CommandFactory;
import pkg.Server.Container;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class CommandFactoryTest {

	@Test
	public void testMake() throws Exception
	{
		MockContainer container = new MockContainer();

		assertThat(container.get(ArduinoCommand.class), instanceOf(ArduinoCommand.class));

		CommandFactory commandFactory = new CommandFactory(container, new String[] {"pkg.Server.Command.Commands"});

		assertThat(commandFactory.make("Arduino"), instanceOf(ArduinoCommand.class));

		try
		{
			commandFactory.make("");
			fail("Command factory should be throwing a CommandException");
		}
		catch (CommandException e) {}

		try
		{
			commandFactory.make("something random");
			fail("Command factory should be throwing a CommandException");
		}
		catch (CommandException e) {}
	}

	static class MockContainer implements Container {

		@Override
		public void put(Class<?> key, Object impl)
		{
			// don't need this method, so we'll just throw a notimplementedexception just in-case
			throw new NotImplementedException();
		}

		@Override
		public void put(Class<?> cls, Object impl, String name)
		{
			// don't need this method, so we'll just throw a notimplementedexception just in-case
			throw new NotImplementedException();
		}

		@Override
		public <T> T get(Class<? extends T> cls)
		{
			return get(cls, null);
		}

		@Override
		public <T> T get(Class<? extends T> cls, String name)
		{
			assertEquals(ArduinoCommand.class, cls);

			// we don't care about the functionality of the command, so set dependencies to null
			return (T)(new ArduinoCommand(null));
		}

	}

}