package kosiec.Server;

import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by Chad on 11/1/2014.
 */
public class ThreadedHandler implements Handler {

	private final ExecutorService executor;
	private final Handler handler;

	public ThreadedHandler(ExecutorService executor, Handler handler)
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
