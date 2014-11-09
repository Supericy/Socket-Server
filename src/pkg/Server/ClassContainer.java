package pkg.Server;

import java.util.HashMap;

/**
 * Created by Chad on 10/31/2014.
 */
public class ClassContainer implements Container {

	private HashMap<Class<?>, Object> classes;

	public ClassContainer()
	{
		this.classes = new HashMap<Class<?>, Object>();
	}

	@Override
	public void put(Class<?> key, Object impl)
	{
		classes.put(key, impl);
	}

	@Override
	public <T> T get(Class<? extends T> key)
	{
		return (T)classes.get(key);
	}

}
