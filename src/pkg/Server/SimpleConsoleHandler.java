package pkg.Server;

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
		System.err.println(record.getLoggerName() + " >> " + record.getMessage() + stringify(record.getParameters()));
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

	private String stringify(Object[] parameters)
	{
		StringBuilder sb = new StringBuilder();

		if (parameters != null)
		{
			boolean first = true;
			sb.append(" : [");

			for (Object param : parameters)
			{
				if (!first)
					sb.append(", ");

				sb.append("(");
				sb.append(param == null ? "null" : param.getClass().toString());
				sb.append(", ");
				sb.append(param == null ? "null" : param.toString());
				sb.append(")");

				first = false;
			}


			sb.append("]");
		}

		return new String(sb);
	}

}
