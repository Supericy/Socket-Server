package pkg.Server;

import java.util.concurrent.ExecutorService;

/**
 * Created by Chad on 11/1/2014.
 */
public class ThreadedClientHandler implements Handler<Client> {

	private final ExecutorService executor;
	private final Handler<Client> handler;

	public ThreadedClientHandler(ExecutorService executor, Handler<Client> handler)
	{
		this.executor = executor;
		this.handler = handler;
	}

	@Override
	public void handle(final Client client)
	{
		executor.execute(new Runnable() {
			@Override
			public void run()
			{
				handler.handle(client);
			}
		});
	}
}
