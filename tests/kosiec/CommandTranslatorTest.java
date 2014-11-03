package kosiec;

import kosiec.Server.Command.CommandTranslator;
import kosiec.Server.MetaCommand;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandTranslatorTest {

	@Test
	public void testDecode() throws Exception
	{
		CommandTranslator commandTranslator = new CommandTranslator();

		MetaCommand meta1 = commandTranslator.decode("CommandName1");
		assertEquals("CommandName1", meta1.getCommandName());
		assertEquals(0, meta1.getCommandArgs().length);
		assertSame(CommandTranslator.NO_ARGS, meta1.getCommandArgs());

		MetaCommand meta2 = commandTranslator.decode("CommandName2;Arg0");
		assertEquals("CommandName2", meta2.getCommandName());
		assertEquals(1, meta2.getCommandArgs().length);
		assertEquals("Arg0", meta2.getCommandArgs()[0]);

		MetaCommand meta3 = commandTranslator.decode("CommandName3;Arg0;Arg1");
		assertEquals("CommandName3", meta3.getCommandName());
		assertEquals(2, meta3.getCommandArgs().length);
		assertEquals("Arg0", meta3.getCommandArgs()[0]);
		assertEquals("Arg1", meta3.getCommandArgs()[1]);
	}

}