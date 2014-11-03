package kosiec.Server;

import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by Chad on 11/1/2014.
 */
public class ThreadedSocketHandler implements SocketHandler {

	private final ExecutorService executor;
	private final SocketHandler handler;

	public ThreadedSocketHandler(ExecutorService executor, SocketHandler handler)
	{
		this.executor = executor;
		this.handler = handler;
	}

	@Override
	public void handle(final Socket socket)
	{
		executor.execute(new Runnable() {
			@Override
			public void run()
			{
				handler.handle(socket);
			}
		});
	}
}
