/**
 *
 */
package morse.code;

import static morse.code.Signal.DASH;
import static morse.code.Signal.DOT;
import static morse.code.Signal.SIGN_STOP;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Iterables;

/**
 * @author tgm
 *
 */
public class CoderTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void encode_tomek() {
        final Coder c = new Coder();
        final List<Signal> actual = c.encode("TOMEK");
        final List<Signal> expected = Arrays.asList(DASH, SIGN_STOP, DASH, DASH, DASH, SIGN_STOP, DASH, DASH, SIGN_STOP, DOT, SIGN_STOP,
                DASH, DOT, DASH);

        Assert.assertTrue(Iterables.elementsEqual(expected, actual));

    }

}
