package morse.code.cmd;

import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import morse.code.Codec;
import morse.code.Signal;
import morse.code.audio.AudioMorseCoder;
import morse.code.audio.MorseCodePlayer;

public class MorseCmd {
    private final static int volume            = 40;
    private static double    beepFrequency     = 660.0;
    private static int       samplingFrequency = 44100;
    private static int       channels          = 2;
    private static int       sampleSizeInBytes = 2;
    private static double    dotTimeInSeconds  = 0.15;

    public static void main(final String[] args) {
        final String message = args.length > 0 ? args[0] : "SOS";

        final AudioMorseCoder audioMorseCoder = new AudioMorseCoder(volume, beepFrequency, samplingFrequency, channels, sampleSizeInBytes,
                dotTimeInSeconds);

        final MorseCodePlayer player = new MorseCodePlayer(audioMorseCoder);

        final Codec codec = new Codec();

        final List<Signal> signal = codec.encode(message);

        System.out.println("Playing Message: " + message + " : " + codec.toString(signal));
        try {
            player.play(signal);
        }
        catch (InterruptedException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
