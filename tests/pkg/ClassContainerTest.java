package pkg;

import pkg.Server.ClassContainer;
import pkg.Server.Container;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

public class ClassContainerTest {



	@Test
	public void testPuttingAndGettingFromContainer() throws Exception
	{
		Container container = new ClassContainer();

		MockClass1 mock1 = new MockClass1();
		MockClass2 mock2 = new MockClass2();

		container.put(MockClass1.class, mock1);
		container.put(MockClass2.class, mock2);
		container.put(SomeInterface.class, new SomeImplementation());

		assertThat(container.get(MockClass1.class), instanceOf(MockClass1.class));
		assertThat(container.get(MockClass1.class), equalTo(mock1));

		assertThat(container.get(MockClass2.class), instanceOf(MockClass2.class));
		assertThat(container.get(MockClass2.class), equalTo(mock2));

		assertThat(container.get(SomeInterface.class), instanceOf(SomeImplementation.class));
	}

	@Test
	public void testPuttingNamedClassAndGettingFromContainer() throws Exception
	{
		Container container = new ClassContainer();

		SomeImplementation defaultImpl = new SomeImplementation();
		SomeAlternateImplementation alternameImpl = new SomeAlternateImplementation();

		container.put(SomeInterface.class, defaultImpl, "default_impl");
		container.put(SomeInterface.class, alternameImpl, "alternate_impl");

		assertThat(container.get(SomeInterface.class, "default_impl"), instanceOf(SomeImplementation.class));
		assertThat(container.get(SomeInterface.class, "default_impl"), equalTo((SomeInterface) defaultImpl));

		assertThat(container.get(SomeInterface.class, "alternate_impl"), instanceOf(SomeAlternateImplementation.class));
		assertThat(container.get(SomeInterface.class, "alternate_impl"), equalTo((SomeInterface) alternameImpl));


	}

	static class MockClass1 {

	}

	static class MockClass2 {

	}

	static interface SomeInterface {

	}

	static class SomeImplementation implements SomeInterface {

	}

	static class SomeAlternateImplementation implements SomeInterface {

	}

}