package code.morse.gui;

import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class BeepExample {

    public static void main(final String[] args) {
        try {
            playSineSignal();
        }
        catch (InterruptedException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playSineSignal() throws InterruptedException, LineUnavailableException {
        final int samplingRate = 44100; // Audio sampling rate
        final double sineWaveFrequency = 440;

        final double lengthInSeconds = 30;
        final int volume = 40;
        final int channels = 2;

        final int sampleSize = 2; // Audio sample size in bytes

        // Open up audio output, using 44100hz sampling rate, 16 bit samples, mono, and big
        // endian byte ordering
        final AudioFormat format = new AudioFormat(samplingRate, sampleSize * 8, channels, true, true);
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " is not supported.");
            throw new LineUnavailableException();
        }

        final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

        final byte[] buffer = constructSineWave(sineWaveFrequency, samplingRate, lengthInSeconds, volume, channels, sampleSize).array();
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

    public static ByteBuffer constructSineWave(final double frequency, final int samplingFrequency, final double lengthInSeconds,
            int volume, final int channels, final int sampleSizeInBytes) {

        final int MAX_VOLUME = 100;
        if (volume > MAX_VOLUME) {
            volume = MAX_VOLUME;
        }
        if (0 > volume) {
            volume = 0;
        }

        final double samplesPerChannel = samplingFrequency * lengthInSeconds;
        final ByteBuffer result = ByteBuffer.allocate((int) (samplesPerChannel * channels * sampleSizeInBytes));

        final double normalisedFreq = frequency / samplingFrequency;

        for (int i = 0; i < samplesPerChannel; ++i) {
            for (int c = 0; c < channels; ++c) {
                result.putShort((short) (((Short.MAX_VALUE * volume) / MAX_VOLUME) * Math.sin(2 * Math.PI * i * normalisedFreq)));
            }
        }

        return result;
    }
}
