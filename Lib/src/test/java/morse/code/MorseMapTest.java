package morse.code;

import static morse.code.Signal.DASH;
import static morse.code.Signal.DOT;
import static morse.code.Signal.WORD_STOP;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Iterables;

public class MorseMapTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void test_letter_A() {
        final List<Signal> actual = MorseMap.get('A');
        final List<Signal> expected = Arrays.asList(DOT, DASH);

        Assert.assertTrue(Iterables.elementsEqual(expected, actual));
    }

    @Test
    public void test_letter_A_reverse() {
        final Character actual = MorseMap.get(Arrays.asList(DOT, DASH));
        final Character expected = 'A';

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_word_stop() {
        final List<Signal> actual = MorseMap.get(' ');
        final List<Signal> expected = Arrays.asList(WORD_STOP);

        Assert.assertTrue(Iterables.elementsEqual(expected, actual));
    }

    @Test
    public void test_all_letters_are_in_the_map() {
        final List<Character> letters = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

        for (final Character c : letters) {
            Assert.assertNotNull(MorseMap.get(c));
        }
    }

    @Test
    public void test_all__polish_letters_are_in_the_map() {
        final List<Character> letters = Arrays.asList('Ą', 'Ę', 'Ó', 'Ś', 'Ł', 'Ż', 'Ź', 'Ć', 'Ń');

        for (final Character c : letters) {
            Assert.assertNotNull(MorseMap.get(c));
        }
    }

    @Test
    public void test_all_numbers_are_in_the_map() {
        final List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

        for (final Character c : numbers) {
            Assert.assertNotNull(MorseMap.get(c));
        }
    }

    @Test
    public void test_signs_that_are_in_the_morse_alphabet() {
        final List<Character> signs = Arrays.asList(',', '.', '!', '@', '$', '&', '(', ')', '_', '-', '+', '=', ':', '"', ';', '?', ',',
                '.', '/', '\'');

        for (final Character c : signs) {
            Assert.assertNotNull(MorseMap.get(c));
        }
    }

    @Test
    public void test_signs_that_are_not_in_the_alphabet() {
        final List<Character> signs = Arrays.asList('~', '#', '%', '^', '*', '[', ']', '\\', '{', '}', '|', '<', '>');

        for (final Character c : signs) {
            Assert.assertNull(MorseMap.get(c));
        }
    }

}
