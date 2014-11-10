package pkg.Server;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Chad on 10/31/2014.
 */
public class ClassContainer implements Container {

	private static final Logger log = Logger.getLogger( ClassContainer.class.getName() );
	static {
		log.addHandler(new SimpleConsoleHandler());
		log.setLevel(Level.OFF);
	}

	private HashMap<ClassKey, Object> classes;

	public ClassContainer()
	{
		this.classes = new HashMap<ClassKey, Object>();
	}

	@Override
	public void put(Class<?> cls, Object impl)
	{
		put(cls, impl, null);
	}

	@Override
	public void put(Class<?> cls, Object impl, String name)
	{
		ClassKey key = new ClassKey(cls, name);

		log.log(Level.FINE, "put", new Object[] { key.cls, key.name, key.hashCode() });

		classes.put(key, impl);
	}

	@Override
	public <T> T get(Class<? extends T> cls)
	{
		return get(cls, null);
	}

	@Override
	public <T> T get(Class<? extends T> cls, String name)
	{
		ClassKey key = new ClassKey(cls, name);

		log.log(Level.FINE, "get", new Object[] { key.cls, key.name, key.hashCode() });

		return (T) classes.get(key);
	}

	private static class ClassKey {
		public Class<?> cls;
		public String name;

		public ClassKey(Class<?> cls, String name)
		{
			this.cls = cls;
			this.name = name;
		}

		@Override
		public int hashCode()
		{
			return (cls.toString() + "#" + (name==null ? "~" : name)).hashCode();
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof ClassKey))
				return false;
			ClassKey other = (ClassKey) obj;

			return this.cls.equals(other.cls) &&
					(
							(this.name == null && other.name == null) ||
							(this.name != null && this.name.equals(other.name)) ||
							(other.name != null && other.name.equals(this.name))
					);
		}
	}

}
