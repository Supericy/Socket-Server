package kosiec.Server;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by Chad on 11/2/2014.
 */
public class SimpleConsoleHandler extends Handler {

	public SimpleConsoleHandler()
	{
		setLevel(Level.ALL);
	}

	@Override
	public void publish(LogRecord record)
	{
		System.out.println(record.getLoggerName() + " >> " + record.getMessage() + stringify(record.getParameters(), record.getParameters()!=null));
	}

	@Override
	public void flush()
	{
		System.out.flush();
	}

	@Override
	public void close() throws SecurityException
	{
	}

	private String stringify(Object[] parameters, boolean includeArrows)
	{
		StringBuilder sb = new StringBuilder();

		if (includeArrows)
			sb.append(" >> ");

		if (parameters != null)
		{
			for (Object param : parameters)
				sb.append(param.toString());
		}

		return new String(sb);
	}

}
