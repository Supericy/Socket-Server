package kosiec;

import kosiec.Server.Arduino.Commands.ArduinoCommand;
import kosiec.Server.CommandFactory;
import kosiec.Server.Container;
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

		CommandFactory commandFactory = new CommandFactory(container);

		assertThat(commandFactory.make("ArduinoCommand"), instanceOf(ArduinoCommand.class));
		assertThat(commandFactory.make(""), nullValue());
		assertThat(commandFactory.make("something random"), nullValue());
	}

	static class MockContainer implements Container {

		@Override
		public void put(Class<?> key, Object impl)
		{
			// don't need this method, so we'll just throw a notimplementedexception just in-case
			throw new NotImplementedException();
		}

		@Override
		public <T> T get(Class<? extends T> key)
		{
			assertEquals(ArduinoCommand.class, key);

			// we don't care about the functionality of the command, so set dependencies to null
			return (T)(new ArduinoCommand(null));
		}

	}

}