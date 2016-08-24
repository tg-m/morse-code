package morse.code.gui.examples;

import javax.sound.sampled.LineUnavailableException;

import morse.code.Codec;
import morse.code.audio.MorseCodePlayer;

public class BeepExample {

    public static void main(final String[] args) {
        final String message = args.length > 0 ? args[0] : "SOS";

        final MorseCodePlayer player = new MorseCodePlayer();

        System.out.println("Playing Message: " + message);
        try {
            player.play(new Codec().encode(message));
        }
        catch (InterruptedException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
