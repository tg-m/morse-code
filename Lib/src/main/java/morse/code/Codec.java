package morse.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableBiMap;

public class Codec {
    private final ImmutableBiMap<Signal, Character> map;

    public Codec(final ImmutableBiMap<Signal, Character> printingMap) {
        this.map = printingMap;
    }

    public Codec() {
        this(ImmutableBiMap.<Signal, Character> builder()
                .put(Signal.DASH, '-')
                .put(Signal.DOT, '.')
                .put(Signal.SIGN_STOP, ' ')
                .put(Signal.WORD_STOP, '/')
                .build());
    }

    public List<Signal> encode(final String text) {
        final List<Signal> result = new ArrayList<Signal>();

        if (0 < text.length()) {
            List<Signal> c = MorseMap.get(text.charAt(0));
            if (null != c) {
                result.addAll(c);
            }

            for (int i = 1; i < text.length(); ++i) {
                if (' ' != text.charAt(i) && ' ' != text.charAt(i - 1)) {
                    result.add(Signal.SIGN_STOP);
                }
                c = MorseMap.get(text.charAt(i));
                if (null != c) {
                    result.addAll(c);
                }
            }
        }

        return result;
    }

    public String decode(final List<Signal> message) {
        final StringBuilder result = new StringBuilder("");

        final List<Signal> word = new ArrayList<Signal>();

        for (final Signal s : message) {
            if (Signal.DOT == s || Signal.DASH == s) {
                word.add(s);
            }
            else {
                if (false == word.isEmpty()) {
                    result.append(MorseMap.get(word));
                }
                word.clear();

                if (Signal.WORD_STOP == s) {
                    result.append(MorseMap.get(Arrays.asList(s)));
                }
            }
        }

        if (false == word.isEmpty()) {
            result.append(MorseMap.get(word));
        }

        return result.toString();
    }

    public String toString(final List<Signal> signals) {
        String result = "";

        for (final Signal s : signals) {
            result += map.get(s);
        }

        return result;
    }

    public List<Signal> toSignal(final String message) {
        final List<Signal> result = new ArrayList<Signal>();

        for (int i = 0; i < message.length(); ++i) {
            final Signal s = map.inverse().get(message.charAt(i));
            if (null != s) {
                result.add(s);
            }
        }

        return result;
    }
}
