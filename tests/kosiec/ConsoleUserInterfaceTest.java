package kosiec;

import kosiec.Server.ConsoleUserInterface;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.*;

public class ConsoleUserInterfaceTest {

	@Test
	public void testDisplay() throws Exception
	{
		MockPrintStream mockStream = new MockPrintStream();

		ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface(mockStream);

		// new stream should have had nothing written to it
		assertEquals(null, mockStream.whatWasLastWritten());

		consoleUserInterface.display("Just a test string to display");
		assertEquals("Just a test string to display", mockStream.whatWasLastWritten());
	}

	static class MockPrintStream extends PrintStream {

		private String lastWritten;

		public MockPrintStream()
		{
			// We don't actually care about this, it's just to build a mock print stream, since we are going to overrwite
			// what we need anyway.
			super(System.out);

			lastWritten = null;
		}

		@Override
		public void println(String str)
		{
			lastWritten = str;
		}

		public String whatWasLastWritten()
		{
			return lastWritten;
		}

	}
}