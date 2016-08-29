package morse.code.audio;

import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import morse.code.Signal;

public final class MorseCodePlayer {
    private final AudioMorseCoder audioMorseCoder;
    public volatile Boolean play = false;

    public MorseCodePlayer(final AudioMorseCoder audioMorseCoder) {
        this.audioMorseCoder = audioMorseCoder;
    }

    public MorseCodePlayer() {
        this(new AudioMorseCoder(40, 660.0, 44100, 2, 2, 0.15));
    }
    public void stop()
    {
    	play = false;
    }

    public void play(final List<Signal> message) throws InterruptedException, LineUnavailableException {
    	play = true;
        final AudioFormat format = new AudioFormat(audioMorseCoder.getSamplingFrequency(), audioMorseCoder.getSampleSizeInBytes() * 8,
                audioMorseCoder.getChannels(), true, true);
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " is not supported.");
            throw new LineUnavailableException();
        }

        final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

        final byte[] buffer = audioMorseCoder.getWave(message).array();// constructSineWave(sineWaveFrequency,

        line.open(format, buffer.length);

        int offset = 0;
        line.start();
        while (play && offset < buffer.length) {
            final int ctSamplesThisPass = line.available();

            line.write(buffer, offset, Math.min(ctSamplesThisPass, buffer.length - offset));

            offset += ctSamplesThisPass;

            while (line.getBufferSize() / 7 < line.available()) {
                Thread.sleep(1);
            }
        }

        line.drain();
        line.close();
    }
}
