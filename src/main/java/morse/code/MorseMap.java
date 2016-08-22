package morse.code;

import static morse.code.Signal.DASH;
import static morse.code.Signal.DOT;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableMap;

final class MorseMap {
    private MorseMap() {}

    public final static List<Signal> get(final Character c) {
        return map.get(Character.toUpperCase(c));
    }

    private static final ImmutableMap<Character, List<Signal>> map = ImmutableMap.<Character, List<Signal>> builder()
            .put(Character.valueOf('A'), Arrays.asList(DOT, DASH))
            .put(Character.valueOf('B'), Arrays.asList(DASH, DOT, DOT, DOT))
            .put(Character.valueOf('C'), Arrays.asList(DASH, DOT, DASH, DOT))
            .put(Character.valueOf('D'), Arrays.asList(DASH, DOT, DOT))
            .put(Character.valueOf('E'), Arrays.asList(DOT))
            .put(Character.valueOf('F'), Arrays.asList(DOT, DOT, DASH, DOT))
            .put(Character.valueOf('G'), Arrays.asList(DASH, DASH, DOT))
            .put(Character.valueOf('H'), Arrays.asList(DOT, DOT, DOT, DOT))
            .put(Character.valueOf('I'), Arrays.asList(DOT, DOT))
            .put(Character.valueOf('J'), Arrays.asList(DOT, DASH, DASH, DASH))
            .put(Character.valueOf('K'), Arrays.asList(DASH, DOT, DASH))
            .put(Character.valueOf('L'), Arrays.asList(DOT, DASH, DOT, DOT))
            .put(Character.valueOf('M'), Arrays.asList(DASH, DASH))
            .put(Character.valueOf('N'), Arrays.asList(DASH, DOT))
            .put(Character.valueOf('O'), Arrays.asList(DASH, DASH, DASH))
            .put(Character.valueOf('P'), Arrays.asList(DOT, DASH, DASH, DOT))
            .put(Character.valueOf('Q'), Arrays.asList(DASH, DASH, DOT, DASH))
            .put(Character.valueOf('R'), Arrays.asList(DOT, DASH, DOT))
            .put(Character.valueOf('S'), Arrays.asList(DOT, DOT, DOT))
            .put(Character.valueOf('T'), Arrays.asList(DASH))
            .put(Character.valueOf('U'), Arrays.asList(DOT, DOT, DASH))
            .put(Character.valueOf('V'), Arrays.asList(DOT, DOT, DOT, DASH))
            .put(Character.valueOf('W'), Arrays.asList(DOT, DASH, DASH))
            .put(Character.valueOf('X'), Arrays.asList(DASH, DOT, DOT, DASH))
            .put(Character.valueOf('Y'), Arrays.asList(DASH, DOT, DASH, DASH))
            .put(Character.valueOf('Z'), Arrays.asList(DASH, DASH, DOT, DOT))

            .put(Character.valueOf('1'), Arrays.asList(DOT, DASH, DASH, DASH, DASH))
            .put(Character.valueOf('2'), Arrays.asList(DOT, DOT, DASH, DASH))
            .put(Character.valueOf('3'), Arrays.asList(DOT, DOT, DOT, DASH, DASH))
            .put(Character.valueOf('4'), Arrays.asList(DOT, DOT, DOT, DOT, DASH))
            .put(Character.valueOf('5'), Arrays.asList(DOT, DOT, DOT, DOT, DOT))
            .put(Character.valueOf('6'), Arrays.asList(DASH, DOT, DOT, DOT, DOT))
            .put(Character.valueOf('7'), Arrays.asList(DASH, DASH, DOT, DOT, DOT))
            .put(Character.valueOf('8'), Arrays.asList(DASH, DASH, DASH, DOT, DOT))
            .put(Character.valueOf('9'), Arrays.asList(DASH, DASH, DASH, DASH, DOT))
            .put(Character.valueOf('0'), Arrays.asList(DASH, DASH, DASH, DASH, DASH))
            .build();

}
