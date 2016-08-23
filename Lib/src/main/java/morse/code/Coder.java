package morse.code;

import java.util.ArrayList;
import java.util.List;

public class Coder {

    public List<Signal> encode(final String text) {
        final List<Signal> result = new ArrayList<Signal>();

        if (0 < text.length()) {
            result.addAll(MorseMap.get(text.charAt(0)));

            for (int i = 1; i < text.length(); ++i) {
                result.add(Signal.SIGN_STOP);
                result.addAll(MorseMap.get(text.charAt(i)));
            }
        }

        return result;
    }

}
