package morse.code.audio;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import morse.code.Codec;

public class AudioMorseCoderTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testgetWave_just_call_without_any_check() {
        Assert.assertNotNull(new AudioMorseCoder(30).getWave(new Codec().encode("asdljfaksdbjf askdfbaks d")));

    }

}
