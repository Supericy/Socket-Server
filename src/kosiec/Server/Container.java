package kosiec.Server;

/**
 * Created by Chad on 10/31/2014.
 */
public interface Container {
	void put(Class<?> key, Object impl);

	<T> T get(Class<? extends T> key);
}
