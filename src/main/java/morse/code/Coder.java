package morse.code;

import java.util.ArrayList;
import java.util.List;

public class Coder {

    public List<Signal> encode(final String text) {
        final List<Signal> result = new ArrayList<Signal>();

        for (final char c : text.toCharArray()) {
            result.addAll(MorseMap.get(c));
        }

        return result;
    }

}
