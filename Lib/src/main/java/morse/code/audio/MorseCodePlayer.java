package morse.code.audio;

import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import morse.code.Signal;

public final class MorseCodePlayer {
    private final int     samplingRate    = 44100;
    private final double  beepFrequency   = 660.0;
    private final int     volume          = 40;
    private final int     channels        = 2;

    private final double  dotTimeInSecods = 0.15;
    private final int     sampleSize      = 2;

    final AudioMorseCoder audioMorseCoder = new AudioMorseCoder(volume, beepFrequency, samplingRate, channels, sampleSize, dotTimeInSecods);

    public MorseCodePlayer() {

    }

    public void play(final List<Signal> message) throws InterruptedException, LineUnavailableException {
        final AudioFormat format = new AudioFormat(samplingRate, sampleSize * 8, channels, true, true);
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " is not supported.");
            throw new LineUnavailableException();
        }

        final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

        final byte[] buffer = audioMorseCoder.getWave(message).array();// constructSineWave(sineWaveFrequency,
        // samplingRate, lengthInSeconds,
        // volume, channels,
        // sampleSize).array();
        line.open(format, buffer.length);

        int offset = 0;
        line.start();
        while (offset < buffer.length) {
            final int ctSamplesThisPass = line.available();
            line.write(buffer, offset, ctSamplesThisPass);

            offset += ctSamplesThisPass;

            // Wait until the buffer is at least half empty before we add more
            while (line.getBufferSize() / 7 < line.available()) {
                Thread.sleep(1);
            }
        }

        line.drain();
        line.close();
    }
}
