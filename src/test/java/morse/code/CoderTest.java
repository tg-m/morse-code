/**
 * 
 */
package morse.code;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;

/**
 * @author tgm
 *
 */
public class CoderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_that_returns_0() {
		Coder c = new Coder();
		final int actual = c.test();
		final int expected = 0;

		Assert.assertEquals(expected, actual);

	}

}
