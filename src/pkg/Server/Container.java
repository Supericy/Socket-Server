package pkg.Server;

/**
 * Created by Chad on 10/31/2014.
 */
public interface Container {

	void put(Class<?> cls, Object impl);
	void put(Class<?> cls, Object impl, String name);

	<T> T get(Class<? extends T> cls);
	<T> T get(Class<? extends T> cls, String name);

}
