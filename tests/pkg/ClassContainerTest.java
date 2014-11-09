package pkg;

import pkg.Server.ClassContainer;
import pkg.Server.Container;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

public class ClassContainerTest {

	@Test
	public void testPutAndStore() throws Exception
	{
		Container container = new ClassContainer();

		container.put(MockClass1.class, new MockClass1());
		container.put(MockClass2.class, new MockClass2());
		container.put(SomeInterface.class, new SomeImplementation());

		assertThat(container.get(MockClass1.class), instanceOf(MockClass1.class));
		assertThat(container.get(MockClass2.class), instanceOf(MockClass2.class));
		assertThat(container.get(SomeInterface.class), instanceOf(SomeImplementation.class));
	}

	static class MockClass1 {

	}

	static class MockClass2 {

	}

	static interface SomeInterface {

	}

	static class SomeImplementation implements SomeInterface {

	}

}